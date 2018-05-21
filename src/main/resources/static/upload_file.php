<?php
	require_once 'idiorm.php';
	ORM::configure('mysql:host=localhost;dbname=tema');
	ORM::configure('username','root');
	ORM::configure('password','');
	ORM::get_db();

	if(isset($_FILES["file"]["type"]) && isset($_POST["user"]))
	{
		$vari = $_POST["user"];
		$validextensions = array("jpeg", "jpg", "png");
		$temporary = explode(".", $_FILES["file"]["name"]);
		$file_extension = end($temporary);
		if ((($_FILES["file"]["type"] == "image/png") || ($_FILES["file"]["type"] == "image/jpg") || ($_FILES["file"]["type"] == "image/jpeg")
		) && ($_FILES["file"]["size"] < 100000)//Approx. 100kb files can be uploaded.
		&& in_array($file_extension, $validextensions)) {
			if ($_FILES["file"]["error"] > 0)
			{
				echo "Return Code: " . $_FILES["file"]["error"] . "<br/><br/>";
			}
			else
			{
				$path = "upload/" . $vari . "_";
				$nume_imag = $_FILES["file"]["name"];
				if (file_exists($path . $_FILES["file"]["name"])) {
					echo $_FILES["file"]["name"] . "already exists. ";
				}
				else
				{
					$sourcePath = $_FILES['file']['tmp_name']; // Storing source path of the file in a variable
					$targetPath = $path.$_FILES['file']['name']; // Target path where file is to be stored
					move_uploaded_file($sourcePath,$targetPath) ; // Moving Uploaded file
				
					$article = ORM::for_table('poze')->create();
					$article->user = $vari;
					$qry =  ORM::for_table('users')->where('nume', $vari)->find_one();
					$article->album = $qry->album;
					$article->path = $targetPath;
					$article->imagine = $nume_imag;
					$article->save();
					echo "Upload success";
				}
			}
		}
		else
		{
			echo "Upload fail due to size or type";
		}
	}
	else
	{
		echo "Not set";
	}
?>


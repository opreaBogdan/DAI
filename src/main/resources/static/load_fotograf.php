<?php
require_once 'idiorm.php';
ORM::configure('mysql:host=localhost;dbname=tema');
ORM::configure('username','root');
ORM::configure('password','');
ORM::get_db();

if(isset($_POST["fotograf"]))
{
	$userName = $_POST["fotograf"];
	$article = ORM::for_table('poze')->where('user',$userName)->find_many();
	if(empty($article))
	{
		$article = ORM::for_table('poze')->where('user',$userName)->find_one();
		if(empty($article))
		{
			echo 'Nu';
		}
		else
		{
			echo json_encode(array("photolist"=>$article));
		}
	}
	else
	{
		$result = array();
    	foreach($article as $item)
        {
         	array_push( $result, $item->path);
		}                
  
		echo json_encode(array("photolist"=>$result));
	}
}


?>


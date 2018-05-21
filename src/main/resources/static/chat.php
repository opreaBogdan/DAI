<?php

require_once 'idiorm.php';

ORM::configure('mysql:host=localhost;dbname=tema');
ORM::configure('username','root');
ORM::configure('password','');
ORM::get_db();


if (isset($_POST['cur_u']))
{
	$foto = $_POST["cur_u"];
	$article = ORM::for_table('chat')->find_many();
	if(empty($article))
	{
		$article = ORM::for_table('chat')->find_one();
		if(empty($article))
		{
			echo 'Nu';
		}
		else
		{
            $arr = array("user"=>$article->user,
            "text"=>$article->textul);           
			echo json_encode(array("chat"=>$arr));
		}
	}
	else
	{
		$result = array();
    	foreach($article as $item){
            $arr = array("user"=>$item->user,
            "text"=>$item->textul);   
         	array_push($result, $arr);
		}                
  
		echo json_encode(array("chat"=>$result));
	}
}


?>
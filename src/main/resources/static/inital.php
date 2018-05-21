<?php

require_once 'idiorm.php';

ORM::configure('mysql:host=localhost;dbname=tema');
ORM::configure('username','root');
ORM::configure('password','');
ORM::get_db();


if (isset($_POST['f']))
{
	$article = ORM::for_table('users')->find_many();
	if(empty($article))
	{
		$article = ORM::for_table('users')->find_one();
		if(empty($article))
		{
			echo 'Nu';
		}
		else
		{ 
			echo json_encode(array("user"=>$article->nume));
		}
	}
	else
	{
		$result = array();
    	foreach($article as $item){
         	array_push($result, $item->nume);
		}                
  
		echo json_encode(array("user"=>$result));
	}
}

?>
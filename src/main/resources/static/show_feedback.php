<?php

require_once 'idiorm.php';

ORM::configure('mysql:host=localhost;dbname=tema');
ORM::configure('username','root');
ORM::configure('password','');
ORM::get_db();


if (isset($_POST['fotog']))
{
	$foto = $_POST["fotog"];
	$article = ORM::for_table('comment')->where('poza',$foto)->find_many();
	if(empty($article))
	{
		$article = ORM::for_table('comment')->where('poza',$foto)->find_one();
		if(empty($article))
		{
			echo 'Nu';
		}
		else
		{
            $arr = array("user"=>$article->user,
            "stele"=>$article->stele,
            "comm"=>$article->comm);           
			echo json_encode(array("feedback"=>$arr));
		}
	}
	else
	{
		$result = array();
    	foreach($article as $item){
            $arr = array("user"=>$item->user,
            "stele"=>$item->stele,
            "comm"=>$item->comm); 
         	array_push($result, $arr);
		}                
  
		echo json_encode(array("feedback"=>$result));
	}
}


?>
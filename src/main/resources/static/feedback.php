<?php

require_once 'idiorm.php';

ORM::configure('mysql:host=localhost;dbname=tema');
ORM::configure('username','root');
ORM::configure('password','');
ORM::get_db();


if (isset($_POST['user']) && isset($_POST['foto']) && isset($_POST['comm']) && isset($_POST['stea']))
{
    $user = $_POST['user'];
    $foto = $_POST['foto'];
    $comm = $_POST['comm'];
    $stea = $_POST['stea'];

    $article = ORM::for_table('comment')->create();
    
    $article->user = $user;
	$article->poza = $foto;
    $article->stele = $stea;
    $article->comm = $comm;
   
	
    $qry =  ORM::for_table('poze')->where('path', $foto)->find_one();
    if(!empty($qry))
	    $article->detine = $qry->user;
	
    $article->save();
    echo "sf";
}


?>
<?php
require_once 'idiorm.php';

ORM::configure('mysql:host=localhost;dbname=tema');
ORM::configure('username','root');
ORM::configure('password','');
ORM::get_db();


if (isset($_POST['user']) && isset($_POST['text'])){
    $user = $_POST['user'];
    $text = $_POST['text'];

    $mes = ORM::for_table('chat')->create();
	$mes->user = $user;
	$mes->textul = $text;
	$mes->save();    
 
	echo "1";
}

?>

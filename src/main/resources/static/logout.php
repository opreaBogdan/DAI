<?php
require_once 'idiorm.php';

ORM::configure('mysql:host=localhost;dbname=tema');
ORM::configure('username','root');
ORM::configure('password','');
ORM::get_db();

if(isset($_POST['sessionId'])) session_id($_POST['sessionId']);            
session_start();

if(isset($_POST['action'])){
    //$_SESSION = array();
	unset($_SESSION['uName']);
	unset($_SESSION['oId']);
	unset($_SESSION['auth']);
	$_SESSION = array();
	session_unset();  
    session_destroy();
    echo "logout";
	exit;
}

?>
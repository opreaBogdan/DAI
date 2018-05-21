<?php
require_once 'idiorm.php';

ORM::configure('mysql:host=localhost;dbname=tema');
ORM::configure('username','root');
ORM::configure('password','');
ORM::get_db();

if(isset($_POST['sessionId'])) session_id($_POST['sessionId']);            
session_start();
    


if (isset($_POST['name']) && isset($_POST['pwd'])){
    $uName = $_POST['name'];
    $pWord = $_POST['pwd'];

    $qry =  ORM::for_table('users')->where('nume', $uName)->find_one();

    
    if( !empty($qry) && $qry->pass == $pWord) {
     
	    
	    $_SESSION['uName'] = $qry->nume;
	    $_SESSION['oId'] = $qry->id;
	    $_SESSION['auth'] = $qry->album;

        echo $qry->nume;
    }
    else {
	    echo 'false';
    }
}

?>
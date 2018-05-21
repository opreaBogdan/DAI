<?php
require_once 'idiorm.php';

ORM::configure('mysql:host=localhost;dbname=tema');
ORM::configure('username','root');
ORM::configure('password','');
ORM::get_db();


if (isset($_POST['name']) && isset($_POST['pwd']) && isset($_POST['album'])){
    $uName = $_POST['name'];
    $pWord = $_POST['pwd'];
    $nAlbum = $_POST['album'];

    $qry =  ORM::for_table('users')->where('nume', $uName)->find_one();

    $id = 1;
    $q =  ORM::for_table('users')->find_many();

    
    foreach($q as $vari)
    {
        $id++;
    }
    $id++;

    if( empty($qry) ) 
    {
        $user = ORM::for_table('users')->create();
		$user->nume = $_POST['name'];
		$user->pass = $_POST['pwd'];
        $user->album = $_POST['album'];
        $user->id = $id;
		$user->save();    
        echo "1";
    }
    else 
    {
	    echo "2";
    }
}

?>

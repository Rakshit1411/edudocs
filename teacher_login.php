<?php

$host = "127.0.0.1";
$user = "root";
$password = "1234";
$database = "softwareapp";
$connect = mysqli_connect($host, $user, $password, $database);

if(mysqli_connect_errno()){
	die("Cannot connect to database".mysqli_connect_error());
}


$query = "select * from teacher_authentication where teacher_email='".$_GET['teacher_email']."' and teacher_password = '".$_GET['teacher_password']."';";

$result = mysqli_query($connect,$query);

if(!$result){
	die('Error cannot run the query');
}
$userInfo = array();

while($row = mysqli_fetch_assoc($result)){
	$userInfo[] = $row;
	break; // Because only one entry will be returned
}
if($userInfo){
print("{'msg':'pass login','info':'". json_encode($userInfo) ."'}");	
}
else{
print("{'msg':'Cannot login'}");		
}

mysqli_free_result($result);

mysqli_close($connect);

?>
<?php

$host = "127.0.0.1";
$user = "root";
$password = "1234";
$database = "softwareapp";
$connect = mysqli_connect($host, $user, $password, $database);

if(mysqli_connect_errno()){
	die("Cannot connect to database".mysqli_connect_error());
}

$query = "update authentication_details_table set image = '".$_GET['image']."' where email = '".$_GET['email']."';";
$result = mysqli_query($connect,$query);

if(!$result){
	$output = "{ 'msg':'fail'}";
}
else{
	$output = "{ 'msg':'user is added'}";	
}
print($output);

mysqli_close($connect);

?>
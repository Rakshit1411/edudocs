<?php

$host = "127.0.0.1";
$user = "root";
$password = "1234";
$database = "softwareapp";
$connect = mysqli_connect($host, $user, $password, $database);

if(mysqli_connect_errno()){
	die("Cannot connect to database".mysqli_connect_error());
}

$query = "insert into documents(tokenID,postID,postText,subCode,postDoc) values('".$_GET['tokenID']."','".$_GET['postID']."','".$_GET['postText']."','".$_GET['subCode']."','".$_GET['postDoc']."')";
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
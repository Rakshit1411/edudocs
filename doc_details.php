<?php

$host = "127.0.0.1";
$user = "root";
$password = "1234";
$database = "softwareapp";
$connect = mysqli_connect($host, $user, $password, $database);

if(mysqli_connect_errno()){
	die("Cannot connect to database".mysqli_connect_error());
}

//http://localhost/doc_details.php?subCode=UML-501&branch_group=CAG-3&tokenID=PSR

$query = "select * from documents where subCode = '".$_GET['subCode']."' and branch_group = '".$_GET['branch_group']."' and tokenID = '".$_GET['tokenID']."' order by postDate desc;";

$result = mysqli_query($connect,$query);

if(!$result){
	die('Error cannot run the query');
}
$userInfo = array();

while($row = mysqli_fetch_assoc($result)){
	$userInfo[] = $row;
	 // Because only one entry will be returned
}
if($userInfo){
print("{'msg':'pass image','info':'". json_encode($userInfo) ."'}");	
}
else{
print("{'msg':'Cannot find image'}");		
}

mysqli_free_result($result);

mysqli_close($connect);

?>
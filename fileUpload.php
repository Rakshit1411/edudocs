<?php 
	$host = "127.0.0.1";
	$user = "root";
	$password = "1234";
	$database = "softwareapp";
	$connect = mysqli_connect($host, $user, $password, $database);

	$file_path = "files/";
	$file_path = $file_path . basename($_FILES['uploaded_file']['name']);

	if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'],$file_path))	{
		echo "success";
		$link=mysqli_connect($host,$user,$password,$database);
    	mysqli_query($link,"INSERT INTO documents (tokenID, branch_group, postText, subCode, postDoc) VALUES ('".$_GET['tokenID']."','".$_GET['branch_group']."','".$_GET['postText']."','".$_GET['subCode']."','".$file_path."');");
	    mysqli_close($link);	
	}else {
		echo "error";
	}
 ?>
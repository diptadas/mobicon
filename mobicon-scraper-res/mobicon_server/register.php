<?php

   include 'connect.php';
   
   $user_name = $_GET['user_name'];
   $password = $_GET['password'];
   $email = $_GET['email'];
   
   $response["success"] = 0;
   $response["data"] = array();
   
	if (!filter_var($email, FILTER_VALIDATE_EMAIL)) //Invalid email format
	{
	   $response["success"] = 1;
	   $single_info = array();
	   $single_info["status"] = "Invalid email format";   
	   array_push($response["data"],$single_info);
	   goto END;
	}
   
   $sql = "SELECT * FROM user_info WHERE user_name = '" . $user_name . "'";
   
   $result = mysql_query($sql);
   
   if(mysql_error()) goto END;
   
   if(mysql_num_rows($result)>0)  //uesr_name not available
   {
	   $response["success"] = 1;
	   $single_info = array();
	   $single_info["status"] = "User name not available.";   
	   array_push($response["data"],$single_info);
	   goto END;
   }
   
   $sql = "INSERT INTO user_info VALUES('" . $user_name . "','" . $password . "','" . $email . "')";
   
   $result = mysql_query($sql);
   
   if(mysql_error()) goto END;
   
   $response["success"] = 1;   
   $single_info = array();
   $single_info["status"] = "ok";   
   array_push($response["data"],$single_info);
   
END:   
   echo json_encode($response);
   
?>   
<?php

   include 'connect.php';
   
   $user_name = $_GET['user_name'];
   
   $response["success"] = 0;
   $response["data"] = array();
   
   $sql = "SELECT password FROM user_info WHERE user_name = '" . $user_name . "'";
   
   $result = mysql_query($sql);
   
   if(!mysql_error()) 
   {
	   $response["success"] = 1;
	   
	   while($row = mysql_fetch_array($result))
	   {
		   $single_info = array();
		   $single_info["password"] = $row["password"];   
		   array_push($response["data"],$single_info);
	   }
   }
   
   echo json_encode($response);
   
?>   
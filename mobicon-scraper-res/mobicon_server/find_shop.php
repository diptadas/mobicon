<?php

   include 'connect.php';
   
   $division = $_GET['division'];
   
   $response["success"] = 0;
   $response["data"] = array();
   
   if($division==NULL) $sql = "SELECT * FROM shop_info";
   else $sql = "SELECT * FROM shop_info WHERE division = '" . $division . "'";
   
   $result = mysql_query($sql);
   
   if(!mysql_error()) 
   {
	   $response["success"] = 1;
	   
	   while($row = mysql_fetch_array($result))
	   {
		    $single_info = array();
		    $single_info["shop_name"] = $row["shop_name"];
			$single_info["division"] = $row["division"];  
			$single_info["address"] = $row["address"];  
			$single_info["lat"] = $row["lat"];  		   
			$single_info["long"] = $row["long"];  		   
		    array_push($response["data"],$single_info);
	   }
   }
   
   echo json_encode($response);
   
?>   
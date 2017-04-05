<?php

   include 'connect.php';
   
   $user_name = $_GET['user_name'];
   $mobile_id = $_GET['mobile_id'];
   $rating = $_GET['rating'];
   
   $response["success"] = 0;
   $response["data"] = array();
   
   $sql = "SELECT * FROM rating_info WHERE user_name = '" . $user_name . "' AND mobile_id = '" . $mobile_id ."'";
   
   $result = mysql_query($sql);
   
   if(mysql_error()) goto END;
   
   if(mysql_num_rows($result)>0)  $sql = "UPDATE rating_info SET rating = " . $rating . " WHERE user_name = '" . $user_name . "' AND mobile_id = '" . $mobile_id ."'";
   else $sql = "INSERT INTO rating_info VALUES('" . $user_name . "','" . $mobile_id . "'," . $rating . ")";
   
   $result = mysql_query($sql);
   
   if(mysql_error()) goto END;
   
   $response["success"] = 1;
   
END:   
   echo json_encode($response);
   
?>   
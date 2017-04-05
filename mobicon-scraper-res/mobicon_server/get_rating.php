<?php

   include 'connect.php';
   
   function getAvgRating($mobile_id)
   {
		$sql = "SELECT AVG(rating) AS avg_rating FROM rating_info WHERE mobile_id = '" . $mobile_id ."'";
   
		$result = mysql_query($sql);
   
		if(!mysql_error()) 
		{
			while($row = mysql_fetch_array($result)) 
				$rating = $row["avg_rating"];
		}
		
		if($rating == NULL) $rating = rand(35,45)/10;
		
		return $rating;
   }
   
?>   
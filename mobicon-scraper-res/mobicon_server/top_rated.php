<?php

	include 'get_rating.php';

	function getContents($url) // get content from url
	{
		$ch = curl_init();
		$timeout = 0; // no timeout
		curl_setopt ($ch, CURLOPT_URL, $url);
		curl_setopt ($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt ($ch, CURLOPT_CONNECTTIMEOUT, $timeout);
		$file_contents = curl_exec($ch);
		curl_close($ch);
		
		if($file_contents == NULL ) $file_contents = file_get_contents($url);
		
		return $file_contents;
	}
	
	$response=array();
	$response["success"] = 1;
	$response["data"] = array();
    
	$url="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20html%20where%20url%20%3D%20%22http%3A%2F%2Fwww.gsmarena.com%2Fresults.php3%3FsQuickSearch%3Dyes%26sName%3D%22&format=json";
	
    $json = getContents($url);
	
	if($json==NULL) goto ERROR;
	
    $data = json_decode($json);
	
	$tables=$data->query->results->body->div->div[0]->div[1]->div->div[2]->div->ul->li;
	
	if($tables==NULL) goto ERROR;

	foreach($tables as $item)
	{
		 $mobile_id=$item->a->href;
		 $mobile_id=current(explode(".php", $mobile_id));
		 
		 $img_url=$item->a->img->src;
		 
		 $title = current(explode( '-', $mobile_id));
		 $title = ucwords(str_replace("_", " ", $title));
		 
		 $tmp_array = array();
		 $tmp_array['title']=$title;
		 $tmp_array['mobile_id']=$mobile_id;
		 $tmp_array['img_url']=$img_url;
		 
		 $tmp_array['price']="N/A";
		 $tmp_array['released']="N/A";
		 
		 $tmp_array['rating'] = getAvgRating($mobile_id);
         
		 array_push($response["data"],$tmp_array);
	}
	
	goto END;
	
ERROR:
	$response["success"] = 0;
	
END:
	//$myfile = fopen("log_file.txt", "w") or die("Unable to open file!");
	//fwrite($myfile, $url);
	//fclose($myfile);
    
	echo json_encode($response);
	
?>
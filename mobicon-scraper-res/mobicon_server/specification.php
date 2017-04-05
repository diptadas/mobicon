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

	$mobile_id = $_GET['mobile_id'];
	
    $url="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20html%20where%20url=%22http://www.gsmarena.com/".$mobile_id.".php%22%20and%20xpath=%22//html/body/div[@id=%27wrapper%27]/div[@id=%27outer%27]/div[@id=%27body%27]/div%22&format=json";
	
	$fields=array("mobile_id", "title", "img_url", "rating", "Technology", "Status", "Dimensions", "Weight", "SIM", "Disp. Type", "Disp. Size", "Resolution", "Protection", "OS",
"Chipset", "CPU", "GPU", "Internal", "Card slot", "Primary Camera", "Secondary Camera", "Video", "WLAN", "Bluetooth", "GPS", "NFC", "Radio", "USB",
"Sensors", "Battery", "Colors", "price");

	$response=array();
	$response["success"] = 1;
	$response["data"] = array();
	
    $json = getContents($url);
	
	if($json==NULL) goto ERROR;
	
    $data = json_decode($json);
	
	$tables=$data->query->results->div->div[1]->table;
	
	if($tables==NULL) goto ERROR;
	
	$tmp_array = array();
	
	foreach($fields as $item) $tmp_array[$item]="N/A";
	
	$tmp_array["title"] = $data->query->results->div->div[0]->div->div[0]->h1->content;
	$tmp_array["img_url"] = $data->query->results->div->div[0]->div->div[1]->div->a->img->src;
	
	$tmp_array["mobile_id"] = $mobile_id;
	
	$tmp_array['rating'] = getAvgRating($mobile_id);
	
	foreach($tables as $item)
	{
		$item=$item->tbody->tr;
		
		if(isset($item[0]->th->content)) $title=$item[0]->th->content;
		else continue;
				
		
		foreach($item as $values)
		{
			if(isset($values->td[0]->a->content)) $key=$values->td[0]->a->content;
			else if(isset($values->td[0]->content)) $key=$values->td[0]->content;
			else continue;
			
			if(isset($values->td[1]->a->content) && $values->td[1]->a->content != "check quality") 	$val=$values->td[1]->a->content;
			else if(isset($values->td[1]->content)) 	$val=$values->td[1]->content;
			else $val="NA";
			
			if(!mb_detect_encoding($key, 'ASCII', true)) $key="Value";
			
			if($title=="Display" && $key=="Type") $key="Disp. Type";
			else if($title=="Display" && $key=="Size") $key="Disp. Size";
			else if($title=="Camera" && $key=="Primary") $key="Primary Camera";
			else if($title=="Camera" && $key=="Secondary") $key="Secondary Camera";
			else if($title=="Battery" && $key=="Value") $key="Battery";
			
			if(array_key_exists($key, $tmp_array)) $tmp_array[$key]=$val;
	
		}
	}
	
	array_push($response["data"],$tmp_array);
	
	goto END;
	
ERROR:
	$response["success"] = 0;
	
END:
	echo json_encode($response);
?>
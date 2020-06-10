<?php
use Kreait\Firebase\Messaging\CloudMessage;


$url = 'https://fcm.googleapis.com/fcm/send';

$servername = "db.sice.indiana.edu";
$username = "i494f19_team39";
$password = "my+sql=i494f19_team39";
$dbname = "i494f19_team39";

$conn = new mysqli($servername, $username, $password, $dbname);
if(!$conn){
	die("Failed to connect to MySQL: ".mysqli_connect_error());
}

$sql = "select userid, username from users";

$userData = array();

if($result = mysqli_query($conn, $sql)){
	$i = 0;
	while($row = $result->fetch_assoc()){
	
		$userData[$i]["username"] = $row["username"];
		$userData[$i]["userid"] = $row["userid"];
		$i++;
	}
}

$sql2 = "select shelfNot, s.name, su.expiration from shelfUserV2 as su, Shelf as s, users as u, household_users as hu where expiration != '0000-00-00' and (expiration between curdate() and curdate() + INTERVAL 2 WEEK) and s.shelfid = su.shelfid and u.userid = %s and hu.userID = u.userid and su.houseid = hu.houseid";
//echo json_encode($userData);
for($i = 0; $i < sizeof($userData); $i++){
	//echo "=-=-=-=-=-=-=-=-=-=-=\n\n".$userData[$i]["username"]."\n";
	$userData[$i]["ingredients"] = array();
	//echo $userData[$i];
	if($result = mysqli_query($conn, sprintf($sql2, $userData[$i]["userid"]))){
	//echo "\n\n\n\n".sprintf($sql2, $userData[$i]["userid"])."\n\n";
		//$bigStr = "Hi there! It's Keepin' It Fresh\n";
		//$bigStr .= "Here's your update on your shelf:\n\n";
		$bigStr = "";
		$j = 0;
		while($row = $result->fetch_assoc()){
			
			$userData[$i]["shelfNot"] =  $row["shelfNot"];
			$userData[$i]["ingredients"][$j]["expiration"]
			= $row["expiration"];
			$userData[$i]["ingredients"][$j]["name"] 
			= $row["name"];
			$j++;
			$bigStr .= $row["name"] . " will expire on ";
			$bigStr .= $row["expiration"]."\n";
		}
		//$bigStr .= "Have a great day!\n";
		if(sizeof($userData[$i]["ingredients"]) >0){
			$body = array('title' => 'Shelf Update!', 'body' => $bigStr);
			//echo $userData[$i]["username"]."keepinitfreshtest";
			$data = array('to' => '/topics/'.$userData[$i]["username"].'keepinitfreshtest', 'notification'=>$body);
			$options = array(
    				'http' => array(
        				'header'  => "Content-type: application/json\r\n".
					"Authorization: key=AAAA8mmTsuU:APA91bHN8JLiVRqu5G7aVNaDaSo3Grwh02qhcWRsbOSfd5o3vKFmKRq7IiQqFKUFgYYpqjsgSIvoCQRuBqyaZnpRHUQZk4KQTrbe2cYvcxP-211LM6iO88oQPSxvaVvYd5Nw0vO73uVx\r\n",
        				'method'  => 'POST',
       					'content' => json_encode($data)
    				)
			);

			if($userData[$i]["shelfNot"]){
				$context  = stream_context_create($options);
				$result = file_get_contents($url, false, $context);
				//echo $bigStr;
				//echo $result."\n\n";
			}
		}		
	} else {
		echo $conn->error;
	}
}
?>

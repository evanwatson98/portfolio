<?php

$json = file_get_contents("php://input");
$json_data = json_decode($json)->input;


$data = $json_data->data;

$houseId = $data->houseId; 
$userid = $data->userId;

$servername = "db.sice.indiana.edu";
$username = "i494f19_team39";
$password = "my+sql=i494f19_team39";
$dbname = "i494f19_team39";


$conn = new mysqli($servername, $username, $password, $dbname);
if(!$conn){
        die("Failed to connect to MySQL: ".mysqli_connect_error());
}

$sql = "select s.name, s.foodGroup, u.shelfid,u.userid,u.expiration,u.quantity,u.measurement,s.imageurl from Shelf s, shelfUserV2 u where s.shelfid = u.shelfid and houseid = $houseId";

if($result = mysqli_query($conn, $sql)){
	$returnData = array();
        $i = 0;
        while($row = $result->fetch_assoc()){
        	$returnData[$i] = $row;
                $i++;
       	}
        $returnJson->status = "true";
        $returnJson->message = "Successfully selected";
        $returnJson->data = $returnData;
} else {
        $returnJson->status = "false";
        $returnJson->message = $conn->error;
	$returnJson->houseID = $houseId;
}

$returnJson = json_encode($returnJson);

echo $returnJson;

?>

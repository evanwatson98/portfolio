<?php
$action = $_POST["action"];
$data = $_POST["data"];

$action = "selectrecipe";
$data = "yeet";

$servername = "db.sice.indiana.edu";
$username = "i494f19_team39";
$password = "my+sql=i494f19_team39";
$dbname = "i494f19_team39";

$conn = new mysqli($servername, $username, $password, $dbname);
if(!$conn){
	die("Failed to connect to MySQL: ".mysqli_connect_error());
}

$returnJson;

switch($action){
	
	case "login":
		$returnJson->status = "true";
		$returnJson->action = $action;
		$returnJson->message = "login";
		break;
	case "selectrecipe";
		$sql = "select name from recipe as r, userhasrecipe as uhr where r.recipeid=uhr.recipeid and uhr.userid = 5";
		if($result = mysqli_query($conn, $sql)){
			$returnData = array();
			$i = 0;
			while($row = $result->fetch_assoc()){
				$returnData[$i] = $row;
				$i++;
			}
			$returnJson->status = "true";
			$returnJson->action = $action;
			$returnJson->message = "Successfully selected";
			$returnJson->data = $returnData;	
		} else {
			$returnJson->status = "false";
			$returnJson->action = $action;
			$returnJson->message = $conn->error;
		}
		break;
	default:
		$returnJson->status = "false";
		$returnJson->action = $action;
		$returnJson->message = "Error: action '$action' not found.";
		break;
}

$returnJson = json_encode($returnJson);

echo $returnJson;

?>

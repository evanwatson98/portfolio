<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");

	
$uniqueToken = $_POST['uniqueToken'];

$sql = "SELECT h.houseID FROM household AS h WHERE uniqueToken = '$uniqueToken'";

$result = mysqli_query($con,$sql);

if ($result->num_rows > 0){
	while($row = $result->fetch_assoc()) {
		echo $row["houseID"];
	}
}elseif ($result->num_rows == 0){
	echo "None";
}

Else
	{ die('SQL Error: ' . mysqli_error($con)); }

mysqli_close($con);
?>
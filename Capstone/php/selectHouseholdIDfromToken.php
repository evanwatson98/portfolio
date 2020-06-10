<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");

$sanuniqueToken = mysqli_real_escape_string($con, $_POST['uniqueToken']);

$sql = "SELECT h.houseID FROM household AS h WHERE h.uniqueToken = '$sanuniqueToken'";

$result1 = mysqli_query($con,$sql);

if ($result1->num_rows > 0){
	while($row = $result1->fetch_assoc()) {
		echo $row["houseID"];
	}
Else
	{ die('SQL Error: ' . mysqli_error($con) ); }

mysqli_close($con);

?>
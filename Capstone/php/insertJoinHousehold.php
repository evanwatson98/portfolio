<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");

$houseID = $_POST['houseID'];
$userID = $_POST['userID'];

$sql = "INSERT INTO household_users(houseID, userID, mainUser) VALUES ('$houseID', '$userID', 'FALSE')";

if (mysqli_query($con, $sql))
	{echo "1 record added";}

Else
	{ die('SQL Error: ' . mysqli_error($con) ); }

mysqli_close($con);
?>
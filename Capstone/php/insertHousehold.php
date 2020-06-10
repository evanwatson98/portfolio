<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");
if(mysqli_connect_errno())
{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_connect_error() . "\n");}
else
{echo nl2br("Established Database Connection \n");}
	

$userID = $_POST['userID'];
$sanfhouseID = mysqli_real_escape_string($con, $_POST['houseID']);
$sanhouseName = mysqli_real_escape_string($con, $_POST['houseName']);
$sanuniqueToken = mysqli_real_escape_string($con, $_POST['uniqueToken']);

$sql = "INSERT INTO household(houseID, houseName, uniqueToken) VALUES ('$sanfhouseID','$sanhouseName','$sanuniqueToken')";
$q = "INSERT INTO household_users(houseID, userID, mainUser) VALUES ('$sanfhouseID', '$userID', 'FALSE')";

if (mysqli_query($con, $sql) AND mysqli_query($con, $q) )
	{echo "1 record added";}

Else
	{ die('SQL Error: ' . mysqli_error($con) ); }

mysqli_close($con);
?>
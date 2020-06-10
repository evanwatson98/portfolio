<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");
if(mysqli_connect_errno())
{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_connect_error() . "\n");}
else
{echo nl2br("Established Database Connection \n");}
	
$sanfhouseID = mysqli_real_escape_string($con, $_POST['houseID']);
$sanhouseName = mysqli_real_escape_string($con, $_POST['houseName']);
$sanuniqueToken = mysqli_real_escape_string($con, $_POST['uniqueToken']);

$userID = mysqli_real_escape_string($con, $_POST['userID']);
$sql = "INSERT INTO household(houseID, houseName, uniqueToken) VALUES ('$sanfhouseID','$sanhouseName','$sanuniqueToken')";

if (mysqli_query($con, $sql))
	{echo "1 record added";}

Else
	{ die('SQL Error: ' . mysqli_error($con) ); }

mysqli_close($con);
?>
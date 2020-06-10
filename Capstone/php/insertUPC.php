<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");
if(mysqli_connect_errno())
{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_connect_error() . "\n");}
else
{echo nl2br("Established Database Connection \n");}
	
$sanname = mysqli_real_escape_string($con, $_POST['name']);
$sanfoodGroup = mysqli_real_escape_string($con, $_POST['foodGroup']);
$sanupc = mysqli_real_escape_string($con, $_POST['upc']);
$sanmeasurements = mysqli_real_escape_string($con, $_POST['measurements']);
$allergens = mysqli_real_escape_string($con, $_POST['allergens']);
$dietary = mysqli_real_escape_string($con, $_POST['dietary']);

$sql = "INSERT INTO Shelf(name, foodGroup, upc, measurements, allergens, dietary) VALUES ('$sanname','$sanfoodGroup','$sanupc','$sanname','$allergens','$dietary')";

if (mysqli_query($con, $sql))
	{echo "1 record added";}

Else
	{ die('SQL Error: ' . mysqli_error($con) ); }

mysqli_close($con);
?>
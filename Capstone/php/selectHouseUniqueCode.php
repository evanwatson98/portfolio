<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");
if(mysqli_connect_errno())
{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_connect_error() . "\n");}
else
{echo nl2br("Established Database Connection \n");}
	
$uniqueToken = $_POST['uniqueToken'];
$sql = "SELECT h.uniqueToken FROM household AS h WHERE uniqueToken = '$uniqueToken'";

$result1 = mysqli_query($con,$sql);

if ($result1->num_rows > 0){
	echo "True";
}elseif ($result1->num_rows == 0){
	echo "None";
}

Else
	{ die('SQL Error: ' . mysqli_error($con)); }

mysqli_close($con);
?>
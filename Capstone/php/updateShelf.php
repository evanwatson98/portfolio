<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");


$houseid = $_POST['houseID'];
$shelfID = $_POST['shelfID'];
$userid = $_POST['userid'];
$foodName = $_POST['foodName'];
$foodQuantity = $_POST['foodQuantity'];
$foodMeasurement = $_POST['foodMeasurement'];
$foodExpiration = $_POST['foodExpiration'];
$foodGroup = $_POST['foodGroup'];
$foodType = $_POST['foodType'];

$sql = "UPDATE shelfUserV2 SET expiration = '$foodExpiration', quantity = '$foodQuantity', measurement = '$foodMeasurement' WHERE shelfid = '$shelfID' AND houseid = '$houseid'";
$sql2 = "UPDATE Shelf SET name = '$foodName', foodGroup = '$foodGroup', type = '$foodType' WHERE shelfID = '$shelfID'";

if (mysqli_query($con, $sql))
	{if (mysqli_query($con, $sql2)) {echo "1 record edited" . $houseid . ", " . $shelfID . "";}}
Else
	{ die('SQL Error: ' . mysqli_error($con)); }

mysqli_close($con);
?>
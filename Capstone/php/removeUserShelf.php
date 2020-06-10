<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");


$houseid = $_POST['houseid'];
$shelfID = $_POST['shelfID'];

$sql = "DELETE FROM shelfUserV2 WHERE houseid = '$houseid' AND shelfid = '$shelfID'";

if (mysqli_query($con, $sql))
	{echo "1 record removed" . $houseid . ", " . $shelfID . "";}
else
	{ die('SQL Error: ' . mysqli_error($con)); }

mysqli_close($con);
?>


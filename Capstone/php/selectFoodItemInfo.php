<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");
if(mysqli_connect_errno())
{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_connect_error() . "\n");}

$shelfid = $_POST['shelfid'];

$sql = "SELECT s.name, s.foodGroup FROM Shelf AS s WHERE shelfid = '$shelfid'";

$result1 = mysqli_query($con,$sql);

if ($result1->num_rows > 0){
	while($row = $result1->fetch_assoc()) {
		echo $row["name"] . "," . $row["foodGroup"] . "%";
	}
}elseif ($result1->num_rows == 0){
	echo "None";
}

Else
	{ die('SQL Error: ' . mysqli_error($con)); }

mysqli_close($con);
?>

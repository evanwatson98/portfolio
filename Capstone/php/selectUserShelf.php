<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");


if(mysqli_connect_errno())
{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_connect_error() . "\n");}

$userid = $_POST['userid'];

$sql = "select u.shelfid,u.userid,u.expiration,u.quantity,u.measurement,s.imageurl from shelf s, shelfUserV2 u where s.shelfid = u.shelfid and userid = '$userid'";
$result1 = mysqli_query($con,$sql);


if ($result1->num_rows > 0){
	while($row = $result1->fetch_assoc()) {
		echo $row["shelfid"] . "," . $row["userid"] . "," . $row["expiration"] . "," . $row["quantity"] . "," . $row["measurement"]. "," . $row["imageURL"] . "%";
		echo $image;
	}
}elseif ($result1->num_rows == 0){
	echo "None";
}


Else
	{ die('SQL Error: ' . mysqli_error($con)); }



mysqli_close($con);
?>



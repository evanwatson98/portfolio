<?php
$con = mysqli_connect("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
//connection check
if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySql:" . mysqli_connect_error(). "\n");}
else
	{echo nl2br("");}
	
		mysqli_close($con);
mysqli_close($con);
?>

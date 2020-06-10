<?php

$conn = mysqli_connect("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());

}
$userID = $_POST['userID'];

$intUserid = (int)$userID;
$home = "SELECT houseID from household_users where userID = '$intUserid'";
$result = mysqli_query($conn, $home);

if($result->num_rows == 0){
		echo ("Not Found --"."NA"."");
	  }
	  
if ($result = mysqli_query($conn, $home)) {
  // Fetch one and one row
  while ($row = mysqli_fetch_row($result)) {
	  if($result->num_rows > 0){
		echo ("Home Found --".$row[0]."");
	  }
 
  }mysqli_free_result($result);

}
	
mysqli_close($conn);
?> 

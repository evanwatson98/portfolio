<?php

$conn = mysqli_connect("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());

}
$userid = $_POST['userid'];


$q = "SELECT fullname, email, username, joindate from users where userid = '$userid'";
$result = mysqli_query($conn, $q);
$date = date("Y-m-d");

	
if ($result = mysqli_query($conn, $q)) {
  // Fetch one and one row
  while ($row = mysqli_fetch_row($result)) {
	  if($result->num_rows > 0){
		  echo ($row[0]. " //".$row[1]. " //" .$row[2]. " // You Joined On: " .$row[3]."");
	  }
 
  }mysqli_free_result($result);
}
	
mysqli_close($conn);
?> 


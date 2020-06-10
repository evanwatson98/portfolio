<?php

$conn = mysqli_connect("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());

}
$upc = $_POST['upc'];


$q = "SELECT name, foodGroup, type, imageURL from Shelf where upc = '$upc'";
$result = mysqli_query($conn, $q);

//$insert2 = "INSERT INTO Shelf(name, foodGroup, type, upc) VALUES('$foodName', '$foodGroup', '$foodType')";
if($result->num_rows == 0){
	  echo ("Not Found. Edit Below. --".$row[0]. " --".$row[1]. " --" .$row[2]. " --" .$row[3]."");
	  echo $upc;
}
	
		
if ($result = mysqli_query($conn, $q)) {
  // Fetch one and one row
  while ($row = mysqli_fetch_row($result)) {
	  if($result->num_rows > 0){
		  echo ("Item Found! --".$row[0]. " --".$row[1]. " --" .$row[2]. " --" .$row[3]. " --" .$row[4]."");
	  }
 
  }mysqli_free_result($result);
}
	
mysqli_close($conn);
?> 


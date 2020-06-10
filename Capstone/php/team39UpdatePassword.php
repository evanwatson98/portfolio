<?php

$conn = mysqli_connect("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());

}

$password = $_POST['password'];
$q = "UPDATE users SET password = SHA2('$password', 256)";

if($conn->query($q)=== TRUE){
	
echo "Reset Successful!";
}
else{
	echo "Error: " . $q . "<br>" . $conn->error;
}
$conn->close();
	
?> 

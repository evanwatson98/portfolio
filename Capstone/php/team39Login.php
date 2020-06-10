<?php

$conn = mysqli_connect("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());

}
$email = $_POST["email"];
$username = $_POST['username'];
$password = $_POST["password"];
$q = "SELECT * from users where (email = '$email' OR username = '$username') and password = SHA2('$password', 256)";
$result = mysqli_query($conn, $q);
$tt = "SELECT * from users where (email = '$email' OR username = '$username')";
$result2 = mysqli_query($conn, $tt);
if($result->num_rows > 0){
	echo "Login Success!";
} elseif($result2->num_rows == 0){
	echo "No Account Found";
} else{
	echo "Failed, username found but password incorrect.";
}

	
mysqli_close($conn);
?> 

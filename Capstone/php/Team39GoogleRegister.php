, d<?php

$conn = mysqli_connect("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());

}

$fullname = $_POST['fullname'];
$email = $_POST['email'];
$username = $_POST['username'];
$googleID = $_POST['googleID'];
$password = bin2hex(openssl_random_pseudo_bytes(2));
$tok = bin2hex(openssl_random_pseudo_bytes(2));

$q = "INSERT into users (fullname, email, username, password, token, googleID) VALUES ('$fullname', '$email', '$username', SHA2('$password', 256), '$tok', '$googleID')";
$update = "UPDATE users set googleID= '$googleID' where email='$email'";
$tt = "SELECT * from users where email = '$email'";
$xx = "SELECT * from users where username = '$username'";
$google =  "SELECT * from users where googleID = '$googleID'";
$comb =  "SELECT * from users where email = '$email' and googleID = '$googleID'";


$resultcomb = mysqli_query($conn, $comb);
$resultGoogle = mysqli_query($conn, $google);
$result2 = mysqli_query($conn, $tt);
$result3 = mysqli_query($conn, $xx);
if($resultcomb->num_rows > 0) {
	echo "Welcome Back!";
	
}else if($result2->num_rows > 0 and $conn->query($update)=== TRUE){
echo "Google Account Linked";

}else if($result2->num_rows ==0 and $conn->query($q)=== TRUE){
echo "Account Created Successfully";

}else{
	echo "Error: " . $q . "<br>" . $conn->error;
}
$conn->close();
	
?> 

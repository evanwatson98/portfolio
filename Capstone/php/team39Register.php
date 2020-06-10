<?php

$conn = mysqli_connect("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());

}

$fullname = $_POST['fullname'];
$email = $_POST['email'];
$username = $_POST['username'];
$password = $_POST['password'];
$date = date('m-d-Y');
$tok = bin2hex(openssl_random_pseudo_bytes(2));
$q = "INSERT into users (fullname, email, username, password, token, joindate) VALUES ('$fullname', '$email', '$username', SHA2('$password', 256), '$tok', '$date')";
$tt = "SELECT * from users where email = '$email'";
$xx = "SELECT * from users where username = '$username'";


$result2 = mysqli_query($conn, $tt);
$result3 = mysqli_query($conn, $xx);
if($result2->num_rows > 0) {
	echo "ERROR: Email Already in Use. Try Again.";
}
else if($result3->num_rows > 0) {
	echo "ERROR: Username Taken. Try Again.";
}
else if($result2->num_rows ==0 and $result3->num_rows ==0 and $conn->query($q)=== TRUE){
	
echo "Insert Successful";
}
else{
	echo "Error: " . $q . "<br>" . $conn->error;
}
$conn->close();
	
?> 

<?php  

$conn = mysqli_connect("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());

}
$tok = $_POST['token'];
$email = $_POST['email'];
$password = $_POST['password'];

	
$sql = "SELECT * from users where token = '$tok' and email = '$email'";
$tt = "UPDATE users SET password = SHA2('$password', 256) where token = '$tok' and email = '$email' ";

$result = mysqli_query($conn, $sql);


if($result->num_rows > 0 and $conn->query($tt)=== TRUE){
	echo "Reset Successful";
}
else{
	echo "Error: " . $tt . "<br>" . $conn->error;
}

		


$conn->close();
	
?> 

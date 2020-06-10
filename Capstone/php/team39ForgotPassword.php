<?php

$conn = mysqli_connect("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());

}


$email = $_POST['email'];
$password = $_POST['password'];
$tok = bin2hex(openssl_random_pseudo_bytes(2));
$sql = "SELECT * from users where email = '$email'";
$response = array($email, $tok);
$q = "UPDATE users SET token = '$tok' WHERE email = '$email'";
$url = "$tok";
//?email=$email&token=$tok


$result = mysqli_query($conn, $sql);




if (mysqli_query($conn, $q)) {
 echo "Your password reset email has been sent!";
}
	



	


	
if(mysqli_num_rows($result)>0)
{


	$row = mysqli_fetch_assoc($result);
	$to = "test";
	$subject = 'Your password reset link';
	
	$message = '<p>We recieved a password reset request. The link to reset your password is below. ';
	$message .= 'If you did not make this request, you can ignore this email</p>';
	$message .= '<p>Here is your password reset link:</br>';
	$message .= sprintf('<a href="http://cgi.sice.indiana.edu/~jplazony/reset.php?tok=$tok">%s</a></p>', $url, $url);
	$message .= sprintf('<p>"Thanks!"</p>', $tok);

	$message .= '<img src="http://cgi.sice.indiana.edu/~jplazony/croppedwhitelogo.jpg" alt="logo" /><br/>';
    
	$headers = "From: " . KEEPIN_IT_FRESH . " <" . keepinitfresh . ">\r\n";
	$headers .= "Reply-To: " . noreply . "\r\n";
	$headers .= "Content-type: text/html\r\n";
	mail($row["email"], $subject, $message, $headers);
	echo "";
	
} else
	echo "FAILED";
	

mysqli_close($conn);
?> 
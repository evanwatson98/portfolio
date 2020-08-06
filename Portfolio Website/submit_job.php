<!DOCTYPE html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>Evan's Portfolio</title>

	<!-- web icon -->
	<link rel="icon" href="web-icon.svg" type="image/svg+xml">
	<!-- default favicon -->
	<link rel="shortcut icon" href="web-icon.svg" type="image/svg+xml">
	<!-- for apple mobile devices -->
	<link rel="apple-touch-icon-precomposed" href="web-icon.svg" type="image/svg+xml" sizes="152x152">
	<link rel="apple-touch-icon-precomposed" href="web-icon.svg" type="image/svg+xml" sizes="120x120">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
	<link rel="stylesheet" href="portfolio.css" type="text/css">

	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Tangerine">
  </head>



  <body style="background-image: none";>
<!--	  GLOBAL-->
	<div class="nav-bar d-flex p-3" style="position: relative;">
		<a href="index.html">
			<div class="webNav pl-4 nav-link" style="width: 100%;">
				<img src="ew.svg" style="width: 40%; position: relative;" class="pl-4">
			</div>
		</a>
		
		<div class="nav-line webNav" style="width: .25%; height: 100%; background-color: white;"></div>
		
<!--
		<a href="home.html" style="text-decoration: none">
			<div class="webNav pl-4 nav-link" style="width: 100%;">
				<p style="color: white; position: relative; font-size: xx-large;">Home</p>
			</div>
	  	</a>
-->
		
		<a href="index.html" style="text-decoration: none">
			<div class="webNav pl-4 nav-link" style="width: 100%;">
				<p style="color: white; position: relative; font-size: xx-large;">Discover</p>
			</div>
	  	</a>
		
		<a href="hire.html" style="text-decoration: none">
			<div class="webNav pl-4 nav-link" style="width: 50%;">
				<p class="pl-4" style="color: white; position: relative; font-size: xx-large;">Hire</p>
			</div>
	  	</a>
		
<!--
		<a href="blog.html" style="text-decoration: none">
			<div class="webNav pl-4 nav-link" style="width: 50%;">
				<p style="color: white; position: relative; font-size: xx-large;">Blog</p>
			</div>
	  	</a>
-->
		
		<!--	  Phones-->
	  
		<div id="mySidenav" class="sidenav">
		  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
<!--		  <a href="#">Home</a>-->
		  <a href="index.html">Discover</a>
		  <a href="hire.html">Hire</a>
<!--		  <a href="#">Blog</a>-->
		</div>
		
		<div id="main">
		  <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; </span>
		</div>
		
		<img src="ew.svg" class="phoneLogo">		

	  </div>

	  <div class="confirmBody d-flex justify-content-center">
		  
	</div>
	<div class="d-flex justify-content-center" style="width:100%;">
	<?php
		$servername = "localhost";
		$username = "root";
		$password = "buildmyportfolio324";
		$db = "hire";
		// Create connection
		$con = mysqli_connect($servername, $username, $password,$db);
		if(!$con)
		{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_connect_error() . "\n");}
		$fname = $_POST['fname'];
		$lname = $_POST['lname'];
		$email = $_POST['email'];
		$phone = $_POST['phone'];
		$company = $_POST['company'];
		$jobTitle = $_POST['jobTitle'];
		$jobDescription = $_POST['jobDescription'];
		$skills = $_POST['skills'];
		$allSkills - $_POST['allSkills'];

		echo $allSkills;

		$mysql1 = "INSERT INTO jobs(title,description, skills) VALUES( '$jobTitle', '$jobDescription', '$skills')";
		$mysql2 = "INSERT INTO employers(fname,lname, company, email, phone) VALUES('$fname', '$lname', '$company', '$email', '$phone')";

		if (mysqli_query($con, $mysql1)){
			if (mysqli_query($con, $mysql2)){}
			Else
			{ die('SQL Error 2: ' . mysqli_error($mysql2) ); }
		}Else
		{ die('SQL Error 1: ' . mysqli_error($mysql1) ); }

		echo '<div class="thanks"style="color: blue; font-size: 30px; font-wight:bold;"> Thank You '. $fname ." " . $lname . "</div>";
	// $sansection = htmlspecialchars(mysqli_real_escape_string($conn, $_POST['fname']));
	mysqli_close($con);
	?>
	</div>




	  <div class="d-flex justify-content-center" style="background-color: white; width:100%;">
	  	<img src="handshakeColor.svg" style="width: 60%;">
	</div>

	<div class="d-flex justify-content-center" style="width:100%;">
		<div class="shortly" style="color: orange; font-size: 30px; font-wight:bold;"> I will be in touch with you shortly </div>
	</div>
	<script src="portfolio.js"></script>
	</body>
</html>
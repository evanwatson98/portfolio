<?php
$conn = mysqli_connect("db.sice.indiana.edu","i308s19_team12","my+sql=i308s19_team12","i308s19_team12");
			
if(!$conn){
	die("Connection failed: " . mysqli_connect_error());
}
//Grab POST Data			
//Create SQL Statement

	$sancourse = htmlspecialchars(mysqli_real_escape_string($conn, $_POST['course']));
	{echo nl2br($sancourse);}
	
	$sql = "SELECT CONCAT(Faculty.name_first, ' ', Faculty.name_middle, ' ', Faculty.name_last) AS Name FROM Faculty WHERE NOT EXISTS ( SELECT * FROM Scheduled WHERE Faculty.facultyID = Scheduled.facultyID AND Scheduled.courseID = '$sancourse')";
    $result = mysqli_query($conn, $sql);

    //check for error on insert
    if (!$result){
		die('Error: ' . mysqli_error($conn)); }

    if (mysqli_num_rows($result) > 0) {

        // output data of each row

        echo "<table border = '1'>";

        echo "<tr><th>Name</th></tr>";

        while($row = mysqli_fetch_assoc($result)) {

            echo "<tr><td>" . $row["Name"] . "</td></tr>";

        }

    } else {

        echo "0 results";

    }

    mysqli_close($con);

    ?>

<?php
$conn = mysqli_connect("db.sice.indiana.edu","i308s19_team12","my+sql=i308s19_team12","i308s19_team12");
			
if(!$conn){
	die("Connection failed: " . mysqli_connect_error());
}
//Grab POST Data			
//Create SQL Statement

	$sansection = htmlspecialchars(mysqli_real_escape_string($conn, $_POST['section']));
	
	$sql="SELECT DISTINCT CONCAT(Student.last_name, '  ',Student.first_name) AS name, ROUND(AVG(Grade.GPA),2) AS GPA FROM Section, Student, Section_Enroll, Grade WHERE Section.sectionID = Section_Enroll.sectionID AND Student.studentID = Section_Enroll.studentID AND Section.sectionID = '$sansection' AND Section_Enroll.grade = Grade.grade GROUP BY name, Section_Enroll.grade WITH ROLLUP";

    $result = mysqli_query($conn, $sql);

    //check for error on insert
    if (!$result){
		die('Error: ' . mysqli_error($conn)); }

    if (mysqli_num_rows($result) > 0) {

        // output data of each row

        echo "<table border = '1'>";

        echo "<tr><th>name</th> <th>GPA</th></tr>";

        while($row = mysqli_fetch_assoc($result)) {

            echo "<tr><td>" . $row["name"] . "</td><td>". $row["GPA"] . "</td></tr>";

        }

    } else {

        echo "0 results";

    }

    mysqli_close($con);

    ?>

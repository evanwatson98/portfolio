<?php
$conn = mysqli_connect("db.sice.indiana.edu","i308s19_team12","my+sql=i308s19_team12","i308s19_team12");
			
if(!$conn){
	die("Connection failed: " . mysqli_connect_error());
}
//Grab POST Data			
//Create SQL Statement

	$sanbuilding = htmlspecialchars(mysqli_real_escape_string($conn, $_POST['building']));
	$sandate = htmlspecialchars(mysqli_real_escape_string($conn, $_POST['date']));
	$santime = htmlspecialchars(mysqli_real_escape_string($conn, $_POST['time']));

	$sql = "SELECT DISTINCT CONCAT(Faculty.name_first,' ', Faculty.name_last) AS faculty_name, CONCAT(Student.first_name, ' ', Student.last_name) AS student_name,'' AS staff_name FROM Student, Faculty, Building, Section, Scheduled, Staff, Room, Section_Enroll WHERE Building.address = Room.address AND Scheduled.sectionID = Section.sectionID AND Scheduled.roomID = Room.roomID AND Scheduled.facultyID = Faculty.facultyID AND Section_Enroll.studentID = Student.studentID AND Section_Enroll.sectionID = Section.sectionID AND Section.time = '$santime' AND ('$sandate' BETWEEN Section.start_date AND Section.end_date) AND Building.address = '$sanbuilding' UNION SELECT  DISTINCT CONCAT(Faculty.name_first,' ', Faculty.name_last) AS faculty_name, '' AS student_name, CONCAT(Staff.first_name, ' ', Staff.last_name) AS staff_name FROM Section, Faculty, Building, Room, Staff WHERE Faculty.roomID = Room.roomID AND Room.address = Building.address AND Staff.roomID = Room.roomID AND Building.address = '$sanbuilding'";
    $result = mysqli_query($conn, $sql);

    //check for error on insert
    if (!$result){
		die('Error: ' . mysqli_error($conn)); }

    if (mysqli_num_rows($result) > 0) {

        // output data of each row

        echo "<table border = '1'>";

        echo "<tr><th>Faculty_Name</th><th>Student_Name</th><th>Staff_Name</th></tr>";

        while($row = mysqli_fetch_assoc($result)) {

            echo "<tr><td>" . $row["faculty_name"] . "</td><td>" . $row["student_name"] . "</td><td>" . $row["staff_name"] . "</td></tr>";

        }

    } else {

        echo "0 results";

    }

    mysqli_close($conn);

    ?>

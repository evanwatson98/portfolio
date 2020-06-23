<?php
$conn = mysqli_connect("db.sice.indiana.edu","i308s19_team12","my+sql=i308s19_team12","i308s19_team12");
			
if(!$conn){
	die("Connection failed: " . mysqli_connect_error());
}
//Grab POST Data			
//Create SQL Statement

	$sanadvisor = htmlspecialchars(mysqli_real_escape_string($conn, $_POST['advisor']));

		$sql = "SELECT CONCAT(Student.first_name, ' ', Student.middle_name, ' ',Student.last_name) AS name, Major.majorID
FROM Student, Major, Staff, Student_Major, Staff_Students
WHERE Student_Major.studentID = Student.studentID AND Student_Major.majorID = Major.majorID AND Staff_Students.staffID = Staff.staffID AND Staff_Students.studentID = Student.studentID AND Staff.staffID = $sanadvisor
GROUP BY Student.studentID
ORDER BY name
";
    $result = mysqli_query($conn, $sql);

    //check for error on insert
    if (!$result){
		die('Error: ' . mysqli_error($conn)); }

    if (mysqli_num_rows($result) > 0) {

        // output data of each row

        echo "<table border = '1'>";

        echo "<tr><th>Name</th><th>majorID</th></tr>";

        while($row = mysqli_fetch_assoc($result)) {

            echo "<tr><td>" . $row["name"] ."</td><td>". $row["majorID"]. "</td></tr>";

        }

    } else {

        echo "0 results";

    }

    mysqli_close($conn);

    ?>

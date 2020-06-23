<?php
$conn = mysqli_connect("db.sice.indiana.edu","i308s19_team12","my+sql=i308s19_team12","i308s19_team12");
			
if(!$conn){
	die("Connection failed: " . mysqli_connect_error());
}
//Grab POST Data			
//Create SQL Statement

	$sanclass = htmlspecialchars(mysqli_real_escape_string($conn, $_POST['class']));
	
	$sql = "SELECT CONCAT(Student.last_name, '  ', Student.first_name) AS Name, Student_Email.email AS email, Student_Phone.phone AS phone
FROM Student_Email, Student_Phone, Student, Course, Scheduled
WHERE Course.courseID = '$sanclass' AND Student.studentID = Student_Email.studentID AND Student.studentID = Student_Phone.studentID
GROUP BY Name";
    $result = mysqli_query($conn, $sql);

    //check for error on insert
    if (!$result){
		die('Error: ' . mysqli_error($conn)); }

    if (mysqli_num_rows($result) > 0) {

        // output data of each row

        echo "<table border = '1'>";

        echo "<tr><th>Name</th><th>email</th><th>phone</th></tr>";

        while($row = mysqli_fetch_assoc($result)) {

            echo "<tr><td>" . $row["Name"] . "</td><td>" . $row["email"] . "</td><td>" . $row["phone"] . "</td></tr>";

        }

    } else {

        echo "0 results";

    }

    mysqli_close($conn);

    ?>

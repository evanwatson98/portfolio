<?php
$conn = mysqli_connect("db.sice.indiana.edu","i308s19_team12","my+sql=i308s19_team12","i308s19_team12");
			
if(!$conn){
	die("Connection failed: " . mysqli_connect_error());
}
//Grab POST Data			
//Create SQL Statement

		$sql = "SELECT Course.courseID,  Section.time, Section_Day.day
FROM Section, Course, Scheduled, Section_Day
WHERE Section.sectionID = Scheduled.sectionID AND Course.courseID = Scheduled.courseID AND Section.title = 'Lab' AND Section_Day.sectionID = Section.sectionID;
";
    $result = mysqli_query($conn, $sql);

    //check for error on insert
    if (!$result){
		die('Error: ' . mysqli_error($conn)); }

    if (mysqli_num_rows($result) > 0) {

        // output data of each row

        echo "<table border = '1'>";

        echo "<tr><th>Course ID</th><th>Time</th><th>Day</th></tr>";

        while($row = mysqli_fetch_assoc($result)) {

            echo "<tr><td>" . $row["courseID"] ."</td><td>". $row["time"]. "</td><td>" . $row["day"] ."</td></tr>";

        }

    } else {

        echo "0 results";

    }

    mysqli_close($conn);

    ?>

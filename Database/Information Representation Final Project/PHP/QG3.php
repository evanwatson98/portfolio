<?php
$conn = mysqli_connect("db.sice.indiana.edu","i308s19_team12","my+sql=i308s19_team12","i308s19_team12");
			
if(!$conn){
	die("Connection failed: " . mysqli_connect_error());
}
//Grab POST Data			
//Create SQL Statement

		$sql = "SELECT CONCAT(Faculty.name_first, ' ', Faculty.name_middle, ' ', Faculty.name_last) AS name, Faculty.office_phone, Faculty_Email.email AS email
FROM Faculty, Faculty_Email
WHERE Faculty.rank = 'chairperson' AND Faculty.facultyID = Faculty_Email.facultyID;
";
    $result = mysqli_query($conn, $sql);

    //check for error on insert
    if (!$result){
		die('Error: ' . mysqli_error($conn)); }

    if (mysqli_num_rows($result) > 0) {

        // output data of each row

        echo "<table border = '1'>";

        echo "<tr><th>Name</th><th>Office Phone</th><th>Email</th></tr>";

        while($row = mysqli_fetch_assoc($result)) {

            echo "<tr><td>" . $row["name"] ."</td><td>". $row["office_phone"]. "</td><td>" . $row["email"] ."</td></tr>";

        }

    } else {

        echo "0 results";

    }

    mysqli_close($conn);

    ?>

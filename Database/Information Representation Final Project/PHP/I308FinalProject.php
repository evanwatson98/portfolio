<!DOCTYPE html>
<html>
	<body>
		<h2>Evan Watson Information Representation Final Project </h2>
		<h3>Queries</h3>

		<h4>1b) Produce a class roster for a *specified section* sorted by studentâ€™s last name, first name. At the end, include the average grade (GPA for the class.)</h4>
		<br>
		<form action="Q1b.php" method="POST">
		Section: <select name="section" required><br>

			<?php
			$con = mysqli_connect("db.sice.indiana.edu", "i308s19_team12", "my+sql=i308s19_team12", "i308s19_team12");
			if (!$con) {
				die("Connection failed: " . mysqli_connect_error());
			}
			$result = mysqli_query($con,"SELECT * FROM Section; ");
			while ($row = mysqli_fetch_assoc($result)) {
                  unset($sectionID);
                  $sectionID = $row['sectionID'];
                  
                  echo '<option value="'.$sectionID.'">'.$sectionID.'</option>';
			}
			?> 
		</select>
		<br>
		<input type="submit" value="Submit Section"><Br>
		</form>
		<h4>3b) Produce a list of faculty who have never taught a *specified course*</h4>
		<br>
		<form action="Q3b.php" method="POST">
		Course: <select name="course" required><br>

			<?php
			$con = mysqli_connect("db.sice.indiana.edu", "i308s19_team12", "my+sql=i308s19_team12", "i308s19_team12");
			if (!$con) {
				die("Connection failed: " . mysqli_connect_error());
			}
			$result = mysqli_query($con,"SELECT * FROM Course; ");
			while ($row = mysqli_fetch_assoc($result)) {
                  unset($courseID);
                  $courseID = $row['courseID'];
                  
                  echo '<option value="'.$courseID.'">'.$courseID.'</option>';
			}
			?>
		</select>
		<br>
		<input type="submit" value="Submit Course"><Br>
		</form>
		<br>
		<h4>5b) Produce a chronological list of all courses taken by a *specified student*. Show grades earned. Include overall hours taken and GPA at the end.</h4>
		<br>
		<form action="Q5b.php" method="POST">
		Student: <select name="student" required><br>

			<?php
			$con = mysqli_connect("db.sice.indiana.edu", "i308s19_team12", "my+sql=i308s19_team12", "i308s19_team12");
			if (!$con) {
				die("Connection failed: " . mysqli_connect_error());
			}
			$result = mysqli_query($con,"SELECT * FROM Student; ");
			while ($row = mysqli_fetch_assoc($result)) {
                  unset($studentID);
                  $studentID = $row['studentID'];
                  $name = $row['first_name'] . " " .$row['last_name'];

                  echo '<option value="'.$studentID.'">'.$name.'</option>';
			}
			?>
		</select>
		<br>
		<input type="submit" value="Submit Student"><Br>
		</form>
		<br>
		<h4>6c) Produce a list of students and faculty who were in a *particular building* at a *particular time*. Also include in the list faculty and advisors who have offices in that building. </h4>
		<br>
		<form action="Q6c.php" method="POST">
		Building: <select name="building" required><br>

			<?php
			$con = mysqli_connect("db.sice.indiana.edu", "i308s19_team12", "my+sql=i308s19_team12", "i308s19_team12");
			if (!$con) {
				die("Connection failed: " . mysqli_connect_error());
			}
			$result = mysqli_query($con,"SELECT * FROM Building; ");
			while ($row = mysqli_fetch_assoc($result)) {
                  unset($address);
                  $address = $row['address'];
                  
                  echo '<option value="'.$address.'">'.$address.'</option>';
			}
			?>
		</select>
		<br>
		Date:<input type = "date" name = "date" min = "1900-01-01" max = "2025-01-31" required><br>

		Time: <select name="time" required><br>

			<?php
			$con = mysqli_connect("db.sice.indiana.edu", "i308s19_team12", "my+sql=i308s19_team12", "i308s19_team12");
			if (!$con) {
				die("Connection failed: " . mysqli_connect_error());
			}
			$result = mysqli_query($con,"SELECT DISTINCT Section.time FROM Section; ");
			while ($row = mysqli_fetch_assoc($result)) {
                  unset($time);
                  $time = $row['time'];
                  
                  echo '<option value="'.$time.'">'.$time.'</option>';
			}
			?>
		</select>
		<br>
		<input type="submit" value="Submit Building"><Br>
		</form>
		<h4>7a) Produce an alphabetical list of students with their majors who are advised by a *specified advisor*.</h4>
		<br>
		<form action="Q7a.php" method="POST">
		Advisor: <select name="advisor" required><br>

			<?php
			$con = mysqli_connect("db.sice.indiana.edu", "i308s19_team12", "my+sql=i308s19_team12", "i308s19_team12");
			if (!$con) {
				die("Connection failed: " . mysqli_connect_error());
			}
			$result = mysqli_query($con,"SELECT * FROM Staff; ");
			while ($row = mysqli_fetch_assoc($result)) {
                  unset($staffID);
                  $staffID = $row['staffID'];
                  $name = $row['first_name'] . " " .$row['last_name'];
				  
                  echo '<option value="'.$staffID.'">'.$name.'</option>';
			}
			?>
		</select>
		<br>
		<input type="submit" value="Submit Advisor"><Br>
		</form>
		<h3>Additional Group Curated Queries</h3>
		<h4>1) for a specific class, return student emails & phone numbers for contact purposes</h4>
		<br>
		<form action="QG1.php" method="POST">
		Class: <select name="class" required><br>

			<?php
			$con = mysqli_connect("db.sice.indiana.edu", "i308s19_team12", "my+sql=i308s19_team12", "i308s19_team12");
			if (!$con) {
				die("Connection failed: " . mysqli_connect_error());
			}
			$result = mysqli_query($con,"SELECT * FROM Course; ");
			while ($row = mysqli_fetch_assoc($result)) {
                  unset($courseID);
                  $courseID = $row['courseID'];
                  
                  echo '<option value="'.$courseID.'">'.$courseID.'</option>';
			}
			?>
		</select>
		<br>
		<input type="submit" value="Submit Class"><Br>
		</form>
		<br>
		<h4>2) Produce a list of courses that have a 'Lab' section and their time as well as the days of the week that it meets </h4>
		<br>
		<form action="QG2.php" method="POST">
		<input type="submit" value="Select ALL Lab sections"><Br>
		</form>
		<br>
		<h4>3) Check to find contact (Name, phone, email(s)) information for Chairperson each department has</h4>
		<br>
		<form action="QG3.php" method="POST">
		<input type="submit" value="Select ALL chairpersons"><Br>
		</form>
		<br>

		
	</body>
</html>

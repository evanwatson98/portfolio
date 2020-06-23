<?php
$conn = mysqli_connect("db.sice.indiana.edu","i308s19_team12","my+sql=i308s19_team12","i308s19_team12");
			
if(!$conn){
	die("Connection failed: " . mysqli_connect_error());
}
//Grab POST Data			
//Create SQL Statement

	$sanstudent = htmlspecialchars(mysqli_real_escape_string($conn, $_POST['student']));
	
	$sql = "SELECT DISTINCT c.courseID, c.course_num, g.grade, c.credit_hours, g.GPA AS GPA FROM Course as c, Grade as g, Student as Stu, Section as s, Section_Enroll as se, Scheduled as sc WHERE Stu.studentID = '$sanstudent' AND se.sectionID = s.sectionID AND se.grade = g.grade AND c.courseID = sc.courseID AND Stu.studentID = se.studentID AND sc.sectionID = s.sectionID UNION SELECT 'OVERALL GPA:','','','', ROUND(SUM(Grade.GPA* Course.credit_hours)/SUM(IF(Section_Enroll.grade = 'NA', NULL, Course.credit_hours)),2) AS GPA FROM Grade, Student, Course, Section, Section_Enroll, Scheduled WHERE Section_Enroll.sectionID = Section.sectionID AND Section_Enroll.grade = Grade.grade AND Course.courseID = Scheduled.courseID AND Student.studentID = Section_Enroll.studentID AND Scheduled.sectionID = Section.sectionID AND Student.studentID = '$sanstudent'";

    $result = mysqli_query($conn, $sql);

    //check for error on insert
    if (!$result){
		die('Error: ' . mysqli_error($conn)); }

    if (mysqli_num_rows($result) > 0) {

        // output data of each row

        echo "<table border = '1'>";

        echo "<tr><th>CourseID</th><th>Course_Num</th><th>Grade</th><th>Credit_Hours</th><th>GPA</th></tr>";

        while($row = mysqli_fetch_assoc($result)) {

            echo "<tr><td>" . $row["courseID"] . "</td><td>" . $row["course_num"] . "</td><td>" . $row["grade"] . "</td><td>" . $row["credit_hours"] . "</td><td>" . $row["GPA"] . "</td></tr>";

        }

    } else {

        echo "0 results";

    }
	
	mysqli_close($conn);

    ?> 
	
	
<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");
if(mysqli_connect_errno())
{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_connect_error() . "\n");}
else
{echo nl2br("Established Database Connection \n");}
	

$sql = "SELECT h.uniqueToken FROM household AS h";

$result1 = mysqli_query($conn,$sql);

if (mysqli_num_rows($result1) > 0){
	
	while($row = mysqli_fetch_assoc($result1)) {
		echo "selected";
	}

}Else
	{ die('SQL Error: ' . mysqli_error($con) ); }

mysqli_close($con);
?>

	
	<?php
$con = mysqli_connect("db.sice.indiana.edu","i494f19_team39","my+sql=i494f19_team39","i494f19_team39");
if(mysqli_connect_errno())
{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_connect_error() . "\n");}
else
{echo nl2br("Established Database Connection \n");}
	

$sql = "SELECT h.uniqueToken FROM household AS h";

$result = mysqli_query($con, $sql)

if (mysqli_num_rows($result1) > 0)
	{echo "test1";}

Else
	{ die('SQL Error: ' . mysqli_error($con) ); }

mysqli_close($con);
?>

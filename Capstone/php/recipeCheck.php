<?php
	$con = new mysqli("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
	//connection check
	if (mysqli_connect_errno())
		{echo nl2br("Failed to connect to MySql:" . mysqli_connect_error(). "\n");}
	else
		{echo nl2br("");}

	$recipeid = $_POST['recipeid'];
	$userid = $_POST['userid'];
	
	
	$intRecipeid = (int)$recipeid;
  
	
	$haveEnoughOf= "select su.shelfid as id, s.name, su.quantity as userQty, su.measurement as userMeas, rhf.quantity as recipeQty, rhf.measurement as recipeMeas
	from shelfUserV2 as su, Shelf as s, recipe as r, recipeHasFoodV2 as rhf
	where r.recipeid = rhf.recipeid
	and rhf.shelfid = su.shelfid
	and s.name in (select s.name from Shelf as s where rhf.shelfid = s.shelfid and s.shelfid = su.shelfid)
	and su.measurement in (select su.measurement from shelfUserV2 as su where rhf.shelfid = s.shelfid and s.shelfid = su.shelfid)
	and rhf.measurement in (select rhf.measurement from recipeHasFoodV2 as rhf where rhf.shelfid = s.shelfid and s.shelfid = su.shelfid)
	and su.quantity >= rhf.quantity
	and r.recipeid = $intRecipeid
	and su.userid = $userid";
	if(!($sufficient = mysqli_query($con, $haveEnoughOf))){
		$returnJson["error"] = $con->error;
	};
	
	$dontHaveEnoughOf= "SELECT su.shelfid as id, s.name, su.quantity AS userQty, su.measurement AS userMeas, rhf.quantity AS recipeQty, rhf.measurement AS recipeMeas
	FROM shelfUserV2 as su, Shelf as s, recipe as r, recipeHasFoodV2 as rhf
	WHERE r.recipeid = rhf.recipeid
	AND rhf.shelfid = su.shelfid
	AND s.name IN (SELECT s.name FROM Shelf as s WHERE rhf.shelfid = s.shelfid AND s.shelfid = su.shelfid)
	AND su.measurement IN (SELECT su.measurement FROM shelfUserV2 as su WHERE rhf.shelfid = s.shelfid AND s.shelfid = su.shelfid)
	AND rhf.measurement IN (SELECT rhf.measurement FROM recipeHasFoodV2 as rhf WHERE rhf.shelfid = s.shelfid AND s.shelfid = su.shelfid)
	AND su.quantity < rhf.quantity
	AND r.recipeid = $intRecipeid
	AND su.userid = $userid";
	$insufficient = mysqli_query($con, $dontHaveEnoughOf);
	
	$haveNoneOf= "SELECT DISTINCT s.shelfid, s.name AS foodMissing, rhf.quantity AS recipeQty, rhf.measurement
	FROM shelfUserV2 as su, Shelf as s, recipe as r, recipeHasFoodV2 as rhf
	WHERE s.name NOT IN (SELECT s.name FROM shelfUserV2 as su WHERE rhf.shelfid = s.shelfid AND s.shelfid = su.shelfid)
	AND s.shelfID = rhf.shelfid
	AND rhf.recipeid = $intRecipeid
	AND su.userid = $userid";
	$none = mysqli_query($con, $haveNoneOf);

	$returnJson = array();

	$returnJson["sufficient"] = array();
	$returnJson["insufficient"] = array();
	$returnJson["missing"] = array();
	
	$returnJson["error"] = $con->error;


	$i = 0;
	if (mysqli_num_rows($sufficient) > 0) {
	
	while($row = mysqli_fetch_assoc($sufficient)) {
		//$returnJson["row"][$i] = $row;
		$returnJson["sufficient"][$i]["userQty"] = $row["userQty"];
		$returnJson["sufficient"][$i]["userMeas"] = $row["userMeas"];
		$returnJson["sufficient"][$i]["name"] = $row["name"];
		$returnJson["sufficient"][$i]["recipeQty"] = $row["recipeQty"];
		$returnJson["sufficient"][$i]["recipeMeas"] = $row["recipeMeas"];
		$returnJson["sufficient"][$i]["id"] = $row["id"];
		$i++;
		
	
	}}

	$i = 0;
	if (mysqli_num_rows($insufficient) > 0) {
	//echo "<p>DON'T HAVE ENOUGH OF</p>";
	while($row2 = mysqli_fetch_assoc($insufficient)) {
		$returnJson["insufficient"][$i]["userQty"] = $row2["userQty"];
		$returnJson["insufficient"][$i]["userMeas"] = $row2["userMeas"];
		$returnJson["insufficient"][$i]["name"] = $row2["name"];
		$returnJson["insufficient"][$i]["recipeQty"] = $row2["recipeQty"];
		$returnJson["insufficient"][$i]["recipeMeas"] = $row2["recipeMeas"];
		$returnJson["insufficient"][$i]["id"] = $row2["id"];
		$i++;
	/*
echo "<p>You have " . $row["userQty"] . " " . $row["userMeas"]. " of " . $row["name"]. ", and this recipe uses " . $row["recipeQty"]. " " .  $row[recipeMeas]. ".</p>";
	*/	
	}}
	
	$i = 0;
	if (mysqli_num_rows($none) > 0) {
	//echo "<p>HAVE NONE OF</p>";
	while($row3 = mysqli_fetch_assoc($none)) {
		$returnJson["missing"][$i]["name"] = $row3["foodMissing"];
		$returnJson["missing"][$i]["recipeQty"] = $row3["recipeQty"];
		$returnJson["missing"][$i]["recipeMeas"] = $row3["measurement"];
		
		$i++;
/*
	echo "<p> You have no " . $row["foodMissing"]. ", and this recipe needs " . $row["recipeQty"]. " " . $row["measurement"] . " of it.</p>";
	*/
	}}
	$returnJson["userid"] = $userid;
	$returnJson["recipeid"] = $recipeid;
	mysqli_close($con);
	echo json_encode($returnJson);
?>

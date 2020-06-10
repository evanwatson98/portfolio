<?php
$con = mysqli_connect("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
//connection check
if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySql:" . mysqli_connect_error(). "\n");}
else
	{echo nl2br("");}
   
	//getting post info from java
	$userid = $_POST['userid'];
	$houseID = $_POST['houseID'];
	$foodName = $_POST['foodName'];
	$foodQuantity = $_POST['foodQuantity'];
	$foodMeasurement = $_POST['foodMeasurement'];
	$foodExpiration = $_POST['foodExpiration'];
	$foodGroup = $_POST['foodGroup'];
	$foodType = $_POST['foodType'];
	$upc = $_POST['upc'];
	
	$intQuantity = (int)$foodQuantity;
	$intUserid = (int)$userid;

	//checking if food has already been created
	$foodCheck = mysqli_query($con, "SELECT shelfID FROM Shelf WHERE name ='$foodName' AND upc='$upc'");
	$foodCheckRow = mysqli_fetch_array($foodCheck);
	$result1 = $foodCheckRow[0];
   
   
	//checking if food is already in user's shelf
	$shelfCheck = mysqli_query($con, "SELECT shelfid from shelfUserV2 WHERE houseID = '$houseID' AND shelfid = (SELECT shelfID FROM Shelf WHERE name ='$foodName' AND upc = '$upc')");
	$shelfCheckRow = mysqli_fetch_array($shelfCheck);
	$result2 = $shelfCheckRow[0];

	$unitInsert = "INSERT INTO shelfUserV2(shelfid, userid, houseID, expiration, quantity, measurement) VALUES((SELECT shelfID FROM Shelf WHERE name ='$foodName' AND foodGroup = '$foodGroup' AND type = '$foodType'), $intUserid, '$houseID',STR_TO_DATE('$foodExpiration','%m-%d-%Y'), $intQuantity, '$foodMeasurement')";
	
	$literInsert = "INSERT INTO shelfUserV2(shelfid, userid, houseID, expiration, quantity, measurement, grams) VALUES((SELECT shelfID FROM Shelf WHERE name ='$foodName' AND foodGroup = '$foodGroup' AND type = '$foodType'), $intUserid, '$houseID', STR_TO_DATE('$foodExpiration','%m-%d-%Y'), $intQuantity, '$foodMeasurement', $intQuantity*1000)";

	$cupInsert = "INSERT INTO shelfUserV2(shelfid, userid, houseID, expiration, quantity, measurement, grams) VALUES((SELECT shelfID FROM Shelf WHERE name ='$foodName' AND foodGroup = '$foodGroup' AND type = '$foodType'), $intUserid, '$houseID', STR_TO_DATE('$foodExpiration','%m-%d-%Y'), $intQuantity, '$foodMeasurement', $intQuantity*128)";

	$ounceInsert = "INSERT INTO shelfUserV2(shelfid, userid, houseID, expiration, quantity, measurement, grams) VALUES((SELECT shelfID FROM Shelf WHERE name ='$foodName' AND foodGroup = '$foodGroup' AND type = '$foodType'), $intUserid,'$houseID', STR_TO_DATE('$foodExpiration','%m-%d-%Y'), $intQuantity, '$foodMeasurement', $intQuantity*28)";

	$tablespoonInsert = "INSERT INTO shelfUserV2(shelfid, userid, houseID, expiration, quantity, measurement, grams) VALUES((SELECT shelfID FROM Shelf WHERE name ='$foodName' AND foodGroup = '$foodGroup' AND type = '$foodType'), $intUserid, '$houseID',STR_TO_DATE('$foodExpiration','%m-%d-%Y'), $intQuantity, '$foodMeasurement', $intQuantity*15)";

	$teaspoonInsert = "INSERT INTO shelfUserV2(shelfid, userid, houseID, expiration, quantity, measurement, grams) VALUES((SELECT shelfID FROM Shelf WHERE name ='$foodName' AND foodGroup = '$foodGroup' AND type = '$foodType'), $intUserid, '$houseID',STR_TO_DATE('$foodExpiration','%m-%d-%Y'), $intQuantity, '$foodMeasurement', $intQuantity*4)";

	$gramInsert = "INSERT INTO shelfUserV2(shelfid, userid, houseID, expiration, quantity, measurement, grams) VALUES((SELECT shelfID FROM Shelf WHERE name ='$foodName' AND foodGroup = '$foodGroup' AND type = '$foodType'), $intUserid,'$houseID', STR_TO_DATE('$foodExpiration','%m-%d-%Y'), $intQuantity, '$foodMeasurement', $intQuantity)";
	
	$literUpdate = "UPDATE shelfUserV2 SET quantity = grams/1000 WHERE measurement = 'Liters'";
	$cupUpdate = "UPDATE shelfUserV2 SET quantity = grams/128 WHERE measurement = 'Cups'";
	$ounceUpdate = "UPDATE shelfUserV2 SET quantity = grams/28 WHERE measurement = 'Ounces'";
	$tablespoonUpdate = "UPDATE shelfUserV2 SET quantity = grams/15 WHERE measurement = 'Tablespoons'";
	$teaspoonUpdate = "UPDATE shelfUserV2 SET quantity = grams/4 WHERE measurement = 'Teaspoons'";
	$gramUpdate = "UPDATE shelfUserV2 SET quantity = grams WHERE measurement = 'Grams'";
	
	//if food item exists already
	if($result1){
		//if they have it in their shelf already
		if($result2){
			$multiply = 0;
			
			if ($foodMeasurement == "Liters"){
				$multiply += 1000;
				$update = "UPDATE shelfUserV2 SET grams = grams + ($intQuantity * $multiply) WHERE shelfid = $result1 AND houseid = $houseID";
				if (!mysqli_query($con,$update))
					{die('Error: a'. mysqli_error($con));}

			}
			if ($foodMeasurement == "Cups"){
				$multiply += 128;
				$update = "UPDATE shelfUserV2 SET grams = grams + ($intQuantity * $multiply) WHERE shelfid = $result1 AND houseid = $houseID";
				if (!mysqli_query($con,$update))
					{die('Error: a'. mysqli_error($con));}

			}
			if ($foodMeasurement == "Ounces"){
				$multiply += 28;
				$update = "UPDATE shelfUserV2 SET grams = grams + ($intQuantity * $multiply) WHERE shelfid = $result1 AND houseid = $houseID";
				if (!mysqli_query($con,$update))
					{die('Error: a'. mysqli_error($con));}

			}
			if ($foodMeasurement == "Tablespoons"){
				$multiply += 15;
				$update = "UPDATE shelfUserV2 SET grams = grams + ($intQuantity * $multiply) WHERE shelfid = $result1 AND houseid = $houseID";
				if (!mysqli_query($con,$update))
					{die('Error: a'. mysqli_error($con));}

			}
			if ($foodMeasurement == "Teaspoons"){
				$multiply += 4;
				$update = "UPDATE shelfUserV2 SET grams = grams + ($intQuantity * $multiply) WHERE shelfid = $result1 AND houseid = $houseID";
				if (!mysqli_query($con,$update))
					{die('Error: a'. mysqli_error($con));}


			}
			if ($foodMeasurement == "Grams"){
				$multiply += 1;
				$update = "UPDATE shelfUserV2 SET grams = grams + ($intQuantity * $multiply) WHERE shelfid = $result1 AND houseid = $houseID";
				if (!mysqli_query($con,$update))
					{die('Error: a'. mysqli_error($con));}
			}
			if ($foodMeasurement == "Units/Servings"){
				$update = "UPDATE shelfUserV2 SET quantity = quantity + $intQuantity WHERE shelfid = $result1 AND houseid = $houseID";
				if (!mysqli_query($con,$update))
					{die('Error: a'. mysqli_error($con));}
			}
			

			if (!mysqli_query($con,$literUpdate))
				{die('Error: literUpdate'. mysqli_error($con));}
			if (!mysqli_query($con,$cupUpdate))
				{die('Error: cupUpdate'. mysqli_error($con));}
			if (!mysqli_query($con,$ounceUpdate))
				{die('Error: ounceUpdate'. mysqli_error($con));}
			if (!mysqli_query($con,$tablespoonUpdate))
				{die('Error: tablespoonUpdate'. mysqli_error($con));}
			if (!mysqli_query($con,$teaspoonUpdate))
				{die('Error: teaspoonUpdate'. mysqli_error($con));}
			if (!mysqli_query($con,$gramUpdate))
				{die('Error: gramUpdate'. mysqli_error($con));}	
		}
		
		//if it exists but isn't in their shelf yet
		if($result2 == false) {
			//checks the measurement then updates it accordingly
			if ($foodMeasurement == "Liters"){
				if (!mysqli_query($con,$literInsert))
					{die('Error on literInsert: '. mysqli_error($con));}
			}

			if ($foodMeasurement == "Cups"){
				if (!mysqli_query($con,$cupInsert))
					{die('Error on cupInsert: '. mysqli_error($con));}
			}

			if ($foodMeasurement == "Ounces"){
				if (!mysqli_query($con,$ounceInsert))
					{die('Error on ounceInsert: '. mysqli_error($con));}
			}

			if ($foodMeasurement == "Tablespoons"){
				if (!mysqli_query($con,$tablespoonInsert))
					{die('Error on tablespoonInsert: '. mysqli_error($con));}
			}

			if ($foodMeasurement == "Teaspoons"){
				if (!mysqli_query($con,$teaspoonInsert))
					{die('Error on teaspoonInsert: '. mysqli_error($con));}
			}

			if ($foodMeasurement == "Grams"){
				if (!mysqli_query($con,$gramInsert))
					{die('Error on gramInsert: '. mysqli_error($con));}
			}
			
			if ($foodMeasurement == "Units/Servings"){
				if (!mysqli_query($con,$unitInsert))
					{die('Error on unitInsert: '. mysqli_error($con));}
			}
		}

		echo "inserting PRE-EXISTING food";
		mysqli_close($con);
	}
	



	
	//if food item is new
	if($result1 == false) {
		$insert2 = "INSERT INTO Shelf(name, foodGroup, upc, type) VALUES('$foodName', '$foodGroup', '$upc', '$foodType')";
		
		if (!mysqli_query($con,$insert2))
			{die('Error y: '. mysqli_error($con));}
		
		//checks the measurement then updates it accordingly
		if ($foodMeasurement == "Liters"){
			if (!mysqli_query($con,$literInsert))
				{die('Error on literInsert: '. mysqli_error($con));}
		}

		if ($foodMeasurement == "Cups"){
			if (!mysqli_query($con,$cupInsert))
				{die('Error on cupInsert: '. mysqli_error($con));}
		}

		if ($foodMeasurement == "Ounces"){
			if (!mysqli_query($con,$ounceInsert))
				{die('Error on ounceInsert: '. mysqli_error($con));}
		}

		if ($foodMeasurement == "Tablespoons"){
			if (!mysqli_query($con,$tablespoonInsert))
				{die('Error on tablespoonInsert: '. mysqli_error($con));}
		}

		if ($foodMeasurement == "Teaspoons"){
			if (!mysqli_query($con,$teaspoonInsert))
				{die('Error on teaspoonInsert: '. mysqli_error($con));}
		}

		if ($foodMeasurement == "Grams"){
			if (!mysqli_query($con,$gramInsert))
				{die('Error on gramInsert: '. mysqli_error($con));}
		}
		
		if ($foodMeasurement == "Units/Servings"){
			if (!mysqli_query($con,$unitInsert))
				{die('Error on unitInsert: '. mysqli_error($con));}
		}
		
		echo"inserting NEW food";

		mysqli_close($con);
	}

mysqli_close($con);
?>
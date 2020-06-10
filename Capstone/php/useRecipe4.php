<?php
$con = mysqli_connect("db.sice.indiana.edu", "i494f19_team39", "my+sql=i494f19_team39", "i494f19_team39");
//connection check
if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySql:" . mysqli_connect_error(). "\n");}
else
	{echo nl2br("");}
	
	$json = $_POST['json'];
	
	$decoded = json_decode($json)->input;

	//echo json_encode($decoded);
	
	$ingredients = $decoded->ingrediants;
	
	$user = intval($decoded->userid);
	
	echo "userid: " . $user . "\n";
	
	$ingredientsJson = json_decode($ingredients);
	
	for ($i = 0; $i < sizeOf($ingredients); $i++) {
		$qty = json_decode($ingredients[$i])->qty;
		$shelfid = json_decode($ingredients[$i])->id;
		$unit = json_decode($ingredients[$i])->unit;
		
		//echo "food id : " . $shelfid . " ";
		//echo "quantity : " . $qty . " \n";
		//echo "unit : " . $unit . " \n";
		
		if ($unit == "Units/Servings") {
			$updateunits = "UPDATE shelfUserV2 SET quantity = quantity - $qty WHERE shelfid = $shelfid AND userid = $user";
			
			echo "im running units";
						
			if (!mysqli_query($con,$updateunits))
				{die('Error: updateunits'. mysqli_error($con));}
		}
		
		else{
			if ($unit == "Liters")
				{$grams = $qty * 1000;}
			if ($unit == "Cups")
				{$grams = $qty * 128;}
			if ($unit == "Ounces")
				{$grams = $qty * 28;}
			if ($unit == "Tablespoons")
				{$grams = $qty * 15;}
			if ($unit == "Teaspoons")
				{$grams = $qty * 4;}
			if ($unit == "Grams") 
				{$grams = $qty;}
			
			echo "im running measurements";
			
			$update = "UPDATE shelfUserV2 SET grams = grams - $grams WHERE shelfid = $shelfid AND userid = $user";
			
			if (!mysqli_query($con,$update))
				{die('Error: updatemeasurement'. mysqli_error($con));}
		}
	}
	
	//updating quantity to reflect what is in grams
	$literUpdate = "UPDATE shelfUserV2 SET quantity = grams/1000 WHERE measurement = 'Liters'";
	$cupUpdate = "UPDATE shelfUserV2 SET quantity = grams/128 WHERE measurement = 'Cups'";
	$ounceUpdate = "UPDATE shelfUserV2 SET quantity = grams/28 WHERE measurement = 'Ounces'";
	$tablespoonUpdate = "UPDATE shelfUserV2 SET quantity = grams/15 WHERE measurement = 'Tablespoons'";
	$teaspoonUpdate = "UPDATE shelfUserV2 SET quantity = grams/4 WHERE measurement = 'Teaspoons'";
	$gramUpdate = "UPDATE shelfUserV2 SET quantity = grams WHERE measurement = 'Grams'";
	
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
		
		
	//if qty < 0 delete from shelf
	$checkifzero = "DELETE FROM shelfUserV2 WHERE quantity <= 0";
	
	if (!mysqli_query($con,$checkifzero))
			{die('Error: remove less than zero'. mysqli_error($con));}
	
	
	
mysqli_close($con);
?>
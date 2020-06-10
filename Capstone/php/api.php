<?php

$json = file_get_contents("php://input");
$json_data = json_decode($json)->input;


$action = $json_data->action;
$data = $json_data->data;


$servername = "db.sice.indiana.edu";
$username = "i494f19_team39";
$password = "my+sql=i494f19_team39";
$dbname = "i494f19_team39";


$conn = new mysqli($servername, $username, $password, $dbname);
if(!$conn){
        die("Failed to connect to MySQL: ".mysqli_connect_error());
}

$returnJson;

switch($action){

        case "selectrecipe";
		$userid = $data;
                $sql = "select r.recipeid,name, url,instructions, if(exists (select avg(ra.rating) from Rating as ra where r.recipeid=ra.recipeid group by ra.recipeid), (select avg(ra.rating) from Rating as ra where r.recipeid=ra.recipeid group by ra.recipeid), 0) as rating, if(exists (select count(rh.shelfid) from recipeHasFoodV2 as rh where rh.recipeid = r.recipeid group by rh.recipeid), (select  count(rh.shelfid) from recipeHasFoodV2 as rh where rh.recipeid = r.recipeid group by rh.recipeid),0) as ingredientscount, spicy, glutenfree, dairy, nuts, kosher, vegan, vegetarian, halal, fish, sugarfree from recipe as r, userhasrecipe as uhr where r.recipeid=uhr.recipeid and uhr.userid = $userid";
                if($result = mysqli_query($conn, $sql)){
                        $returnData = array();
                        $i = 0;
                        while($row = $result->fetch_assoc()){
                                $returnData[$i] = $row;
                                $i++;
                        }
                        $returnJson->status = "true";
                        $returnJson->action = $action;
                        $returnJson->message = "Successfully selected";
                        $returnJson->data = $returnData;
                } else {
                        $returnJson->status = "false";
                        $returnJson->action = $action;
                        $returnJson->data = $data;
                        $returnJson->message = $conn->error;
                }
                break;
        case "getrecipe":
		$sql = "select * from recipe where recipeid = $data";
		if($result = mysqli_query($conn, $sql)){
			$returnData = array();
			$i = 0;
			while($row = $result->fetch_assoc()){
				$returnData[$i] = $row;
				$i++;
			}
			$sql2 = "select name from tools where recipeid = $data";
			if($result2 = mysqli_query($conn, $sql2)){
				$tools = array();
				$j = 0;
				while($row2 = $result2->fetch_assoc()){
					$tools[$j] = $row2;
					$j++;
				}
				$sql3 = "select s.name, s.shelfID, rh.quantity, rh.measurement from Shelf as s, recipeHasFoodV2 as rh where s.shelfID = rh.shelfid and rh.recipeid = $data";
				if($result3 = mysqli_query($conn, $sql3)){
					$ingredients = array();
					$k = 0;
					while($row3 = $result3->fetch_assoc()){
						$ingredients[$k] = $row3;
						$k++;
					}
					$sql4 = "select rcid from recipeHasCategory where recipeid = $data";
					if($result4 = mysqli_query($conn, $sql4)){	
						while($row4 = $result4->fetch_assoc()){
							$cat = $row4;
						}
						$returnData[$i] = $tools;
						$returnData[$i+1] = $ingredients;
						$returnData[$i+2] = $cat;
						$returnJson->status = "true";
						$returnJson->action = $action;
						$returnJson->message = "Successfully selected";
						$returnJson->data = $returnData;
					}
				} else {
					$returnJson->status="false";
					$returnJson->action = $action;
					$returnJson->message = $conn->error;
					$returnJson->data = $data;
				}
			} else {
				$returnJson->status = "false";
				$returnJson->action = $action;
				$returnJson->message = $conn->error;
				$returnJson->data = $data;		
			}
		} else {
			$returnJson->status = "false";
			$returnJson->action = $action;
			$returnJson->data = $data;
			$returnJson->message = $conn->error;
		}
		break;
	case "selectfood":
                $sql = "select * from Shelf";
                if($result = mysqli_query($conn, $sql)){
                        $returnData = array();
                        $i = 0;
                        while($row = $result->fetch_assoc()){
                                $returnData[$i] = $row;
                                $i++;
                        }
                        $returnJson->status = "true";
                        $returnJson->action = $action;
                        $returnJson->message = "Successfully selected";
                        $returnJson->data = $returnData;
                } else {
                        $returnJson->status = "false";
                        $returnJson->action = $action;
                        $returnJson->message = $conn->error;
                }
                break;
        case "selectfoodlike":
                $sql = "select * from Shelf where name like '".$data."%'";
                if($result = mysqli_query($conn, $sql)){
                        $returnData = array();
                        $i = 0;
                        while($row = $result->fetch_assoc()){
                                $returnData[$i] = $row;
                                $i++;
                        }
                        $returnJson->status = "true";
                        $returnJson->action = $action;
                        $returnJson->message = "Successfully selected";
                        $returnJson->data = $returnData;
                } else {
                        $returnJson->status = "false";
                        $returnJson->action = $action;
                        $returnJson->message = $conn->error;
                }
                break;

        case "insertrecipe":
                $recipe = $data->recipe;
		$userid = $data->userid;
                $name = $conn->real_escape_string($recipe->Name);
                $instructions = $conn->real_escape_string($recipe->Instructions);
                $nationality = $recipe->Nationality;
		$cookingtime = $recipe->CookingTime;
		$isPublic = $recipe->isPublic;
		$spicy = $recipe->Spicy;
		$gluten = $recipe->Gluten;
		$halal = $recipe->Halal;
		$kosher = $recipe->Kosher;
		$sugarfree = $recipe->Sugarfree;
		$fish = $recipe->Fish;
		$nuts = $recipe->Nuts;
		$vegan = $recipe->Dairy;
		$vegetarian = $recipe->Vegetarian;
		$image = $recipe->image;
		$decodedImage = base64_decode($image);
		
                $sql = "select recipeid from recipe order by recipeid desc limit 1";
                if($biggestIdJson = mysqli_query($conn, $sql)){
                        $biggestId = 1 + (int)$biggestIdJson->fetch_assoc()["recipeid"];
                } else {
                        $biggestId = 0;
                }
		$file = fopen("images/".$name.$biggestId.".JPG", "w");
		fwrite($file, $decodedImage);
		fclose($file);

		chmod("images/".$name.$biggestId.".JPG", 0755);
                $sql2 = "insert into recipe (spicy, glutenfree, halal, kosher, sugarfree, fish, nuts, vegan, vegetarian, isPublic, cooking_time, recipeid, name, instructions, url, creator) values ($spicy, $gluten, $halal, $kosher, $sugarfree, $fish, $nuts, $vegan, $vegetarian, $isPublic, '$cookingtime', $biggestId, '$name', '$instructions', 'https://cgi.sice.indiana.edu/~team39/images/".str_replace(' ','%20',$name)."$biggestId.JPG', $userid); ";
                $ingrediants = $data->ingrediants;
                $returnJson->action = $action;
                for($i = 0; $i < sizeof($ingrediants); $i++){
                        $theIngrediant = $ingrediants[$i];
                        $ingrediantName = $theIngrediant->name;
                        $ingrediantId = $theIngrediant->id;
                        $ingrediantQty = $theIngrediant->qty;
                        $ingrediantUnit = $theIngrediant->unit;
                        $sql2 .= "insert into recipeHasFoodV2 (recipeid, shelfid, quantity, measurement) values ($biggestId, $ingrediantId, $ingrediantQty,'$ingrediantUnit'); ";
                }
		$sql2 .= " insert into userhasrecipe (userid, recipeid) values ($userid, $biggestId);";
		$returnJson->message = $conn->error;
                if($result = mysqli_multi_query($conn, $sql2)){
                        $returnJson->message = "Succesfully inserted";
                        $returnJson->status = "true";
			$returnJson->data=$biggestId;
                } else {
                        $returnJson->message = $conn->error;
                        $returnJson->status = "false";
                }
                break;

	case "editother":
		$recipe = $data->recipe;
                $oldid = $recipe->oldid;
		$userid = $data->userid;
		$name = $conn->real_escape_string($recipe->Name);
                $instructions = $conn->real_escape_string($recipe->Instructions);
                $nationality = $recipe->Nationality;
                $cookingtime = $recipe->CookingTime;
		$isPublic = $recipe->isPublic;
		$spicy = $recipe->Spicy;
		$gluten = $recipe->Gluten;
		$halal = $recipe->Halal;
		$kosher = $recipe->Kosher;
		$sugarfree = $recipe->Sugarfree;
		$fish = $recipe->Fish;
		$nuts = $recipe->Nuts;
		$vegan = $recipe->Dairy;
		$vegetarian = $recipe->Vegetarian;
		$image = $recipe->image;
		$rcid = $recipe->rcid;
		$decodedImage = base64_decode($image); 
		$sql = "select recipeid from recipe order by recipeid desc limit 1";
                if($biggestIdJson = mysqli_query($conn, $sql)){
                        $biggestId = 1 + (int)$biggestIdJson->fetch_assoc()["recipeid"];
                } else {
                        $biggestId = 0;
                }
		$file = fopen("images/".$name.$biggestId.".JPG", "w");
		fwrite($file, $decodedImage);
		fclose($file);	
                chmod("images/".$name.$biggestId.".JPG", 0755); 
		$sql3 = "";
		$sql2 = "insert into recipe (spicy, glutenfree, halal, kosher, sugarfree, fish, nuts, vegan, vegetarian, isPublic, cooking_time, recipeid, name, instructions, url, creator) values ($spicy, $gluten, $halal, $kosher, $sugarfree, $fish, $nuts, $vegan, $vegetarian, $isPublic, '$cookingtime', $biggestId, '$name', '$instructions', 'https://cgi.sice.indiana.edu/~team39/images/".str_replace(' ','%20',$name)."$biggestId.JPG', $userid); ";
		$ingrediants = $data->ingrediants;
                $returnJson->action = $action;
                for($i = 0; $i < sizeof($ingrediants); $i++){
                        $theIngrediant = $ingrediants[$i];
                        $ingrediantName = $theIngrediant->name;
                        $ingrediantId = $theIngrediant->id;
                        $ingrediantQty = $theIngrediant->qty;
                        $ingrediantUnit = $theIngrediant->unit;
                        $sql3 .= "insert into recipeHasFoodV2 (recipeid, shelfid, quantity, measurement) values ($biggestId, $ingrediantId, $ingrediantQty,'$ingrediantUnit'); ";
			                	
		}
		$sql3  .= " insert into userhasrecipe (userid, recipeid) values ($userid, $biggestId); delete from userhasrecipe where userid = $userid and recipeid = $oldid;";

		$sql3 .= " insert into recipeHasCategory (recipeid, rcid) values ($biggestId, $rcid);";
			
	
		$returnJson->message = $conn->error;
                if(mysqli_multi_query($conn, $sql2)){
                   	while(mysqli_next_result($conn)){}      
			mysqli_multi_query($conn, $sql3);
			$returnJson->message = "Successfully Added";
                        $returnJson->status = "true";
			
				
		} else {
                        $returnJson->message = $conn->error;
                        $returnJson->status = "false";
                }

		break;

	case "editowner":
		$recipe = $data->recipe;
                $userid = $data->userid;
		$name = $conn->real_escape_string($recipe->Name);
                $instructions = $conn->real_escape_string($recipe->Instructions);
                $nationality = $recipe->Nationality;
		$isPublic = $recipe->isPublic;
		$spicy = $recipe->Spicy;
		$gluten = $recipe->Gluten;
		$halal = $recipe->Halal;
		$kosher = $recipe->Kosher;
		$sugarfree = $recipe->Sugarfree;
		$fish = $recipe->Fish;
		$nuts = $recipe->Nuts;
		$vegan = $recipe->Dairy;
		$vegetarian = $recipe->Vegetarian;
		$rcid = $recipe->rcid;
               	$image = $recipe->image;
		$decodedImage = base64_decode($image); 
		$oldid = $recipe->oldid;
		$cookingTime = $recipe->CookingTime;
		$tools = $recipe->tools;
                $file = fopen("images/".$name.$oldid.".JPG", "w");
		fwrite($file, $decodedImage);
		fclose($file); 
		chmod("images/".$name.$oldid.".JPG", 0755); 		
		$sql2 = "update recipe set isPublic = $isPublic, spicy = $spicy, glutenfree = $gluten, halal = $halal, kosher = $kosher, sugarfree = $sugarfree, fish = $fish, nuts = $nuts, vegan = $vegan, vegetarian = $vegetarian, name = '$name', instructions = '$instructions', cooking_time = '$cookingTime', url = 'https://cgi.sice.indiana.edu/~team39/images/".str_replace(' ','%20',$name)."$oldid.JPG' where recipeid = $oldid; ";
                $sql3 = "delete from tools where recipeid = $oldid; ";
		for($i = 0; $i < sizeof($tools); $i++){
			$toolName = $tools[$i]->name;
			$sql3 .= "insert into tools (recipeid, name) values ($oldid, '$toolName'); ";	
		}
		$sql3 .= "delete from recipeHasFoodV2 where recipeid = $oldid; ";
		$sql3 .= "update recipeHasCategory set rcid = $rcid where recipeid =  $oldid; ";
		$ingrediants = $data->ingrediants;
                $returnJson->action = $action;
                for($i = 0; $i < sizeof($ingrediants); $i++){
                        $theIngrediant = $ingrediants[$i];
                        $ingrediantName = $theIngrediant->name;
                        $ingrediantId = $theIngrediant->id;
                        $ingrediantQty = $theIngrediant->qty;
                        $ingrediantUnit = $theIngrediant->unit;
                        $sql3 .= "insert into recipeHasFoodV2 (recipeid, shelfid, quantity, measurement) values ($oldid, $ingrediantId, $ingrediantQty,'$ingrediantUnit'); ";
                }             
		if($result = mysqli_query($conn, $sql2)){
			if($result2 = mysqli_multi_query($conn, $sql3)){
				$returnJson->message = "Successfully added";
                        	$returnJson->status = "true";
				$returnJson->data=$biggestId;
			} else {
                        	$returnJson->message = $conn->error;
                       		$returnJson->status = "false";	
			}
                } else {
                        $returnJson->message = $conn->error;
                        $returnJson->status = "false";
                }
	
		break;

	case "deleterecipe":
		$recipeid = $data->recipeid;
		$userid = $data->userid;
		$sql = "delete from userhasrecipe where userid = $userid AND recipeid = $recipeid";
		$returnJson->action = $action;	
		if(mysqli_query($conn, $sql)){
			$returnJson->message = "Successfully deleted";
			$returnJson->status = "true";
		} else {
			$returnJson->message = $conn->error;
			$returnJson->status = "false";
		}
		break;
	case "getnot":
		$id = $data;
		$sql = "select rateNot, shelfNot from users where userid = $id";
		$returnJson->action = $action;
		if($result = mysqli_query($conn, $sql)){
			while($row = $result->fetch_assoc()){
				$returnJson->shelfNot = $row["shelfNot"];
				$returnJson->rateNot = $row["rateNot"];	
			}
			$returnJson->status = "true";
			$returnJson->message = "successfully selected";
		} else {
			$returnJson->message = $conn->error;
			$returnJson->status = "false";
		}
		break;
	case "raterecipe":
		$userid = $data->userid;
		$recipeid = $data->recipeid;
		$rating = $data->rating;
		$sql = "select distinct re.recipeid as recipeid, re.name as name, username, rateNot from Rating as r, users as u, recipe as re where $userid = r.userid and re.recipeid = $recipeid and re.creator = u.userid;";
		if($result = mysqli_query($conn, $sql)){
			$testid = $result->fetch_assoc();	
		}
		$userName = $testid["username"];
		$recipeName = $testid["name"];
		$blocked = $testid["rateNot"];
		$testid = $testid["recipeid"];
		if($blocked){
		if($testid === $recipeid){
			$sql2 = "update Rating set rating = $rating where userid = $userid and recipeid = $recipeid";
			$returnJson->action = $action;
			if(mysqli_query($conn, $sql2)){
				$returnJson->message = "Successfully updated";	
				$returnJson->status = "true";
				$body = array('title' => 'Someone rated your recipe!', 'body' => 'Your recipe, '.$recipeName.', has been rated!');
	                        $data = array('to' => '/topics/'.$userName.'keepinitfreshtest', 'notification'=>$body);
        	                $options = array(
                	                'http' => array(
                        	                'header'  => "Content-type: application/json\r\n".
                                	        "Authorization: key=AAAA8mmTsuU:APA91bHN8JLiVRqu5G7aVNaDaSo3Grwh02qhcWRsbOSfd5o3vKFmKRq7IiQqFKUFgYYpqjsgSIvoCQRuBqyaZnpRHUQZk4KQTrbe2cYvcxP-211LM6iO88oQPSxvaVvYd5Nw0vO73uVx\r\n",
                                        	'method'  => 'POST',
                                        	'content' => json_encode($data)
                                	)
                        	);
	
                        	$context  = stream_context_create($options);
                        	$result = file_get_contents('https://fcm.googleapis.com/fcm/send', false, $context);
			} else {
				$returnJson->message = $conn->error;
				$returnJson->status = "false";
			}
		} else {
			$sql2 = "insert into Rating (rating, userid, recipeid) values ($userid, $recipeid, $rating)";
			$returnJson->action = $action;
			if(mysqli_query($conn, $sql2)){
				$returnJson->message = "Successfully rated";
				$returnJson->status = "true";
				$body = array('title' => 'Someone rating your recipe!', 'body' => 'Your recipe,'.$recipeName.', has been rated!');
	                        $data = array('to' => '/topics/'.$userName.'keepinitfreshtest', 'notification'=>$body);
        	                $options = array(
                	                'http' => array(
                        	                'header'  => "Content-type: application/json\r\n".
                                	        "Authorization: key=AAAA8mmTsuU:APA91bHN8JLiVRqu5G7aVNaDaSo3Grwh02qhcWRsbOSfd5o3vKFmKRq7IiQqFKUFgYYpqjsgSIvoCQRuBqyaZnpRHUQZk4KQTrbe2cYvcxP-211LM6iO88oQPSxvaVvYd5Nw0vO73uVx\r\n",
                                        	'method'  => 'POST',
                                        	'content' => json_encode($data)
                                	)
                        	);

				$returnJson->message = json_encode($testid);	
                        	$context  = stream_context_create($options);
                        	$result = file_get_contents('https://fcm.googleapis.com/fcm/send', false, $context);
				
		
			} else {
				$returnJson->message = $conn->error;
				$returnJson->status = "false";
			}
		}
		} else {
			$returnJson->status = "true";
			$returnJson->message = "Notification Blocked";
			$returnJson->action = $action;
		}
		break;

	case "selectrecipecat":
		$sql = "select * from recipeCategories";
		if($result = mysqli_query($conn, $sql)){
			$returnData = array();
			$i = 0;
			while($row = $result->fetch_assoc()){
				$returnData[$i] = $row;
				$i++;
			}
			$returnJson->status = "true";
			$returnJson->action = $action;
			$returnJson->message = "Successfully selected";
			$returnJson->data = $returnData;
		} else {
			$returnJson->status = "false";
			$returnJson->action = $action;
			$returnJson->message = $conn->error;
		}
		break;
	case "updatenot":
		$truthValue = $data->truth;
		$userid = $data->id;
		$choice = $data->key;
		$returnJson->action = $action;
		$newTruth = $truthValue? 1:0;
		$returnJson->message = $newTruth;
		$sql = "update users set $choice = $newTruth where userid = $userid";
		$returnJson->message = $sql;
		if(mysqli_query($conn, $sql)){
			$returnJson->status = "true";
			$returnJson->message = "successfully update";	
		} else {
			$returnJson->status = "false";		
			$returnJson->message = $conn->error;
		}
		break;
	case "selectrecipebycat":
		$rcid = $data;
		
		$sql = "select name, if(url is null, '', url) as url, cooking_time, recipe.recipeid, if(not exists (select avg(rating) from Rating where recipe.recipeid = Rating.recipeid), 0, (select avg(rating) from Rating where recipe.recipeid = Rating.recipeid)) as rating from recipe, recipeHasCategory where recipe.isPublic = 1 and recipe.recipeid = recipeHasCategory.recipeid  and recipeHasCategory.rcid = $rcid;";
		if($result = mysqli_query($conn, $sql)){
			$returnData = array();
			$i = 0;
			while($row = $result->fetch_assoc()){
				$returnData[$i] = $row;
				$i++;
			}
			$returnJson->status = "true";
			$returnJson->action = $action;
			$returnJson->message = "succesfully selected";
			$returnJson->data = $returnData;
		} else {
			$returnJson->status = "false";
			$returnJson->action = $action;
			$returnJson->message = $conn->error;
		}
		break;
        default:
                $returnJson->status = "false";
                $returnJson->action = $action;
                $returnJson->message = "Error: action '$action' not found.";
                break;
}

$returnJson = json_encode($returnJson);

echo $returnJson;

?>


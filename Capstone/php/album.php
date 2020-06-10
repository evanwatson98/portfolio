<!DOCTYPE html>
<html>
	<body>
	<!--Inserting begins -->
		<h1> Album Table</h1>
		<br>
		<h2>Insert a new Album</h2>
		<br>
		<form action="insertalbum.php" method="post">
		Title: <input type = "text" name = "form_albumTitle" maxlength="50" required><br>
		Band: <select name="form_band"required>
			<!--Dropdown for band is created -->
			<?php
			$conn = mysqli_connect("db.sice.indiana.edu","i308s19_team12","my+sql=i308s19_team12","i308s19_team12");
			//Check connection
			if(!$conn) {
				die("Connection failed: " .mysqli_connect_error());
			}
				$result = mysqli_query($conn,"SELECT * FROM band");
				while($row = mysqli_fetch_assoc($result)){
					unset($bid);
					$bid = $row['bid'];
					$name = $row['name'];
					
					echo '<option value ="'.$bid.'">'.$name.'</option>';
				}
				?>
				
		</select><br>
		Published Year:<input type = "number" name = "form_PublishedYear" min = "1900-01-01" max = "2025-01-31" required><br>
		Publisher : <input type = "text" name ="form_Publisher" max = "50"required><br>
		Format:<select name="form_band"required>
			<option value= "Album">Album</option>
			<option value="CD">CD</option>
			<option value="WAV">WAV</option>
			<option value="MP3">MP3</option>
		</select>
		<br>
		Price: $:<input type="number" step = ".01" name = "form_Price" min = "0" max="99999.99" required> Between $0 and $99,999.99 <br>
		<input type = "submit" value = "Insert Album" ></br>
		</form>
	<!--Selection begins -->
			<h2> Select all Albums</h2>
			<br>
			<form action="selectalbum.php" method="post">
			<input type = "submit" value = "Select Album Table" ></br>
			
			<br><br>
		</form>
	</body>
</html>
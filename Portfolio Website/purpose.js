document.addEventListener("keydown", checkKey);
function checkKey(e) {

//    e = e || window.event;
	
	var x, y = 0;
	
	var k = 0;
	var sunBox = document.querySelector("#curSun");
	var move = "";
	
	var alpha = 20;
	var vel = 10;
	var deltaSec = .01;
	
	var x_velocity = vel * deltaSec * Math.cos(alpha);
	var y_velocity = vel * deltaSec * Math.sin(alpha);
	
    if (e.keyCode == '39') {
        // right arrow
		k = 0;
		move = setInterval(moveSun,3);
		
    }
    else if (e.keyCode == '37') {
       // left arrow
		k = 1;
		moveSun();
    }
	
	function moveSun(){
		e = e || window.event;
		//move right
		move = setInterval(startMove, 5);
		
	}
	
	function startMove(){
		e = e || window.event;
		
		var sunRect = sunBox.getBoundingClientRect();
		x = sunRect.x;
		y = sunRect.y;

		if(k==0){
			//move right
			x = x + x_velocity;
			y = y + y_velocity;
			
			sunBox.style.left = (sunBox.offsetLeft + x) + "px";
			sunBox.style.top = (sunBox.offsetTop + y) + "px";
			console.log("x: " + (sunBox.offsetLeft + x) + "px, y: " + (sunBox.offsetTop + y));
		}
		else if(k==1){
			//move left
			
		}
	}
	
	function stopMove(){
		e = e || window.event;
		
	}

}
document.addEventListener("keydown", checkKey);

var move = "";

document.addEventListener('keyup', (event) => {
		const keyName = event.key;
		if(keyName == "ArrowRight" || keyName == "ArrowLeft"){
			clearInterval(move);
			console.log("Release");
			console.log("Release Clear Move");
//			alert('keyup event\n\n' + 'key: ' + keyName);

		}
	});
var ogX = document.querySelector("#curSun").getBoundingClientRect().left;
function checkKey(e) {
	

//    e = e || window.event;
	var sunBox = document.querySelector("#curSun");
	
	var x_velocity = 1;
	var y_velocity = -1 * Math.sin;

	var position = sunBox.getBoundingClientRect();
	var x = position.left;
	var y = position.top;
	var moveRightx = x +  x_velocity;
	var moveLeftx =  -  x_velocity;
	
	var k = 0;
	
	var alpha = .75;
	var vel = 10;
	var deltaSec = .01;
	
//	var x_velocity = vel * deltaSec * Math.cos(alpha);
//	var y_velocity = vel * deltaSec * Math.sin(alpha);
	
	//&& ((sunBox.offsetLeft + x) <= 279)
    if (e.keyCode == '39') {
        // right arrow
		k = 0;
		clearInterval(move);
		move = setInterval(startMove, 10);
		
    }
    else if (e.keyCode == '37') {
       // left arrow
		k = 1;
		clearInterval(move);
		move = setInterval(startMove, 10);
    }
	
	var boxLimit = 200;
	var r = 255;
	var g = 193;
	var b = 77;
	var purposeBackground = document.querySelector("#wakeUpContainer");
	var timeIcon = document.querySelector("#timeIcon");
	
	function startMove(){
		e = e || window.event;
		
		console.log(sunBox.offsetLeft);
		
		if( (sunBox.offsetLeft == boxLimit) && k != 1){
			console.log("Clear Move");
			clearInterval(move);
		}
		else if(k==0){
			//move right
			if(sunBox.offsetLeft == ((ogX+x_velocity)*2)){
				purposeBackground.style.backgroundColor = "rgb(" + r +", " + g +", " + b +")";
				timeIcon.style.content = "url('sun.svg')";
			}
			else if(sunBox.offsetLeft == ((ogX+x_velocity)*3)){
				purposeBackground.style.backgroundColor = "rgb(" + 0 +", " + 0 +", " + 0 +")";
				timeIcon.style.content = "url('cresMoon.svg')";
			}
			
			sunBox.style.left = sunBox.offsetLeft + x_velocity + "px";
//			sunBox.style.top = (sunBox.offsetTop + y_velocity) + "px";
//				sunBox.style.top = y + y_velocity;
		}
		else if(k==1){
			
			if(sunBox.offsetLeft == ((ogX+x_velocity)*2)){
				purposeBackground.style.backgroundColor = "rgb(" + 36+", " + 197 +", " + 255 +")";
				timeIcon.style.content = "url('plainSun.svg')";
			}
			else if(sunBox.offsetLeft == ((ogX+x_velocity)*3)){
				purposeBackground.style.backgroundColor = "rgb(" + r +", " + g +", " + b +")";
				timeIcon.style.content = "url('sun.svg')";
			}
			
			if(sunBox.offsetLeft > ogX){
				//move left
				sunBox.style.left = sunBox.offsetLeft - x_velocity + "px";
	//			sunBox.style.top = (sunBox.offsetTop + (-1*(sunBox.offsetTop * Math.sin(5)))) + "px";
	//			sunBox.style.top = slope;
	//			console.log("x: " + moveLeftx + "px, y: " + ( slope) + "px");
			}else{
				clearInterval(move);
			}
			
		}
	}

}
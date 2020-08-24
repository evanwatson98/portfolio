document.addEventListener("keydown", checkKey);

var move = "";

document.addEventListener('keyup', (event) => {
		const keyName = event.key;
		if(keyName == "ArrowRight" || keyName == "ArrowLeft"){
			clearInterval(move);
		}
	});
var ogX = document.querySelector("#curSun").getBoundingClientRect().left;
function checkKey(e) {
	

//    e = e || window.event;
	var sunBox = document.querySelector("#curSun");
	
	var x_velocity = 1;
//	var y_velocity = -1 * Math.sin;

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
	
    if (e.keyCode == '39') {
        // right arrow
		k = 0;
		clearInterval(move);
		move = setInterval(startMoveR, 5);
		
    }
    else if (e.keyCode == '37') {
       // left arrow
		k = 1;
		clearInterval(move);
		move = setInterval(startMoveL, 5);
    }
	
	var r = 255;
	var g = 193;
	var b = 77;
//	var purposeBackground = document.querySelector("#wakeUpContainer");
	var purposeBackgroundPlainSun = document.querySelector("#purposeBackgroundPlainSun");
	var purposeBackgroundSun = document.querySelector("#purposeBackgroundSun");
	var purposeBackgroundCresMoon = document.querySelector("#purposeBackgroundCresMoon");
	
	var timeIcon = document.querySelector("#timeIcon");
	var whyWakeBox = document.querySelector("#whyWake");
	var whyStepBox = document.querySelector("#whyStep");
	var whyStayBox = document.querySelector("#whyStay");
	var plainSun = document.querySelector("#plainSun");
	var sun = document.querySelector("#sun");
	var cresMoon = document.querySelector("#cresMoon");
	var y_velocity = 2;
	var boxLimit = document.querySelector("#wakeUp").offsetLeft - 80;
	var c = 1;

	function startMoveR(){
//		e = e || window.event;
		
//		console.log(sunBox.offsetLeft);
		
			//move right
				sunBox.style.left = sunBox.offsetLeft + x_velocity + "px";
				sunBox.style.top = sunBox.offsetTop - y_velocity + "px";
	//			sunBox.style.top = sunBox.offsetTop - y_velocity + "px";
	//			
				whyWakeBox.style.top = whyWakeBox.offsetTop - x_velocity + "px";
				whyStepBox.style.top = whyStepBox.offsetTop - x_velocity + "px";
				whyStayBox.style.top = whyStayBox.offsetTop - x_velocity + "px";

			if(sunBox.offsetLeft == ogX-x_velocity){clearInterval(move)};
			 
			if(sunBox.offsetLeft == ((ogX+x_velocity)*6)){
//				purposeBackground.style.backgroundColor = "rgb(" + r +", " + g +", " + b +")";
				plainSun.style.visibility= "hidden";
				sun.style.visibility = "visible";
				purposeBackgroundPlainSun.style.visibility = "hidden";
				purposeBackgroundSun.style.visibility = "visible";
//				sunBox.style.left = sunBox.offsetLeft + x_velocity + "px";
			}
			else if(sunBox.offsetLeft == ((ogX+x_velocity)*12)){
//				purposeBackground.style.backgroundImage = "url('stars.jpg')";
				sun.style.visibility= "hidden";
				cresMoon.style.visibility = "visible";
				purposeBackgroundSun.style.visibility = "hidden";
				purposeBackgroundCresMoon.style.visibility = "visible";
//				sunBox.style.left = sunBox.offsetLeft + x_velocity + "px";
			}
			
		}
	
	function startMoveL(){
		if(sunBox.offsetLeft == ((ogX+x_velocity)*6)){
//				purposeBackground.style.backgroundColor = "rgb(" + 36+", " + 197 +", " + 255 +")";
				sun.style.visibility= "hidden";
				plainSun.style.visibility = "visible";
				purposeBackgroundSun.style.visibility = "hidden";
				purposeBackgroundPlainSun.style.visibility = "visible";
//				sunBox.style.left = sunBox.offsetLeft - x_velocity + "px";
			}
			else if(sunBox.offsetLeft == ((ogX+x_velocity)*12)){
//				purposeBackground.style.backgroundImage = "none";
//				purposeBackground.style.backgroundColor = "rgb(" + r +", " + g +", " + b +")";
				cresMoon.style.visibility= "hidden";
				sun.style.visibility = "visible";
				purposeBackgroundSun.style.visibility = "visible";
				purposeBackgroundCresMoon.style.visibility = "hidden";
//				sunBox.style.left = sunBox.offsetLeft - x_velocity + "px";
			}
			
			if(sunBox.offsetLeft > ogX){
				//move left

				sunBox.style.left = sunBox.offsetLeft - x_velocity + "px";
				sunBox.style.top = sunBox.offsetTop + y_velocity + "px";
	//			sunBox.style.top = sunBox.offsetTop - y_velocity + "px";
	//			
				whyWakeBox.style.top = whyWakeBox.offsetTop + x_velocity + "px";
				whyStepBox.style.top = whyStepBox.offsetTop + x_velocity + "px";
				whyStayBox.style.top = whyStayBox.offsetTop + x_velocity + "px";
			}else{
				clearInterval(move);
			}
	}
			
		}

	var welcomeBox = setInterval(welcomeWheel,2000);

	var welcomeCounter = 0;
	function welcomeWheel() {
		var bubble = document.querySelector("#introBubble");
		
		if(welcomeCounter == 13){
			welcomeCounter=0;
			var i =0;
			while(i <= 13){
				bubble.removeChild(bubble.childNodes[i]);
				i++;
			}
			
		}
		var welcomeList = ["Welcome", "	Bienvenido", "Selamat datang!", "Kalos Ilthate", "Välkommen", "Willkommen", "Aloha", "Benvenuto", "Salve", "Witaj!", "Fàilte", "Swaagat", "Gratus Mihi Venis"];

		var welcome = document.createElement('div');
		welcome.className = "bubbleContent";
		welcome.innerHTML = welcomeList[welcomeCounter];
		
		bubble.appendChild(welcome);
		welcomeCounter++;
	}

function moveWelcome(elmnt) {
	var pos1 = 0, pos3 = 0;
  if (document.getElementById(elmnt.id + "header")) {
    // if present, the header is where you move the DIV from:
    document.getElementById(elmnt.id + "header").onmousedown = dragMouseDown;
  } else {
    // otherwise, move the DIV from anywhere inside the DIV:
    elmnt.onmousedown = dragMouseDown;
  }

  function dragMouseDown(e) {
    e = e || window.event;
    e.preventDefault();
    // get the mouse cursor position at startup:
    pos3 = e.clientX;
    document.onmouseup = closeDragElement;
    // call a function whenever the cursor moves:
    document.onmousemove = elementDrag;
  }

  function elementDrag(e) {
    e = e || window.event;
    e.preventDefault();
    // calculate the new cursor position:
    pos1 = pos3 - e.clientX;
    pos3 = e.clientX;
    // set the element's new position:
    elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
  }

  function closeDragElement() {
    // stop moving when mouse button is released:
    document.onmouseup = null;
    document.onmousemove = null;
  }
}

function makeDragIcon(){
	var moveButton = document.querySelector("#goToSite");
	moveButton.innerHTML = "Drag Here to Begin Our Introduction";
}

window.setTimeout(makeDragIcon,5000);

document.querySelector("#goToSite").addEventListener("keydown", moveWelcome(document.querySelector("#welcomeContainer")));

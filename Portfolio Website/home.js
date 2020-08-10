
	var welcomeBox = setInterval(welcomeWheel,2000);
	var welcomeCounter = 0;
	function welcomeWheel() {
		
		if(welcomeCounter == 13){welcomeCounter=0;}
		var welcomeList = ["Welcome", "	Bienvenido", "Selamat datang!", "Kalos Ilthate", "Välkommen", "Willkommen", "Aloha", "Benvenuto", "Salve", "Witaj!", "Fàilte", "Swaagat", "Gratus Mihi Venis"];

		var welcome = document.createElement('div');
		welcome.className = "bubbleContent";
		welcome.innerHTML = welcomeList[welcomeCounter];
		
		var bubble = document.querySelector("#introBubble");
		bubble.appendChild(welcome);
		welcomeCounter++;
	}


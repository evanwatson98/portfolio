// const deg = 6;
// const hr = document.querySelector('#hr');
// const mn = document.querySelector('#mn');
// const sc = document.querySelector('#sc');

// setInterval(() => {

    
//     let day = new Date();
//     let hh = day.getHours() * 30;
//     let mm = day.getMinutes() * deg;
//     let ss = day.getSeconds() * deg;
    
//     hr.style.transform = `rotateZ(${(hh)+(mm/12)}deg)`;
//     mn.style.transform = `rotateZ(${mm}deg)`;
//     sc.style.transform = `rotateZ(${ss}deg)`;
// })

// var headshotHeight=document.getElementById('headshotContainer').style.height;
// var bioHeight=document.getElementById('bioTextContainer').style.height;

// if(headshotHeight>bioHeight)
// {
//     bioHeight=headshotHeight;
//     var maxHeight= headshotHeight
// }
// else
// {
//     headshotHeight=bioHeight;
//     var maxHeight= headshotHeight
// }

// businessCardFrontContainer.getElementById('bioTextContainer').style.height = maxHeight;

// var maxHeightContainer;

// function checkHeight(containerA, containerB){
//     var containerAht = document.getElementById(containerA).clientHeight;;
//     var containerBht = document.getElementById(containerB).clientHeight;;
//     // containerAht = busHeight;
//     // containerBht = busHeight; 
//     document.getElementById(containerB).setAttribute("style","width:" + containerAht + "px");
//     console.log(containerA + containerAht + " test");
//     console.log(document.getElementById(containerB).clientHeight + " test");
// }

// checkHeight('carCard1', 'carCard2');

function fixAspect(img) {
    var $img = $(img),
      width = $img.width(),
      height = $img.height(),
      tallAndNarrow = width / height < 1;
    if (tallAndNarrow) {
      $img.addClass('tallAndNarrow');
    }
    $img.addClass('loaded');
}

function openNav() {
  document.getElementById("mySidenav").style.width = "250px";
  document.getElementById("main").style.marginLeft = "250px";
  document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
}

function closeNav() {
  document.getElementById("mySidenav").style.width = "0";
  document.getElementById("main").style.marginLeft= "0";
  document.body.style.backgroundColor = "white";
}

function del_parent(e){
	e.parentElement.remove();
}

var ct = 0;
function add_skill(e){
	var rand_colors = ['#75DDEB', '#AEE6B6', '#FF3600', '#F061F2', '#908CFF'];
	if(e && e.keyCode === 32 || e.keyCode === 13){
		var test = document.getElementById("skills").value;
		
		var bubbleSkillWrapper = document.createElement('div');
		bubbleSkillWrapper.className ='d-flex';

		var bubbleSkill = document.createElement('div');
		bubbleSkill.className ='bubbleSkill';
		
		var del = document.createElement('div');
		del.className ='del';
		del.id = 'del';
		del.innerHTML = 'x';
		
		bubbleSkill.style.backgroundColor = rand_colors[ct];
		bubbleSkill.innerHTML = test;
		document.getElementById("skills").value = "";
		bubbleSkillWrapper.appendChild(bubbleSkill);
		bubbleSkillWrapper.appendChild(del);
		
		document.getElementById("storeSkills").appendChild(bubbleSkillWrapper);
		ct++;
		if(ct == 5){
			ct=0;
		}
		console.log(ct);
		
		document.getElementById("del").addEventListener("click", del_parent);

   }
}


$('form input').keydown(function (e) {
    if (e.keyCode == 13 ) {
        e.preventDefault();
        return false;
    }
});

document.getElementById("skills").addEventListener("keyup", add_skill);

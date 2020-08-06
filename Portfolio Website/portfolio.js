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


// Used in add_skills
var ct = 0;
var skillsArr = [];
//
function add_skill(e){
	var rand_colors = ['#75DDEB', '#AEE6B6', '#FF3600', '#F061F2', '#908CFF'];
	if(e && e.keyCode === 32 || e.keyCode === 13){
		var test = document.getElementById("skills").value;
		skillsArr.push(test);
		
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

function postSkill(e){
	$.ajax({
		type : "POST", 
		url  : "submit_job.php",  
		data : { allSkills : skillsArr},
		success: function(res){  
								//do what you want here...
				}
	});
}


document.getElementById("skills").addEventListener("keyup", add_skill);
document.getElementById("submitJob").addEventListener("keyup", postSkill);


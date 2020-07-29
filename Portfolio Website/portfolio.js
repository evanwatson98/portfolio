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
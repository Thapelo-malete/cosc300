let dropdownBtn = document.querySelector(".dropdown-btn");
let dropdownItems = document.querySelector(".dropdown-items");

let menuButton = document.querySelector('.menu-button');
let navLeft = document.querySelector('.nav-left');

menuButton.addEventListener('click', ()=> {
    navLeft.classList.toggle('active');
});


window.addEventListener('click',(event)=>{
    console.log(dropdownBtn.contains(event.target))
    if(!event.target.matches('.dropdown-btn') && !dropdownBtn.contains(event.target)) {
        if (dropdownItems.classList.contains('vissible')) {
            dropdownItems.classList.remove('vissible');
        }
    }

    
});

dropdownBtn.addEventListener('click',()=>{
    dropdownItems.classList.toggle('vissible');
});

let dropdownBtn = document.querySelector(".dropdown-btn");
let dropdownItems = document.querySelector(".dropdown-items");


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


let navButtons = document.querySelectorAll(".psychologist .navigation .nav-item");
let navButton = document.querySelector(".psychologist .navigation .nav-item");
let tableContainers = document.querySelectorAll(".table-container")

navButtons.forEach((button, index) => {
    button.addEventListener('click', () => {
        for (let i = 0; i < navButtons.length; i++) {
            navButtons[i].classList.remove("active");
            tableContainers[i].classList.remove("vissible");
        }
        button.classList.add("active")
        tableContainers[index].classList.add("vissible")
    })
});

const form = document.getElementById('emergency-form');

form.addEventListener('submit', (event) => {
  event.preventDefault();
  const name = document.getElementById('name').value;
  const phone = document.getElementById('phone').value;
  const relationship = document.getElementById('relationship').value;
  
  localStorage.setItem('emergency-contact-name', name);
  localStorage.setItem('emergency-contact-phone', phone);
  localStorage.setItem('emergency-contact-relationship', relationship);
  
  alert('Emergency contact saved successfully!');
});

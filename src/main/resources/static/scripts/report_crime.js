const reportForm = document.getElementById('report-form');
const reportedCrimesTable = document.getElementById('reported-crimes');

// Retrieve reported crimes from local storage and display them in the table
let crimes = JSON.parse(localStorage.getItem('reportedCrimes')) || [];
crimes.forEach(crime => {
    const row = reportedCrimesTable.insertRow();
    row.insertCell().textContent = crime.crimeType;
    row.insertCell().textContent = crime.description;
    row.insertCell().textContent = crime.location;
    row.insertCell().textContent = crime.date;
    row.insertCell().textContent = crime.time;
});

reportForm.addEventListener('submit', (event) => {
    event.preventDefault();

    // Get form data
    const crimeType = document.getElementById('crime-type').value;
    const description = document.getElementById('description').value;
    const location = document.getElementById('location').value;
    const date = document.getElementById('date').value;
    const time = document.getElementById('time').value;

    // Create new crime object
    const newCrime = {
        'crimeType': crimeType,
        'description': description,
        'location': location,
        'date': date,
        'time': time
    };

    // Get existing crimes from local storage or create empty array
    let crimes = JSON.parse(localStorage.getItem('reportedCrimes')) || [];

    // Add new crime to the array and store in local storage
    crimes.push(newCrime);
    localStorage.setItem('reportedCrimes', JSON.stringify(crimes));

    // Add new crime to the table
    const row = reportedCrimesTable.insertRow();
    row.insertCell().textContent = crimeType;
    row.insertCell().textContent = description;
    row.insertCell().textContent = location;
    row.insertCell().textContent = date;
    row.insertCell().textContent = time;
});

const messageInput = document.querySelector('span');
console.log(messageInput.innerHTML == 'taken');
if (messageInput.innerHTML == 'taken') {
    Swal.fire({
        icon: 'warning',
        title: "Oops...",
        text: 'The you provided is already taken!!',
        footer: '<a href="/user/login">Sign in?</a>'
    })
} else if (messageInput.innerHTML == "expired") {
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'The verification link has expired!!',
        footer: '<a href="/user/token/resend">Resend?</a>'
    })
} else if (messageInput.innerHTML == "notFound") {
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: "The verification link doesn't exist"
    })
}
function sendMessage() {
    const username = document.getElementById("username").value;
    const messageText = document.getElementById("message").value;

    if (username && messageText) {
        const message = document.createElement("div");
        message.className = "message";
        message.innerHTML = `<strong>${username}:</strong> ${messageText}`;

        const messagesContainer = document.getElementById("messages");
        messagesContainer.insertBefore(message, messagesContainer.firstChild);
        document.getElementById("username").value = "";
        document.getElementById("message").value = "";
    }
}

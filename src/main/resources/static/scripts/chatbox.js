// get chatbox elements
const chatbox = document.querySelector('.chatbox');
const header = chatbox.querySelector('.chat-header');
const body = chatbox.querySelector('.chat-body');
const input = chatbox.querySelector('input');
const sendBtn = chatbox.querySelector('.send-btn');
const closeBtn = chatbox.querySelector('.close-btn');

// add event listeners
header.addEventListener('click', toggleChatbox);
input.addEventListener('keydown', handleInput);
sendBtn.addEventListener('click', sendMessage);
closeBtn.addEventListener('click', toggleChatbox);

// function to toggle chatbox
function toggleChatbox() {
  chatbox.classList.toggle('active');
}

// function to handle input
function handleInput(event) {
  if (event.keyCode === 13) {
    sendMessage();
  }
}

// function to send message
function sendMessage() {
  const message = input.value;
  if (message.trim()) {
    addMessage('user', message);
    input.value = '';
    // replace with your own logic to send message to server and get response
    setTimeout(() => {
      addMessage('bot', 'Thank you for reporting. We will take necessary actions to handle the situation.');
    }, 1000);
  }
}

// function to add message
function addMessage(sender, message) {
  const msgElement = document.createElement('div');
  msgElement.classList.add('chat-message', sender);
  const pElement = document.createElement('p');
  pElement.textContent = message;
  msgElement.appendChild(pElement);
  body.appendChild(msgElement);
  body.scrollTop = body.scrollHeight;
}

document.getElementById('send-btn').addEventListener('click', function() {
    let messageInput = document.getElementById('message-input');
    let messageText = messageInput.value.trim();

    if (messageText !== '') {
        addMessage(messageText, 'user');
        messageInput.value = '';
        setTimeout(() => {
            addMessage('안녕하세요, 어떻게 도와드릴까요?', 'bot');
        }, 1000);
    }
});

function addMessage(text, sender) {
    let messageContainer = document.createElement('div');
    messageContainer.classList.add('message');
    if (sender === 'user') {
        messageContainer.classList.add('user-message');
    } else {
        messageContainer.classList.add('bot-message');
    }

    let messageText = document.createElement('span');
    messageText.classList.add('message-text');
    messageText.textContent = text;

    let timestamp = document.createElement('span');
    timestamp.classList.add('timestamp');
    let now = new Date();
    timestamp.textContent = now.getHours() + ':' + (now.getMinutes() < 10 ? '0' : '') + now.getMinutes();

    messageContainer.appendChild(messageText);
    messageContainer.appendChild(timestamp);

    document.getElementById('chat-box').appendChild(messageContainer);
    document.getElementById('chat-box').scrollTop = document.getElementById('chat-box').scrollHeight;
}

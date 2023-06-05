let stompClient = null

window.addEventListener('load', function() {
    let socket = new SockJS('/support-chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function() {
        stompClient.subscribe('/topic/support-messages', function(response) {
            const message = JSON.parse(response.body);
            displayMessage(message.sender, message.content);
        });
    });
    let button = document.getElementsByClassName("send-button")[0];
    button.addEventListener('click', sendMessage);
});

function sendMessage() {
        const messageInput = document.getElementsByClassName('input-field')[0];
        const chatId = document.getElementById('chatId')
        if(messageInput.value == null || messageInput.value === '') {
            return null;
        }
        const message = {
            content: messageInput.value,
            uuid: chatId.value
        };
        stompClient.send('/app/support-chat', {}, JSON.stringify(message));
        messageInput.value = '';
}


function displayMessage(sender, content) {
    const chatMessages = document.getElementsByClassName('message-list')[0];

    const messageElement = document.createElement('div');
    let userNickname = document.getElementsByClassName('profile')[0].innerHTML;

    if(sender === userNickname) {
        messageElement.setAttribute('class', 'message user-message');
    } else {
        messageElement.setAttribute('class', 'message other-message');
    }

    let p = document.createElement('p');
    p.setAttribute('class', 'message-text')
    p.innerHTML = content;

    messageElement.appendChild(p);

    chatMessages.appendChild(messageElement);
}
window.onclose=disconnect

window.addEventListener('load', function() {
    let socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function() {
        stompClient.subscribe('/topic/messages', function(messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });
    let button = document.getElementById("sendMessage");
    button.addEventListener('click', sendMessage);
});

let stompClient = null;
function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
}

function sendMessage() {
    let text = document.getElementById('text').value;
    if(text === "" || text === null) {
        return null
    }
    stompClient.send("/app/chat", {},
        JSON.stringify({'text':text}));
    let chat = document.getElementById("chat")
    setTimeout(function () {
        chat.scrollTop = chat.scrollHeight
    }, 50)
    document.getElementById('text').value = ''
}

function showMessageOutput(messageOutput) {
    let response = document.getElementById('response');

    let divElement = document.createElement('div');
    let nickname = document.createElement('div')
    let message = document.createElement('div')
    let timestamp = document.createElement('div');

    divElement.setAttribute("class", "message")

    nickname.setAttribute("class", "message-nickname")
    nickname.innerHTML = messageOutput.from

    message.setAttribute("class", "message-content")
    message.innerHTML = messageOutput.text

    timestamp.setAttribute("class", "message-time")
    timestamp.innerHTML = messageOutput.time

    divElement.appendChild(nickname)
    divElement.appendChild(message)
    divElement.appendChild(timestamp)

    response.appendChild(divElement);
}


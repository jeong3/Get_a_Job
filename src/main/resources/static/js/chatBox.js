let job_title = '';  // 직무를 저장할 변수
const apiKey = '';

window.onload = function() {
    // 페이지 로드 시 직무 입력을 받는 함수 실행
    job_title = prompt("직무를 입력해주세요 (예: 백엔드 개발자)");
    if (job_title) {
        // 직무가 입력되었을 때 메시지 초기화
        startChatWithJob(job_title);
    } else {
        // 직무가 입력되지 않으면 기본 메시지 표시
        alert("직무를 입력하지 않았습니다.");
    }
};

function startChatWithJob(job_title) {
    const messages = [
        { 
            role: "system", 
            content: `너는 ${job_title} 전문 모의면접관이야. 사용자의 답변에 따라 추가 질문을 해줘.` 
        },
        { 
            role: "user", 
            content: "안녕하세요!"  // 기본 인사
        }
    ];

    // 여기서 GPT API로 요청하거나, 페이지에 대화 내용을 추가하는 로직을 작성할 수 있습니다.
    console.log(messages);
}

// 보내기 버튼을 클릭했을 때 실행되는 함수
document.getElementById('send-btn').addEventListener('click', function() {
    let messageInput = document.getElementById('message-input');
    let messageText = messageInput.value.trim();

    if (messageText !== '') {
        addMessage(messageText, 'user');
        messageInput.value = '';

        // GPT로 메시지 전송
        sendToGPT(messageText);
    }
});

async function sendToGPT(userInput) {
    // 직무 정보가 포함된 메시지
    const messages = [
        { 
            role: "system", 
            content: `너는 ${job_title} 직무의 전문 모의면접관이야. 사용자의 답변에 따라 추가 질문을 해줘.` 
        },
        { 
            role: "user", 
            content: userInput 
        }
    ];

    const response = await fetch("https://api.openai.com/v1/chat/completions", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer "+apiKey  // API 키를 여기에 넣으세요
        },
        body: JSON.stringify({
            model: "gpt-3.5-turbo",  
            messages: messages
        })
    });

    const data = await response.json();
    const gptResponse = data.choices[0].message.content;

    // GPT의 응답을 화면에 출력
    addMessage(gptResponse, 'bot');
}

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

let job_title = '';  // 직무를 저장할 변수
let chatNum = '';
const apiKey = '';
let lastQuestion = "";
window.onload = function() {
    // 페이지 로드 시 직무 입력을 받는 함수 실행
    job_title = prompt("직무를 입력해주세요 (예: 백엔드 개발자)");
	
    if (job_title) {
        SendingJob(job_title);
        startChatWithJob(job_title);
    } else {
        // 직무가 입력되지 않으면 기본 메시지 표시
        alert("직무를 입력하지 않았습니다.");
    }
};
function SendingJob(job_title){
	$.ajax({
	       url: "/interview/chatRoomCreate",  // API 요청 URL
	       method: "post",  // HTTP 메서드 (POST)
	       data: { "jobTitle" : job_title},
	       success: function(response) {
			chatNum = response;  // 서버에서 받은 chatNum을 전역 변수에 저장
			console.log("chatNum: ", chatNum);

	           
	       },
	       error: function(xhr, status, error) {
	           console.error("Error:", error);
	           alert("서버 오류");
	       }
	   });
}

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
document.getElementById('message-input').addEventListener('keydown', function(event) {
    if (event.key === 'Enter' && !event.shiftKey) { // Shift+Enter는 줄바꿈
        event.preventDefault(); // 엔터 기본 동작(폼 전송 등) 방지
        document.getElementById('send-btn').click(); // 버튼 클릭 실행
    }
});


async function sendToGPT(userInput) {
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

    try {
        const response = await fetch("https://api.openai.com/v1/chat/completions", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + apiKey
            },
            body: JSON.stringify({
                model: "gpt-3.5-turbo",
                messages: messages
            })
        });

        if (!response.ok) {
            const errorData = await response.json();
            console.error("API Error:", errorData);
            alert("API 호출 중 오류가 발생했습니다.");
            return;
        }

        const data = await response.json();
        const gptResponse = data.choices[0].message.content;

        // ✅ 지금 사용자 입력은 지난 GPT 질문에 대한 답변이므로 그걸 저장
        if (lastQuestion !== "") {
            saveQuestionAnswer(lastQuestion, userInput);  
        }

        // 화면에 다음 GPT 질문 보여주기
        addMessage(gptResponse, 'bot');

        // 다음 턴을 위한 질문 기억해두기
        lastQuestion = gptResponse;

    } catch (error) {
        console.error("Fetch error:", error);
        alert("API 호출 중 예외가 발생했습니다.");
    }
}
// 질문과 답변을 DB에 저장하는 함수
function saveQuestionAnswer(question, userAnswer) {
    $.ajax({
        url: "/interview/saveQA",  // 서버로 데이터 전송
        method: "POST",  // POST 메서드
        data: {
            chatNum: chatNum,      // 채팅방 번호
            question: question,    // 질문 내용
            userAnswer: userAnswer // 사용자의 답변
        },
        success: function() {
            console.log("질문과 답변이 저장되었습니다.");
        },
        error: function(xhr, status, error) {
            console.error("저장 중 오류 발생:", error);
            alert("저장 실패");
        }
    });
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



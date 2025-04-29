let lastFeedbackNum = null; // ⭐ 업로드할 때 받은 feedbackNum 저장

// 직무 설정 폼 제출
document.getElementById("jobTitleForm").addEventListener("submit", function (event) {
    event.preventDefault();
    
    const jobTitle = document.getElementById("jobTitleInput").value;

    fetch('/feedback/setJobTitle', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({ jobTitle: jobTitle })
    })
    .then(response => response.text())
    .then(result => {
        addMessageToChatBox("system", `희망 직무가 '${jobTitle}'로 설정되었습니다.`);
    })
    .catch(error => {
        console.error('직무 설정 실패:', error);
        alert('직무 설정 실패');
    });
});

// 파일 업로드 폼 제출
document.getElementById("fileForm").addEventListener("submit", function (event) {
    event.preventDefault();
    
    const fileInput = document.getElementById("fileInput");
    const file = fileInput.files[0];
    const jobTitle = document.getElementById("jobTitleInput").value || "일반";

    if (!file) {
        alert("업로드할 파일을 선택하세요.");
        return;
    }

    const formData = new FormData();
    formData.append('file', file);
    formData.append('jobTitle', jobTitle);

	fetch('/feedback/upload', {
	  method: 'POST',
	  body: formData
	})
	.then(response => response.json())
	.then(data => {
	  console.log("업로드 결과:", data); // 콘솔 찍기
	  if (!data.feedback || !data.feedbackNum) {
	    alert("서버에서 피드백을 제대로 받지 못했습니다.");
	    return;
	  }

	  // 저장
	  sessionStorage.setItem("lastFeedbackNum", data.feedbackNum);
	  addMessageToChatBox("bot", data.feedback);
	})
	.catch(err => {
	  console.error("Error:", err);
	  alert("파일 업로드 실패");
	});

});

// 질문 전송 버튼 클릭
document.getElementById("sendQuestionButton").addEventListener("click", function (event) {
    event.preventDefault();
    
	const feedbackNum = sessionStorage.getItem("lastFeedbackNum");
	
    const questionInput = document.getElementById("questionInput");
    const question = questionInput.value.trim();

    if (!question) {
        alert("질문을 입력하세요!");
        return;
    }

    if (!feedbackNum) {
        alert("먼저 파일을 업로드하고 피드백을 받아야 합니다.");
        return;
    }

    addMessageToChatBox("user", question);

    fetch('/feedback/feedback/ask', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({
            feedbackNum: feedbackNum,
            question: question
        })
    })
    .then(response => response.text())
    .then(answer => {
        addMessageToChatBox("bot", answer);
        questionInput.value = ""; // 질문 입력창 초기화
    })
    .catch(error => {
        console.error('질문 전송 실패:', error);
        alert('질문 전송 실패');
    });
});

// 채팅박스에 메시지 추가하는 함수
function addMessageToChatBox(sender, message) {
    const chatBox = document.getElementById("chatBox");

    const messageDiv = document.createElement("div");
    messageDiv.classList.add("message");

    const textDiv = document.createElement("div");
    textDiv.classList.add("message-text");

    if (sender === "user") {
        messageDiv.classList.add("user-message");
    } else if (sender === "bot") {
        messageDiv.classList.add("bot-message");
    } else if (sender === "system") {
        messageDiv.classList.add("system-message");
    }

    textDiv.textContent = message;
    messageDiv.appendChild(textDiv);

    chatBox.appendChild(messageDiv);
    chatBox.scrollTop = chatBox.scrollHeight; // 항상 맨 아래로 스크롤
}

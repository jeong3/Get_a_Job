// 희망 직무 저장
function setJobTitle(jobTitle) {
    fetch('/feedback/setJobTitle', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({ 'jobTitle': jobTitle })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('직무 저장 실패');
        }
        return response.text();
    })
    .then(data => {
        addMessageToChatBox('system', '희망 직무가 저장되었습니다: ' + jobTitle);
    })
    .catch(error => {
        console.error(error);
    });
}

// 파일 업로드 및 피드백 요청
function uploadFileAndRequestFeedback(file) {
    const formData = new FormData();
    formData.append('file', file);

    fetch('/feedback/upload', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('파일 업로드 실패');
        }
        return response.json();
    })
    .then(data => {
        addMessageToChatBox('bot', data.gptFeedback);
    })
    .catch(error => {
        console.error(error);
        addMessageToChatBox('system', '피드백 요청 실패');
    });
}

// 채팅창에 메시지 추가
function addMessageToChatBox(sender, message) {
    const chatBox = document.getElementById('chatBox');
    const messageElement = document.createElement('div');
    messageElement.className = sender === 'user' ? 'user-message' : (sender === 'bot' ? 'bot-message' : 'system-message');
    messageElement.innerText = message;
    chatBox.appendChild(messageElement);
    chatBox.scrollTop = chatBox.scrollHeight;
}

// 이벤트 등록
document.addEventListener('DOMContentLoaded', function () {
    const jobTitleForm = document.getElementById('jobTitleForm');
    const fileForm = document.getElementById('fileForm');

    jobTitleForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const jobTitleInput = document.getElementById('jobTitleInput');
        const jobTitle = jobTitleInput.value.trim();
        if (jobTitle) {
            addMessageToChatBox('user', jobTitle);
            setJobTitle(jobTitle);
            jobTitleInput.value = '';
        }
    });

    fileForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const fileInput = document.getElementById('fileInput');
        if (fileInput.files.length > 0) {
            const file = fileInput.files[0];
            addMessageToChatBox('user', '파일 업로드: ' + file.name);
            uploadFileAndRequestFeedback(file);
            fileInput.value = '';
        }
    });
});

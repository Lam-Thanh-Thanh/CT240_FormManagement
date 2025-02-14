const API_URL = "http://localhost:8080/api/responses";

// Hàm gửi câu trả lời khảo sát
async function submitResponse() {
    const responseData = {
        surveyId: "survey999",
        userId: "user789",
        answers: {
            question1: "Answer Atest111",
            question2: "Answer Btest111"
        }
    };

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(responseData)
        });

        const result = await response.json();
        document.getElementById("result").innerText = JSON.stringify(result, null, 2);
    } catch (error) {
        console.error("Lỗi:", error);
    }
}

// Hàm lấy danh sách câu trả lời theo khảo sát
async function getResponsesBySurvey(surveyId) {
    try {
        const response = await fetch(`${API_URL}/survey/${surveyId}`);
        const data = await response.json();
        document.getElementById("result").innerText = JSON.stringify(data, null, 2);
    } catch (error) {
        console.error("Lỗi:", error);
    }
}

// Hàm lấy danh sách câu trả lời theo người dùng
async function getResponsesByUser(userId) {
    try {
        const response = await fetch(`${API_URL}/user/${userId}`);
        const data = await response.json();
        document.getElementById("result").innerText = JSON.stringify(data, null, 2);
    } catch (error) {
        console.error("Lỗi:", error);
    }
}

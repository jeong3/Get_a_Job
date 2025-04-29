package Get_a_Job.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Get_a_Job.domain.FeedbackDTO;
import Get_a_Job.service.feedback.FeedbackService;
import Get_a_Job.service.feedback.QuestionRequest;
import jakarta.servlet.http.HttpSession;

/**
 * 파일 업로드 및 피드백 처리 Controller
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;


	// 1. 희망 직무 설정
    @PostMapping("/setJobTitle")
    public ResponseEntity<String> setJobTitle(@RequestParam String jobTitle) {
        feedbackService.setJobTitle(jobTitle);
        return ResponseEntity.ok("희망 직무가 설정되었습니다: " + jobTitle);
    }

    // 2. 파일 업로드 및 GPT 피드백 생성
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadResume(@RequestParam("file") MultipartFile file,
                                                            @RequestParam("jobTitle") String jobTitle,
                                                            HttpSession session) {
        Map<String, String> result = feedbackService.processFileAndGetFeedback(file, jobTitle);

        // 세션에 피드백 저장 (옵션)
        session.setAttribute("lastFeedback", result.get("feedback"));
        session.setAttribute("lastFeedbackNum", result.get("feedbackNum"));

        return ResponseEntity.ok(result);
    }

    // 3. 피드백 기반 추가 질문
    @PostMapping("/question")
    public ResponseEntity<String> askQuestion(
            @RequestBody QuestionRequest request,
            HttpSession session) {

        String feedbackNum = (String) session.getAttribute("lastFeedbackNum");
        if (feedbackNum == null) {
            return ResponseEntity.badRequest().body("먼저 파일을 업로드하고 피드백을 받아야 합니다.");
        }

        String answer = feedbackService.askQuestionAboutFeedback(feedbackNum, request.getQuestion());

        return ResponseEntity.ok(answer);
    }
    
    // 4. 명시적 피드백 번호로 질문 (직접 호출용)
    @PostMapping("/feedback/ask")
    @ResponseBody
    public String askQuestionDirect(@RequestParam("feedbackNum") String feedbackNum,
                                    @RequestParam("question") String question) {
        return feedbackService.askQuestionAboutFeedback(feedbackNum, question);
    }
}
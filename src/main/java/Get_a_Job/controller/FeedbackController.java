package Get_a_Job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Get_a_Job.domain.FeedbackDTO;
import Get_a_Job.service.feedback.FeedbackService;

/**
 * 파일 업로드 및 피드백 처리 Controller
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // 희망 직무를 입력받아 세션에 저장
    @PostMapping("/setJobTitle")
    public ResponseEntity<String> setJobTitle(@RequestParam String jobTitle) {
        feedbackService.setJobTitle(jobTitle);
        return ResponseEntity.ok("희망 직무가 설정되었습니다: " + jobTitle);
    }

    // 파일 업로드 및 피드백 생성
    @PostMapping("/upload")
    public ResponseEntity<FeedbackDTO> uploadResume(@RequestParam("file") MultipartFile file) {
        FeedbackDTO feedbackDTO = feedbackService.processFileAndGetFeedback(file);
        return ResponseEntity.ok(feedbackDTO);
    }
}

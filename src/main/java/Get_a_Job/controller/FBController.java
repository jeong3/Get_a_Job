package Get_a_Job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Get_a_Job.service.feedback.FeedbackDetailService;
import Get_a_Job.service.feedback.FeedbackHistoryService;
import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("feedback")
public class FBController {
	@Autowired
	FeedbackHistoryService feedbackHistoryService;
	@Autowired
	FeedbackDetailService feedbackDetailService;
	@GetMapping("main")
	public String main(HttpSession session) {
		return "thymeleaf/feedback/feedback";
	}
	@GetMapping("fbHistory")
	public String fbHistory(HttpSession session, Model model) {
		feedbackHistoryService.execute(session, model);
		return "thymeleaf/feedback/fbHistory";
	}
	@GetMapping("feedbackDetail")
	public String feedbackDetail(HttpSession session, String feedbackNum, Model model) {
		feedbackDetailService.execute(session, feedbackNum, model);
		return "thymeleaf/feedback/feedbackDetail";
	}
	
	
}

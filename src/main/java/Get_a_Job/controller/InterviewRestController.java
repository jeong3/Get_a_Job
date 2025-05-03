package Get_a_Job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Get_a_Job.command.InterviewCommand;
import Get_a_Job.service.interview.ChatRoomCreateService;
import Get_a_Job.service.interview.SaveFeedbackService;
import Get_a_Job.service.interview.SaveQAServie;
import jakarta.servlet.http.HttpSession;
@RequestMapping("interview")
@RestController
public class InterviewRestController {
	@Autowired
	ChatRoomCreateService chatRoomCreateService;
	@Autowired
	SaveQAServie saveQAService;
	@Autowired
	SaveFeedbackService saveFeedbackService;
	
	@RequestMapping("chatRoomCreate")
	public String chatRoomCreate(@RequestParam String jobTitle, HttpSession session, Model model) {

		System.out.println("직무"+jobTitle);
		String chatNum = chatRoomCreateService.execute(jobTitle,session,model);
	
	    return chatNum;
		
	}
	@RequestMapping("saveQA")
	public int saveQA(InterviewCommand interviewCommand) {
		System.out.println("커맨드"+interviewCommand);
		saveQAService.execute(interviewCommand);
		return 1;
	}
	
	@RequestMapping("saveFeedback")
	public int saveFeedback(InterviewCommand interviewCommand) {
		saveFeedbackService.execute(interviewCommand);
		return 1;
	}
}

package Get_a_Job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Get_a_Job.service.interview.ChatListService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("interview")
public class InterviewController {
	@Autowired
	ChatListService chatListService;
	
	@GetMapping("main")
	public String main(Model model) {
		
		return "thymeleaf/interview/interview";
	}
	@GetMapping("IvHistory")
	public String IvHistory(HttpSession session, Model model) {
		chatListService.execute(session,model);
		return "thymeleaf/interview/ChatRoomList";
	}
	@GetMapping("ChatDetail")
	public String ChatDetail(String chatNum, Model model) {
		chatListService.chatNumExecute(chatNum, model);
		return "thymeleaf/interview/ChatDetail";
	}
		
}

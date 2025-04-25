package Get_a_Job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("interview")
public class InterviewController {
	@GetMapping("main")
	public String main() {
		
		return "thymeleaf/interview/interview";
	}
}

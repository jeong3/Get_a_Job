package Get_a_Job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("feedback")
public class FBController {
	@GetMapping("main")
	public String main(HttpSession session) {
		return "thymeleaf/feedback/feedback";
	}
	
}

package Get_a_Job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("feedback")
public class FBController {
	@GetMapping("main")
	public String main() {
		return "thymeleaf/feedback/feedback";
	}
	
}

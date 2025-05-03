package Get_a_Job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Get_a_Job.service.mypage.EmpMyPageService;
import Get_a_Job.service.mypage.MemMyPageService;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("myPage")
public class MyPageController {
	@Autowired
	MemMyPageService memMyPageService;
	@Autowired
	EmpMyPageService empMyPageService;
	
	
	@GetMapping("memMyPage")
	public String memMyPage(HttpSession session, Model model) {
		memMyPageService.execute(session,model);
		
		return "thymeleaf/myPage/memMyPage";
	}
	@GetMapping("empMyPage")
	public String empMyPage(HttpSession session, Model model) {
		empMyPageService.execute(session,model);
		
		return "thymeleaf/myPage/empMyPage";
	}
	
}

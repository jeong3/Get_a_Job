package Get_a_Job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Get_a_Job.command.MemberCommand;
import Get_a_Job.service.AutoNumService;
import ch.qos.logback.core.model.Model;

@RequestMapping("member")
@Controller
public class MemberController {
	@Autowired
	AutoNumService autoNumService;
	
	
	@GetMapping("memberList")
	public String memberList() {
		return "thymeleaf/member/memberList";
	}
	@GetMapping("memberRegist")
	public String memberRegist(Model model) {
		String autoNum = autoNumService.execute("mem_","member_num",5,"members");
		System.out.println(autoNum+"asdasdasd");
		return "thymeleaf/member/memberRegist";
	}
	@PostMapping("memberRegist")
	public String memberRegist(@Validated MemberCommand memberCommand, BindingResult result) {
		
		
		return "Redirect:memberList";
	}
	

}

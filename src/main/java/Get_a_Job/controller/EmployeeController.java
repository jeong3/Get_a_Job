package Get_a_Job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Get_a_Job.service.AutoNumService;



@Controller
@RequestMapping("emp")
public class EmployeeController {
	@GetMapping("empList")
	public String empList() {
		return "thymeleaf/emp/empList";
	}
	@Autowired
	AutoNumService autoNumService;
	@GetMapping("empRegist")
	public String empRegist(Model model) {
		String autoNum = autoNumService.execute("emp_", "emp_num", 5, "employees");
		model.addAttribute(autoNum);
		return "thymeleaf/emp/empRegist";
	}
	
}

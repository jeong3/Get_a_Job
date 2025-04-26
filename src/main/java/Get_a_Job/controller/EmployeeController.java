package Get_a_Job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Get_a_Job.command.EmployeeCommand;
import Get_a_Job.service.AutoNumService;
import Get_a_Job.service.employee.EmployeeDeleteService;
import Get_a_Job.service.employee.EmployeeDetailService;
import Get_a_Job.service.employee.EmployeeListService;
import Get_a_Job.service.employee.EmployeeUpdateService;
import Get_a_Job.service.employee.EmployeeWriteService;







@Controller
@RequestMapping("emp")
public class EmployeeController {
	@Autowired
	AutoNumService autoNumService;
	@Autowired
	EmployeeListService employeeListService;
	@Autowired
	EmployeeWriteService employeeWriteService;
	@Autowired
	EmployeeDetailService employeeDetailService;
	@Autowired
	EmployeeUpdateService employeeUpdateService;
	@Autowired
	EmployeeDeleteService employeeDeleteService;
	@GetMapping("empList")
	public String empList(Model model) {
		employeeListService.execute(model);
		return "thymeleaf/emp/empList";
	}
	@GetMapping("empRegist")
	public String empRegist(Model model) {
		String autoNum = autoNumService.execute("emp_", "emp_num", 5, "employees");
		EmployeeCommand employeeCommand = new EmployeeCommand();
		employeeCommand.setEmpNum(autoNum);
		model.addAttribute("employeeCommand", employeeCommand);
		return "thymeleaf/emp/empRegist";
	}
	@PostMapping("empRegist")
	public String empRegist(@Validated EmployeeCommand employeeCommand
			, BindingResult result) {
		if (result.hasErrors()) {
			return "thymeleaf/emp/empRegist";
		}
		if (!employeeCommand.isEmpPwEqualPwCon()) {
			System.out.println("비밀번호 불일치");
			result.rejectValue("empPwCon", "employeeCommand.empPwCon", "비밀번호 불일치");
			return "thymeleaf/emp/empRegist";
		}
		employeeWriteService.execute(employeeCommand);
		return "redirect:empList";
	}
	@GetMapping("empUpdate")
	public String empUpdate(String empNum, Model model) {
		employeeDetailService.execute(empNum, model);
		return "thymeleaf/emp/empUpdate";
	}
	@PostMapping("empUpdate")
	public String empUpdate(EmployeeCommand employeeCommand) {
		employeeUpdateService.execute(employeeCommand);
		return "redirect:empList";
	}
	@GetMapping("empDelete")
	public String empDelete(String empNum) {
		employeeDeleteService.execute(empNum);
		return "redirect:empList";
	}
	
}

package Get_a_Job.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmployeeCommand {
	String empNum;
	String empId;
	@Pattern(regexp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-+]).{8,}$",
			message = "영문자와 숫자 그리고 특수문자가 포함된 8글자 이상")
	String empPw;
	@NotBlank(message = "비밀번호 확인을 입력해주세요.")
	String empPwCon;
	String empName;
	String empPhone;
	String empEmail;
	
	public boolean isEmpPwEqualPwCon() {
		return empPw.equals(empPwCon);
	}
}

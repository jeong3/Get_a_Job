package Get_a_Job.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Alias("empDTO")
public class EmployeeDTO {
	String empNum;
	String empId;
	String empPw;
	String empName;
	String empPhone;
	String empEmail;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date empRegist;
}

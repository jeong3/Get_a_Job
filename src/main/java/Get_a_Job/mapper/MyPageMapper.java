package Get_a_Job.mapper;

import org.apache.ibatis.annotations.Mapper;

import Get_a_Job.domain.EmployeeDTO;
import Get_a_Job.domain.MemberDTO;

@Mapper
public interface MyPageMapper {

	MemberDTO memMyPageSelect(String memberId);

	EmployeeDTO empMyPageSelect(String empId);

}

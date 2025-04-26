package Get_a_Job.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import Get_a_Job.domain.EmployeeDTO;

@Mapper
public interface EmployeeMapper {
	public Integer employeeInsert(EmployeeDTO dto);
	public List<EmployeeDTO> employeeSelectAll();
	public EmployeeDTO employeeSelectOne(String empNum);
	public Integer employeeUdpate(EmployeeDTO dto);
	public Integer employeeDelete(String empNum);

}

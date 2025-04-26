package Get_a_Job.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import Get_a_Job.domain.MemberDTO;

@Mapper
public interface MemberMapper {

	int memberInsert(MemberDTO dto);

	List<MemberDTO> memberSelectAll();

	MemberDTO memberSelectOne(String memberNum);

	int memberUpdate(MemberDTO dto);

	int memberDelete(String memberNum);
	
}

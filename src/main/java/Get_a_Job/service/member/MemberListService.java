package Get_a_Job.service.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import Get_a_Job.domain.MemberDTO;
import Get_a_Job.mapper.MemberMapper;
@Service
public class MemberListService {
	@Autowired
	MemberMapper memberMapper;
	public void execute(Model model) {
		List<MemberDTO> list = memberMapper.memberSelectAll();
		
		model.addAttribute("list", list);
		
	}

}

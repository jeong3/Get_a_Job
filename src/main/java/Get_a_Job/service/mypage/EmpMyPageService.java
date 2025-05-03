package Get_a_Job.service.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import Get_a_Job.domain.AuthInfoDTO;
import Get_a_Job.domain.EmployeeDTO;
import Get_a_Job.mapper.MyPageMapper;
import jakarta.servlet.http.HttpSession;

@Service
public class EmpMyPageService {
	@Autowired
	MyPageMapper myPageMapper;
	

	public void execute(HttpSession session, Model model) {
		AuthInfoDTO auth = (AuthInfoDTO) session.getAttribute("auth");
		EmployeeDTO dto = myPageMapper.empMyPageSelect(auth.getUserId());
		model.addAttribute("dto", dto);
		
	}

}

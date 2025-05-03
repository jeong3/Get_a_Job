package Get_a_Job.service.interview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import Get_a_Job.domain.AuthInfoDTO;
import Get_a_Job.domain.ChatRoomDTO;
import Get_a_Job.mapper.InterviewMapper;
import jakarta.servlet.http.HttpSession;

@Service
public class ChatListService {
	@Autowired
	InterviewMapper interviewMapper;
	
	
	public void execute(HttpSession session, Model model) {
		AuthInfoDTO auth = (AuthInfoDTO) session.getAttribute("auth");
		List<ChatRoomDTO> list = interviewMapper.ChatListSelectAll(auth.getUserId());
		model.addAttribute("list", list);
	}
	
	public void chatNumExecute(String chatNum, Model model) {
		ChatRoomDTO dto = interviewMapper.ChatDetail(chatNum);
		model.addAttribute("dto", dto);
	}
	

}

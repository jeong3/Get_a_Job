package Get_a_Job.service.interview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import Get_a_Job.domain.AuthInfoDTO;
import Get_a_Job.mapper.InterviewMapper;
import Get_a_Job.service.AutoNumService;
import jakarta.servlet.http.HttpSession;

@Service
public class ChatRoomCreateService {
	@Autowired
	InterviewMapper interviewMapper;
	@Autowired
	AutoNumService autoNumService;
	public String execute(String jobTitle, HttpSession session, Model model) {
		AuthInfoDTO auth = (AuthInfoDTO) session.getAttribute("auth");
		String chatNum = autoNumService.execute("chat_", "chat_num", 6, "chat_room");
		System.out.println("서비스"+chatNum+jobTitle+auth.getUserId());
		interviewMapper.ChatRoomInsert(chatNum, jobTitle, auth.getUserId());
		return chatNum;
	}

}

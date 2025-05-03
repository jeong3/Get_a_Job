package Get_a_Job.service.feedback;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import Get_a_Job.domain.AuthInfoDTO;
import Get_a_Job.domain.FeedbackDTO;
import Get_a_Job.mapper.FeedbackMapper;
import jakarta.servlet.http.HttpSession;

@Service
public class FeedbackHistoryService {
	@Autowired
	FeedbackMapper feedbackMapper;
	public void execute(HttpSession session, Model model) {
		AuthInfoDTO auth = (AuthInfoDTO)session.getAttribute("auth");
		String userId = auth.getUserId();
		List<FeedbackDTO> list = feedbackMapper.selectFeedbackHistory(userId);
		model.addAttribute("list", list);
	}
}

package Get_a_Job.service.feedback;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import Get_a_Job.domain.FeedbackDTO;
import Get_a_Job.domain.FeedbackQuestionDTO;
import Get_a_Job.mapper.FeedbackMapper;
import jakarta.servlet.http.HttpSession;

@Service
public class FeedbackDetailService {
	@Autowired
	FeedbackMapper feedbackMapper;
	public void execute(HttpSession session, String feedbackNum, Model model) {
		FeedbackDTO fbdto = feedbackMapper.selectFeedbackDetail(feedbackNum);
		List<FeedbackQuestionDTO> fblist = feedbackMapper.selectFeedBackQnA(feedbackNum);
		model.addAttribute("fbdto", fbdto);
		model.addAttribute("fblist", fblist);
		
	}
}

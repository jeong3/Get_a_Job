package Get_a_Job.service.interview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Get_a_Job.command.InterviewCommand;
import Get_a_Job.mapper.InterviewMapper;

@Service
public class SaveFeedbackService {
	@Autowired
	InterviewMapper interviewMapper;
	public void execute(InterviewCommand interviewCommand) {
		
		System.out.println("SaveFeedbackService커맨드"+interviewCommand);
		interviewMapper.ChatRoomUpdate(interviewCommand.getChatNum(), interviewCommand.getQuestion());
		
	}

}

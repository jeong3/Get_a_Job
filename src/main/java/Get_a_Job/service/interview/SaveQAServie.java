package Get_a_Job.service.interview;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Get_a_Job.command.InterviewCommand;
import Get_a_Job.domain.InterviewDTO;
import Get_a_Job.mapper.InterviewMapper;
import Get_a_Job.service.AutoNumService;

@Service
public class SaveQAServie {
	@Autowired
	AutoNumService autoNumService;
	@Autowired
	InterviewMapper interviewMapper;
	
	
	
	public void execute(InterviewCommand interviewCommand) {
		String interviewNum = autoNumService.execute("int_", "interview_num", 5, "interview");
		System.out.println("interviewNum"+interviewNum);
		interviewCommand.setInterviewNum(interviewNum);
		InterviewDTO interviewDTO = new InterviewDTO();
		System.out.println("dto"+interviewDTO);
		BeanUtils.copyProperties(interviewCommand, interviewDTO);
		interviewMapper.InterviewInsert(interviewDTO);
	}

}

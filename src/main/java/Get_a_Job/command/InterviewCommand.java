package Get_a_Job.command;

import lombok.Data;

@Data
public class InterviewCommand {
	String interviewNum;
	String question;
	String userAnswer;
	String chatNum;
}

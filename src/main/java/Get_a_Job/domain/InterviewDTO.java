package Get_a_Job.domain;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("interviewDTO")
public class InterviewDTO {
	String interviewNum;
	String question;
	String userAnswer;
	String chatNum;
}

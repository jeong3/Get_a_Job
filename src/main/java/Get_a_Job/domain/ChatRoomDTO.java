package Get_a_Job.domain;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("chatRoomDTO")
public class ChatRoomDTO {
	String chatNum;
	String gptFeedback;
	Date interviewDate;
	String jobTitle;
	String userId;
	List<InterviewDTO> interviewDTO;
}

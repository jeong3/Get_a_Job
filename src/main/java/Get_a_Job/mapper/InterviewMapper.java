package Get_a_Job.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.Alias;

import Get_a_Job.domain.InterviewDTO;

@Mapper
public interface InterviewMapper {

	int ChatRoomInsert(String chatNum, String jobTitle, String userId);

	int InterviewInsert(InterviewDTO interviewDTO);

	int ChatRoomUpdate(String chatNum, String gptFeedback);

}

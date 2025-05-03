package Get_a_Job.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import Get_a_Job.domain.ChatRoomDTO;
import Get_a_Job.domain.InterviewDTO;

@Mapper
public interface InterviewMapper {

	int ChatRoomInsert(String chatNum, String jobTitle, String userId);

	int InterviewInsert(InterviewDTO interviewDTO);

	int ChatRoomUpdate(String chatNum, String gptFeedback);

	List<ChatRoomDTO> ChatListSelectAll(String userId);

	ChatRoomDTO ChatDetail(String chatNum);

}

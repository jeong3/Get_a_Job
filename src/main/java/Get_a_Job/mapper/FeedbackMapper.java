package Get_a_Job.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import Get_a_Job.domain.FeedbackDTO;
import Get_a_Job.domain.FeedbackQuestionDTO;

@Mapper
public interface FeedbackMapper {
    void insertFeedback(FeedbackDTO feedbackDTO);
    FeedbackDTO selectFeedbackById(Long feedbackId);
	void insertFeedbackQuestion(FeedbackQuestionDTO feedbackQuestionDTO);
	List<FeedbackDTO> selectFeedbackHistory(String userId);
	FeedbackDTO selectFeedbackDetail(String feedbackNum);
	List<FeedbackQuestionDTO> selectFeedBackQnA(String feedbackNum);

}
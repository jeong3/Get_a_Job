package Get_a_Job.mapper;

import org.apache.ibatis.annotations.Mapper;

import Get_a_Job.domain.FeedbackDTO;
import Get_a_Job.domain.FeedbackQuestionDTO;

@Mapper
public interface FeedbackMapper {
    void insertFeedback(FeedbackDTO feedbackDTO);
    FeedbackDTO selectFeedbackById(Long feedbackId);
	void insertFeedbackQuestion(FeedbackQuestionDTO feedbackQuestionDTO);

}
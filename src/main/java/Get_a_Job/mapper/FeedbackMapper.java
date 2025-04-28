package Get_a_Job.mapper;

import org.apache.ibatis.annotations.Mapper;

import Get_a_Job.domain.FeedbackDTO;

@Mapper
public interface FeedbackMapper {
    void insertFeedback(FeedbackDTO feedbackDTO);
}
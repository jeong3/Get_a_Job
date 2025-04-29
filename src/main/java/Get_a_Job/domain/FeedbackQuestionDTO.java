package Get_a_Job.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("feedbackQuestionDTO")
public class FeedbackQuestionDTO {
    private Long questionId;
    private String feedbackNum;
    private String question;
    private String answer;
    private Date createdAt;
}

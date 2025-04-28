package Get_a_Job.domain;

import org.apache.ibatis.type.Alias;

import lombok.Data;

/**
 * Oracle DB 저장 및 반환용 DTO
 */
@Data
@Alias("feedbackDTO")
public class FeedbackDTO {
    private String feedbackNum;
    private String userId;
    private String originalFileName;
    private String savedFileName;
    private String filePath;
    private String gptFeedback;
    private String jobTitle;
}
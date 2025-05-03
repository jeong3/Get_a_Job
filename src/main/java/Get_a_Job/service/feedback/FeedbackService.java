package Get_a_Job.service.feedback;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Get_a_Job.domain.AuthInfoDTO;
import Get_a_Job.domain.FeedbackDTO;
import Get_a_Job.domain.FeedbackQuestionDTO;
import Get_a_Job.mapper.FeedbackMapper;
import jakarta.servlet.http.HttpSession;

/**
 * 파일 저장, 변환, GPT 호출 및 Oracle 저장 담당 Service
 */
@Service
public class FeedbackService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private String jobTitle = "일반"; // 기본 직무명

    @Autowired
    private ChatGPTService chatGPTService;

    @Autowired
    private FeedbackMapper feedbackMapper;

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    // 파일 저장 + GPT 요청 + DB 저장
    public Map<String, String> processFileAndGetFeedback(MultipartFile file, String jobTitle, HttpSession session) {
        try {
            // 파일 저장
            String savedFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + savedFileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            // 파일 텍스트 추출
            String extractedText = extractTextFromDocx(filePath.toFile());

            // GPT 피드백 요청
            String prompt = "사용자의 희망 직무는 \"" + jobTitle + "\" 입니다.\n아래 이력서/자소서에 대해 피드백을 제공해주세요:\n\n" + extractedText;
            String gptFeedback = chatGPTService.getGPTFeedback(prompt);

            // Session으로 ID 호출
            AuthInfoDTO auth = (AuthInfoDTO)session.getAttribute("auth");
            String userId = auth.getUserId();
            
            // DTO 생성 및 저장
            FeedbackDTO dto = new FeedbackDTO();
            dto.setUserId(userId);
            dto.setOriginalFileName(file.getOriginalFilename());
            dto.setSavedFileName(savedFileName);
            dto.setFilePath(filePath.toString());
            dto.setGptFeedback(gptFeedback);
            dto.setJobTitle(jobTitle);

            feedbackMapper.insertFeedback(dto); // 여기에 selectKey로 피드백 번호가 생성됨

            // 결과 Map에 포함해서 리턴
            Map<String, String> result = new HashMap<>();
            result.put("feedback", gptFeedback);
            result.put("feedbackNum", dto.getFeedbackNum()); // ★ 여기 추가
            return result;

        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }



    private String extractTextFromDocx(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {

            StringBuilder textBuilder = new StringBuilder();
            document.getParagraphs().forEach(p -> textBuilder.append(p.getText()).append("\n"));

            return textBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "파일을 읽는 데 실패했습니다.";
        }
    }

    // 기존 피드백에 대해 추가 질문하고 저장
    public String askQuestionAboutFeedback(String feedbackNum, String question) {
        String gptAnswer = chatGPTService.getGPTFeedback(question);

        FeedbackQuestionDTO questionDTO = new FeedbackQuestionDTO();
        questionDTO.setFeedbackNum(feedbackNum);
        questionDTO.setQuestion(question);
        questionDTO.setAnswer(gptAnswer);

        feedbackMapper.insertFeedbackQuestion(questionDTO);

        return gptAnswer;
    }
}

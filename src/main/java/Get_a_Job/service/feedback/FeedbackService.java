package Get_a_Job.service.feedback;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Get_a_Job.domain.FeedbackDTO;
import Get_a_Job.mapper.FeedbackMapper;

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

    public FeedbackDTO processFileAndGetFeedback(MultipartFile file) {
        try {
            // 파일 저장
            String savedFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + savedFileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            // 파일 텍스트 읽기
            String extractedText = extractTextFromDocx(filePath.toFile());

            // ChatGPT API 호출
            String prompt = "사용자의 희망 직무는 \"" + jobTitle + "\" 입니다.\n아래 이력서/자소서에 대해 구체적인 피드백을 작성해주세요:\n\n" + extractedText;
            String gptFeedback = chatGPTService.getGPTFeedback(prompt);

            // DB 저장
            FeedbackDTO dto = new FeedbackDTO();
            dto.setUserId("test_user");
            dto.setOriginalFileName(file.getOriginalFilename());
            dto.setSavedFileName(savedFileName);
            dto.setFilePath(filePath.toString());
            dto.setGptFeedback(gptFeedback);
            dto.setJobTitle(jobTitle);

            feedbackMapper.insertFeedback(dto);

            return dto;
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 및 처리 실패", e);
        }
    }

    public String extractTextFromDocx(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {
             
            StringBuilder textBuilder = new StringBuilder();
            document.getParagraphs().forEach(p -> textBuilder.append(p.getText()).append("\n"));
            // 표가 있어도 무시 (표 추출 안 함)

            return textBuilder.toString();
        } catch (Exception e) {
            // 파일이 손상됐거나 파싱 실패해도 서버 죽지 않게 함
            e.printStackTrace();
            return "파일을 읽는 데 실패했습니다.";
        }
    }
}
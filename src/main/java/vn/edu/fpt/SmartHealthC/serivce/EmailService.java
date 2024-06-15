package vn.edu.fpt.SmartHealthC.serivce;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LessonRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;

import java.util.List;

public interface EmailService {
    boolean sendMail(String to, String subject, String body);
    String generateRandomCode(int codeLength);
}

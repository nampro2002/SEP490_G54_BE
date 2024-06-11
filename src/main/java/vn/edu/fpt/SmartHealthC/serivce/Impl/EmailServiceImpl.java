package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FAQRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.FAQRepository;
import vn.edu.fpt.SmartHealthC.serivce.EmailService;
import vn.edu.fpt.SmartHealthC.serivce.FAQService;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    private String from = "ntkien651@gmail.com";
    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public boolean sendMail(String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);

            javaMailSender.send(mimeMessage);

            return true;

        } catch (Exception e) {
            return false;
        }

    }
}

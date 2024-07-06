package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    private MimeMessage mimeMessage;

    @BeforeEach
    void setUp() {
        mimeMessage = mock(MimeMessage.class);
        lenient().when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void testSendMail_Success() {
        // Arrange
        String to = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            doNothing().when(javaMailSender).send(any(MimeMessage.class));

            // Act
            boolean result = emailService.sendMail(to, subject, body);

            // Assert
            assertTrue(result);
            verify(javaMailSender, times(1)).createMimeMessage();
            verify(javaMailSender, times(1)).send(mimeMessage);
        } catch (Exception e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    void testSendMail_Failure() {
        // Arrange
        String to = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        when(javaMailSender.createMimeMessage()).thenThrow(new RuntimeException());

        // Act
        boolean result = emailService.sendMail(to, subject, body);

        // Assert
        assertFalse(result);
        verify(javaMailSender, times(1)).createMimeMessage();
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void testGenerateRandomCode_Length6() {
        // Act
        String code = emailService.generateRandomCode(6);

        // Assert
        assertNotNull(code);
        assertEquals(6, code.length());
    }

    @Test
    void testGenerateRandomCode_Length10() {
        // Act
        String code = emailService.generateRandomCode(10);

        // Assert
        assertNotNull(code);
        assertEquals(10, code.length());
    }
}
package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ForgetPasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.repository.ForgetPasswordCodeRepository;
import vn.edu.fpt.SmartHealthC.serivce.EmailService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ForgetPasswordCodeServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ForgetPasswordCodeRepository forgetPasswordCodeRepository;

    @InjectMocks
    private ForgetPasswordCodeServiceImpl forgetPasswordCodeService;

    private Account testAccount;
    private ForgetPasswordCodeDTO forgetPasswordCodeDTO;

    @BeforeEach
    void setUp() {
        testAccount = Account.builder()
                .Id(1)
                .email("test@example.com")
                .password("oldPassword")
                .build();

        forgetPasswordCodeDTO = ForgetPasswordCodeDTO.builder()
                .email("test@example.com")
                .code("123456")
                .password("newPassword")
                .build();
    }

    @Test
    void testSendEmailCode_Success() throws ParseException {
        // Arrange
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(testAccount));
        when(emailService.generateRandomCode(anyInt())).thenReturn("123456");
        when(emailService.sendMail(anyString(), anyString(), anyString())).thenReturn(true);
        when(forgetPasswordCodeRepository.save(any(ForgetPasswordCode.class))).thenReturn(null);

        // Act
        String result = forgetPasswordCodeService.sendEmailCode("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals("123456", result);
        verify(forgetPasswordCodeRepository, times(1)).save(any(ForgetPasswordCode.class));
    }

    @Test
    void testSendEmailCode_EmailNotExisted() {
        // Arrange
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act and Assert
        AppException exception = assertThrows(AppException.class, () -> forgetPasswordCodeService.sendEmailCode("test@example.com"));
        assertEquals(ErrorCode.EMAIL_NOT_EXISTED, exception.getErrorCode());
    }

    @Test
    void testSendEmailCode_SendEmailFail() {
        // Arrange
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(testAccount));
        when(emailService.generateRandomCode(anyInt())).thenReturn("123456");
        when(emailService.sendMail(anyString(), anyString(), anyString())).thenReturn(false);

        // Act and Assert
        AppException exception = assertThrows(AppException.class, () -> forgetPasswordCodeService.sendEmailCode("test@example.com"));
        assertEquals(ErrorCode.SEND_EMAIL_FAIL, exception.getErrorCode());
    }

    @Test
    void testVerifyAndChangePassword_Success() throws ParseException {
        // Arrange
        LocalDateTime futureDateTime = LocalDateTime.now().plusMinutes(5);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expirationDate = dateFormat.parse(futureDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        ForgetPasswordCode forgetPasswordCode = ForgetPasswordCode.builder()
                .code("123456")
                .accountId(testAccount)
                .expiredDate(expirationDate)
                .build();

        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(testAccount));
        when(forgetPasswordCodeRepository.findRecordByCodeAndAccount(any(Account.class), anyString())).thenReturn(Optional.of(forgetPasswordCode));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        boolean result = forgetPasswordCodeService.verifyAndChangePassword(forgetPasswordCodeDTO);

        // Assert
        assertTrue(result);
        assertEquals("encodedPassword", testAccount.getPassword());
        verify(accountRepository, times(1)).save(testAccount);
        verify(forgetPasswordCodeRepository, times(1)).delete(forgetPasswordCode);
    }

    @Test
    void testVerifyAndChangePassword_CodeExpired() throws ParseException {
        // Arrange
        ForgetPasswordCode forgetPasswordCode = ForgetPasswordCode.builder()
                .code("123456")
                .accountId(testAccount)
                .expiredDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(LocalDateTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                .build();

        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(testAccount));
        when(forgetPasswordCodeRepository.findRecordByCodeAndAccount(any(Account.class), anyString())).thenReturn(Optional.of(forgetPasswordCode));

        // Act and Assert
        AppException exception = assertThrows(AppException.class, () -> forgetPasswordCodeService.verifyAndChangePassword(forgetPasswordCodeDTO));
        assertEquals(ErrorCode.CODE_EXPIRED, exception.getErrorCode());
    }

    @Test
    void testVerifyAndChangePassword_CodeInvalid() {
        // Arrange
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(testAccount));
        when(forgetPasswordCodeRepository.findRecordByCodeAndAccount(any(Account.class), anyString())).thenReturn(Optional.empty());

        // Act and Assert
        AppException exception = assertThrows(AppException.class, () -> forgetPasswordCodeService.verifyAndChangePassword(forgetPasswordCodeDTO));
        assertEquals(ErrorCode.CODE_INVALID, exception.getErrorCode());
    }

    @Test
    void testVerifyAndChangePassword_EmailNotExisted() {
        // Arrange
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act and Assert
        AppException exception = assertThrows(AppException.class, () -> forgetPasswordCodeService.verifyAndChangePassword(forgetPasswordCodeDTO));
        assertEquals(ErrorCode.EMAIL_NOT_EXISTED, exception.getErrorCode());
    }
}
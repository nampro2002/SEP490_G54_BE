package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ChangePasswordCodeDTO;
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
    private ChangePasswordCodeDTO changePasswordCodeDTO;
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
                .build();
        changePasswordCodeDTO = ChangePasswordCodeDTO.builder()
                .email("test@example.com")
                .password("password")
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
        assertEquals("Gửi mã xác thức quên mật khẩu thành công", result);
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

        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(testAccount));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        boolean result = forgetPasswordCodeService.verifyAndChangePassword(changePasswordCodeDTO);

        // Assert
        assertTrue(result);
        assertEquals("encodedPassword", testAccount.getPassword());
        verify(accountRepository, times(1)).save(testAccount);
    }
    @Test
    void testVerifyAndChangePassword_Fail() throws ParseException {
//         Arrange
        changePasswordCodeDTO.setEmail("fail@gmail.com");

        // Mock the repository to return empty, simulating a non-existing email
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act and Assert
        AppException exception = assertThrows(AppException.class, () -> forgetPasswordCodeService.verifyAndChangePassword(changePasswordCodeDTO));
        assertEquals(ErrorCode.EMAIL_NOT_EXISTED, exception.getErrorCode());
    }
    @Test
    void testCheckEmailCodeInvalid_Success() throws ParseException {
    //         Arrange
        String dateString = "2030-01-01 12:00:00";
        ForgetPasswordCode forgetPasswordCode = ForgetPasswordCode.builder()
                .code("123456")
                .accountId(testAccount)
                .expiredDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString))
                .build();
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(testAccount));
        when(forgetPasswordCodeRepository.findRecordByCodeAndAccount(any(Account.class), anyString())).thenReturn(Optional.of(forgetPasswordCode));
        doNothing().when(forgetPasswordCodeRepository).delete(forgetPasswordCode);

        // Act
        boolean result = forgetPasswordCodeService.checkEmailCodeInvalid(forgetPasswordCodeDTO);

        // Assert
        assertTrue(result);
    }
    @Test
    void testVerifyAndChangePassword_EmailNotExisted() throws ParseException {
        // Arrange
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act and Assert
        AppException exception = assertThrows(AppException.class, () -> forgetPasswordCodeService.checkEmailCodeInvalid(forgetPasswordCodeDTO));
        assertEquals(ErrorCode.EMAIL_NOT_EXISTED, exception.getErrorCode());
    }

    @Test
    void testCheckEmailCodeInvalid_CodeExpire() throws ParseException {
//         Arrange
        String dateString = "2002-01-01 12:00:00";
        ForgetPasswordCode forgetPasswordCode = ForgetPasswordCode.builder()
                .code("123456")
                .accountId(testAccount)
                .expiredDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString))
                .build();
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(testAccount));
        when(forgetPasswordCodeRepository.findRecordByCodeAndAccount(any(Account.class), anyString())).thenReturn(Optional.ofNullable(forgetPasswordCode));

        // Act and Assert
        AppException exception = assertThrows(AppException.class, () -> forgetPasswordCodeService.checkEmailCodeInvalid(forgetPasswordCodeDTO));
        assertEquals(ErrorCode.CODE_EXPIRED, exception.getErrorCode());
    }

    @Test
    void testVerifyAndChangePassword_CodeInvalid() throws ParseException {
//         Arrange
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(testAccount));
        when(forgetPasswordCodeRepository.findRecordByCodeAndAccount(any(Account.class), anyString())).thenReturn(Optional.empty());

        // Act and Assert
        Boolean check = forgetPasswordCodeService.checkEmailCodeInvalid(forgetPasswordCodeDTO);
        assertFalse(check);
    }


}
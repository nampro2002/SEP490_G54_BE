package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointmentStatus;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserLessonDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserMedicalHistoryDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.repository.UserMedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.Impl.UserMedicalHistoryServiceImpl;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserMedicalHistoryServiceImplTest {

    @Mock
    private UserMedicalHistoryRepository userMedicalHistoryRepository;

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private AppUserService appUserService;

    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;

    @InjectMocks
    private UserMedicalHistoryServiceImpl userMedicalHistoryService;
    private Account account;
    private AppUser testAppUser;
    private WebUser testWebUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        account = Account.builder()
                .Id(1)
                .email("john.doe@example.com")
                .password("encoded_password")
                .type(TypeAccount.USER)
                .isActive(true)
                .isDeleted(false)
                .build();
        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");
        // Khởi tạo WebUser mock
        testWebUser = new WebUser();
        testWebUser.setId(1);
        testWebUser.setUserName("Test Doctor");
        testAppUser.setAccountId(account);
    }


    @Test
    void testCreateUserMedicalHistory_ValidData() {
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");
        // Arrange
        UserMedicalHistoryDTO userMedicalHistoryDTO = new UserMedicalHistoryDTO();
        userMedicalHistoryDTO.setConditionId(1);

        AppUser appUser = new AppUser();
        appUser.setId(1);

        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setId(1);

        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.of(testAppUser));
        when(appUserService.findAppUserByEmail("Test@gmail.com"))
                .thenReturn(testAppUser);
        when(medicalHistoryRepository.findById(1)).thenReturn(Optional.of(medicalHistory));
        when(userMedicalHistoryRepository.save(any(UserMedicalHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserMedicalHistory createdUserMedicalHistory = userMedicalHistoryService.createUserMedicalHistory(userMedicalHistoryDTO);

        // Assert
        assertNotNull(createdUserMedicalHistory);
        assertEquals(testAppUser, createdUserMedicalHistory.getAppUserId());
        assertEquals(medicalHistory, createdUserMedicalHistory.getConditionId());
    }

    @Test
    void testCreateUserMedicalHistory_AppUserNotFound() {
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");
        // Arrange
        UserMedicalHistoryDTO userMedicalHistoryDTO = new UserMedicalHistoryDTO();
        userMedicalHistoryDTO.setConditionId(1);

        AppUser appUser = new AppUser();
        appUser.setId(1);

        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setId(1);
        when(appUserService.findAppUserByEmail("Test@gmail.com"))
                .thenThrow(new AppException(ErrorCode.APP_USER_NOT_FOUND));
        when(appUserRepository.findByAccountEmail(any(String.class))).thenReturn(Optional.of(appUser));


        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () ->userMedicalHistoryService.createUserMedicalHistory(userMedicalHistoryDTO));

        // Act and Assert
        assertEquals(ErrorCode.APP_USER_NOT_FOUND ,exception.getErrorCode());
    }

    @Test
    void testCreateUserMedicalHistory_MedicalHistoryNotFound() {
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");
        // Arrange
        UserMedicalHistoryDTO userMedicalHistoryDTO = new UserMedicalHistoryDTO();
        userMedicalHistoryDTO.setConditionId(1);

        AppUser appUser = new AppUser();
        appUser.setId(1);

        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setId(1);
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.of(testAppUser));
        when(appUserService.findAppUserByEmail("Test@gmail.com"))
                .thenReturn(testAppUser);
        when(medicalHistoryRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> userMedicalHistoryService.createUserMedicalHistory(userMedicalHistoryDTO));
    }

    @Test
    void testGetUserMedicalHistoryById_ValidId() {
        // Arrange
        UserMedicalHistory userMedicalHistory = new UserMedicalHistory();
        userMedicalHistory.setId(1);

        when(userMedicalHistoryRepository.findById(1)).thenReturn(Optional.of(userMedicalHistory));

        // Act
        UserMedicalHistory retrievedUserMedicalHistory = userMedicalHistoryService.getUserMedicalHistoryById(1);

        // Assert
        assertNotNull(retrievedUserMedicalHistory);
        assertEquals(userMedicalHistory.getId(), retrievedUserMedicalHistory.getId());
    }

    @Test
    void testGetUserMedicalHistoryById_InvalidId() {
        // Arrange
        when(userMedicalHistoryRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> userMedicalHistoryService.getUserMedicalHistoryById(1));
    }
}
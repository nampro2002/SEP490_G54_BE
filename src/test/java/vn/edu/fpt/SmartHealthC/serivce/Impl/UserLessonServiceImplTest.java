package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserLessonDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.UserLessonRepository;
import vn.edu.fpt.SmartHealthC.repository.UserMedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserLessonServiceImplTest {

    @Mock
    private UserLessonRepository userLessonRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private UserMedicalHistoryRepository userMedicalHistoryRepository;

    @InjectMocks
    private UserMedicalHistoryServiceImpl userMedicalHistoryService;
    @Mock
    private AppUserService appUserService;


    @InjectMocks
    private UserLessonServiceImpl userLessonService;

    private UserLessonDTO userLessonDTO;
    private UserLesson userLesson;
    private AppUser appUser;
    private Account account;
    private AppUser testAppUser;
    private WebUser testWebUser;
    @BeforeEach
    void setUp() {
        // Initialize test data
        userLessonDTO = new UserLessonDTO();
        userLessonDTO.setLessonId(1);

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

        userLesson = new UserLesson();
        userLesson.setId(1);
        userLesson.setAppUserId(testAppUser);
        userLesson.setLesson(1);
        userLesson.setLessonDate(new Date());
    }

    @Test
    void testGetAllUserMedicalHistory() {
        // Arrange
        List<UserMedicalHistory> mockMedicalHistories = new ArrayList<>();
        mockMedicalHistories.add(new UserMedicalHistory());
        mockMedicalHistories.add(new UserMedicalHistory());

        when(userMedicalHistoryRepository.findAll()).thenReturn(mockMedicalHistories);

        // Act
        List<UserMedicalHistory> retrievedMedicalHistories = userMedicalHistoryService.getAllUserMedicalHistory();

        // Assert
        assertNotNull(retrievedMedicalHistories);
        assertEquals(2, retrievedMedicalHistories.size());
    }

    @Test
    void testGetAllUserMedicalHistory_EmptyList() {
        // Arrange
        List<UserMedicalHistory> mockMedicalHistories = new ArrayList<>();

        when(userMedicalHistoryRepository.findAll()).thenReturn(mockMedicalHistories);

        // Act
        List<UserMedicalHistory> retrievedMedicalHistories = userMedicalHistoryService.getAllUserMedicalHistory();

        // Assert
        assertNotNull(retrievedMedicalHistories);
        assertTrue(retrievedMedicalHistories.isEmpty());
    }

    @Test
    void testDeleteUserMedicalHistory_ValidId() {
        // Arrange
        Integer id = 1;
        UserMedicalHistory userMedicalHistoryToDelete = new UserMedicalHistory();
        userMedicalHistoryToDelete.setId(id);

        when(userMedicalHistoryRepository.findById(id)).thenReturn(Optional.of(userMedicalHistoryToDelete));

        // Act
        UserMedicalHistory deletedUserMedicalHistory = userMedicalHistoryService.deleteUserMedicalHistory(id);

        // Assert
        assertNotNull(deletedUserMedicalHistory);
        assertEquals(id, deletedUserMedicalHistory.getId());
        verify(userMedicalHistoryRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUserMedicalHistory_InvalidId() {
        // Arrange
        Integer id = 1;

        when(userMedicalHistoryRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> userMedicalHistoryService.deleteUserMedicalHistory(id));
        verify(userMedicalHistoryRepository, never()).deleteById(id);
    }
    @Test
    void testCreateUserLesson_ValidData() {
        // Arrange

        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");
        // Arrange
        when(appUserService.findAppUserByEmail(any(String.class))).thenReturn(testAppUser);
        when(userLessonRepository.save(any(UserLesson.class))).thenReturn(userLesson);

        // Act
        UserLesson createdUserLesson = userLessonService.createUserLesson(userLessonDTO);

        // Assert
        assertNotNull(createdUserLesson);
        assertEquals(testAppUser, createdUserLesson.getAppUserId());
    }

    @Test
    void testCreateUserLesson_AppUserNotFound() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        // Mock the service to throw the exception
        when(appUserService.findAppUserByEmail("Test@gmail.com"))
                .thenThrow(new AppException(ErrorCode.APP_USER_NOT_FOUND));

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> userLessonService.createUserLesson(userLessonDTO));
        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

        verify(appUserService).findAppUserByEmail("Test@gmail.com");


    }

//    @Test
//    void testCreateUserLesson_LessonNotFound() {
//        // Arrange
////        when(appUserRepository.findById(userLessonDTO.getAppUserId())).thenReturn(Optional.of(appUser));
//        when(lessonRepository.findById(userLessonDTO.getLessonId())).thenReturn(Optional.empty());
//
//        // Act & Assert
//        AppException exception = assertThrows(AppException.class,
//                () -> userLessonService.createUserLesson(userLessonDTO));
//        assertEquals(ErrorCode.LESSON_NOT_FOUND, exception.getErrorCode());
//    }

    @Test
    void testGetUserLessonById_ExistingId() {
        // Arrange
        when(userLessonRepository.findById(1)).thenReturn(Optional.of(userLesson));

        // Act
        UserLesson retrievedUserLesson = userLessonService.getUserLessonById(1);

        // Assert
        assertNotNull(retrievedUserLesson);
        assertEquals(userLesson.getId(), retrievedUserLesson.getId());
    }

    @Test
    void testGetUserLessonById_NonExistingId() {
        // Arrange
        when(userLessonRepository.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> userLessonService.getUserLessonById(2));
        assertEquals(ErrorCode.USER_LESSON_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testGetAllUserLessons() {
        // Arrange
        List<UserLesson> userLessonList = new ArrayList<>();
        userLessonList.add(userLesson);
        when(userLessonRepository.findAll()).thenReturn(userLessonList);

        // Act
        List<UserLesson> allUserLessons = userLessonService.getAllUserLessons();

        // Assert
        assertNotNull(allUserLessons);
        assertEquals(1, allUserLessons.size());
    }



    @Test
    void testDeleteUserLesson() {
        // Arrange
        when(userLessonRepository.findById(1)).thenReturn(Optional.of(userLesson));

        // Act
        UserLesson deletedUserLesson = userLessonService.deleteUserLesson(1);

        // Assert
        assertNotNull(deletedUserLesson);
        assertEquals(userLesson.getId(), deletedUserLesson.getId());
    }
    @Test
    void testDeleteUserLesson_NonExistingId() {
        // Arrange
        when(userLessonRepository.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> userLessonService.deleteUserLesson(2));
        assertEquals(ErrorCode.USER_LESSON_NOT_FOUND, exception.getErrorCode());
    }
    @Test
    void testUpdateUserLesson_NonExistingId() {
        // Arrange
        when(userLessonRepository.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> userLessonService.updateUserLesson(2, userLessonDTO));
        assertEquals(ErrorCode.USER_LESSON_NOT_FOUND, exception.getErrorCode());
    }
}
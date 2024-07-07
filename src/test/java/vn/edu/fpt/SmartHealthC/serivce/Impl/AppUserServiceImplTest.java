package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalHistory;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AppUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AssignRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AppUserDetailResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AppUserResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.WebUserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceImplTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private WebUserService webUserService;

    @InjectMocks
    private AppUserServiceImpl appUserService;

    private AppUser testAppUser;
    private WebUser testWebUser;

    @BeforeEach
    void setUp() {
        // Khởi tạo các đối tượng test
        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");
        // Khởi tạo WebUser mock
        testWebUser = new WebUser();
        testWebUser.setId(1);
        testWebUser.setUserName("Test Doctor");
        Account account = new Account();
        account.setId(101);
        account.setEmail("test@example.com");
        testAppUser.setAccountId(account);
        UserMedicalHistory medicalHistory1 = new UserMedicalHistory();
        medicalHistory1.setId(101);
        MedicalHistory condition1 = new MedicalHistory();
        condition1.setName("Hypertension");
        condition1.setType(TypeMedicalHistory.ARTHRITIS);
        medicalHistory1.setConditionId(condition1);

        UserMedicalHistory medicalHistory2 = new UserMedicalHistory();
        medicalHistory2.setId(102);
        MedicalHistory condition2 = new MedicalHistory();
        condition2.setName("Diabetes");
        condition2.setType(TypeMedicalHistory.CARDINAL);
        medicalHistory2.setConditionId(condition2);

        testAppUser.setUserMedicalHistoryList(Arrays.asList(medicalHistory1, medicalHistory2));
    }

    @Test
    void testFindAll() {
        // Arrange
        List<AppUser> appUsers = new ArrayList<>();
        appUsers.add(testAppUser);
        when(appUserRepository.findAll()).thenReturn(appUsers);

        // Act
        List<AppUser> result = appUserService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testAppUser.getId(), result.get(0).getId());
    }

    @Test
    void testFindAll_EmptyList() {
        // Arrange
        List<AppUser> appUsers = new ArrayList<>();
        when(appUserRepository.findAll()).thenReturn(appUsers);

        // Act
        List<AppUser> result = appUserService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }



    @Test
    void testGetAppUserDetailById_AppUserNotFound() {
        // Arrange
        when(appUserRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(AppException.class, () -> appUserService.getAppUserDetailById(1));
    }



    @Test
    void testAssignPatientToDoctor() {
        // Arrange
        AssignRequestDTO assignRequestDTO = new AssignRequestDTO();
        assignRequestDTO.setAppUserId(1);
        assignRequestDTO.setWebUserId(1);

        // Mocking appUserRepository.findById()
        when(appUserRepository.findByIdActivated(anyInt())).thenReturn(Optional.of(testAppUser));

        // Mocking webUserService.getWebUserById()
        when(webUserService.getWebUserById(anyInt())).thenReturn(testWebUser);

        // Mocking testAppUser.getWebUser() to return testWebUser
        testAppUser.setWebUser(testWebUser);

        // Mocking testWebUser.getAccountId() to return a valid Account object
        Account mockAccount = mock(Account.class);
        when(mockAccount.getType()).thenReturn(TypeAccount.MEDICAL_SPECIALIST); // Adjust as per your Account implementation
        testWebUser.setAccountId(mockAccount);

        // Mocking testWebUser.getAppUserList() to return a non-null list
        testWebUser.setAppUserList(new ArrayList<>()); // Empty list or mock with desired behavior

        // Act and Assert
        assertDoesNotThrow(() -> appUserService.assignPatientToDoctor(assignRequestDTO));
    }

    @Test
    void testAssignPatientToDoctor_AppUserNotFound() {
        // Arrange
        AssignRequestDTO assignRequestDTO = new AssignRequestDTO();
        assignRequestDTO.setAppUserId(1);
        assignRequestDTO.setWebUserId(1);

        when(appUserRepository.findByIdActivated(anyInt())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(AppException.class, () -> appUserService.assignPatientToDoctor(assignRequestDTO));
    }
}
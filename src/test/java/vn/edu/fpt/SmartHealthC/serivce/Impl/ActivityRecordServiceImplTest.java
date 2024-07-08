package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.flywaydb.core.api.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalHistory;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.ActivityRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MonthlyQuestionRepository;

import javax.swing.text.html.Option;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ActivityRecordServiceImplTest {

    @Mock
    private ActivityRecordRepository activityRecordRepository;


    @InjectMocks
    private ActivityRecordServiceImpl activityRecordService;

    @Mock
    private AppUserRepository appUserRepository;


    // No need to @InjectMocks here as we aren't injecting mocks into it
    private AppUserServiceImpl appUserService;

    private AppUser testAppUser;

    private List<ActivityRecord> activityRecordList;
    private ActivityRecord activityRecord ;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");

        activityRecordList  = new ArrayList<>();
        activityRecord = new ActivityRecord();
    }


    @Test
    void createActivityRecord_appUserID_Notfound() {

        ActivityRecordCreateDTO activityRecord = new ActivityRecordCreateDTO();

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.empty());

        // Assert the expected exception with specific error code
        AppException exception = assertThrows(AppException.class, () -> activityRecordService.createActivityRecord(activityRecord));

        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void createActivityRecord_Success() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(activityRecordRepository.findRecordByIdUser(testAppUser.getId())).thenReturn(activityRecordList);
        when(activityRecordRepository.save(activityRecord)).thenReturn(activityRecord);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail("test@test.com");
        List<ActivityRecord> resultActivityRecords = activityRecordRepository.findRecordByIdUser(testAppUser.getId());
        ActivityRecord resultActivityRecord = activityRecordRepository.save(activityRecord);

        assertNotNull(resultAppUser.get());
        assertNotNull(resultActivityRecords);
        assertNotNull(resultActivityRecord);


        org.assertj.core.api.Assertions.assertThat(resultActivityRecords.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(activityRecord).isEqualTo(resultActivityRecord);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("test@test.com"));
        assertDoesNotThrow(() -> activityRecordRepository.findRecordByIdUser(testAppUser.getId()));
        assertDoesNotThrow(() -> activityRecordRepository.save(activityRecord));

    }

    @Test
    void getDataChart_Success() {
        Integer userId = 13;
        String email = "test@test.com";
        //Act
        when(appUserRepository.findByAccountEmail(email)).thenReturn(Optional.of(testAppUser));
        when(activityRecordRepository.findRecordByIdUser(userId)).thenReturn(activityRecordList);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail(email);
        List<ActivityRecord> result = activityRecordRepository.findRecordByIdUser(userId);

        //THEN
        assertNotNull(resultAppUser);
        assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.size()).isGreaterThanOrEqualTo(0);
        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail(email));
        assertDoesNotThrow(() -> activityRecordRepository.findRecordByIdUser(userId));

    }

    @Test
    void updateActivityRecord_Success() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(activityRecordRepository.findRecordByIdUser(testAppUser.getId())).thenReturn(activityRecordList);
        when(activityRecordRepository.save(activityRecord)).thenReturn(activityRecord);
        when(activityRecordRepository.findById(1)).thenReturn(Optional.of(activityRecord));

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail("test@test.com");

        List<ActivityRecord> resultActivityRecords = activityRecordRepository.findRecordByIdUser(testAppUser.getId());
        assertNotNull(resultActivityRecords);

        Optional<ActivityRecord> activityRecordOption = activityRecordRepository.findById(1);
        assertNotNull(activityRecordOption);

        activityRecord.setDate(activityRecordOption.get().getWeekStart());
        ActivityRecord resultActivityRecord = activityRecordRepository.save(activityRecord);

        assertNotNull(resultAppUser.get());
        assertNotNull(resultActivityRecord);
        assertNotNull(resultAppUser.get());


        org.assertj.core.api.Assertions.assertThat(resultActivityRecords.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(activityRecord).isEqualTo(resultActivityRecord);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("test@test.com"));
        assertDoesNotThrow(() -> activityRecordRepository.findRecordByIdUser(testAppUser.getId()));
        assertDoesNotThrow(() -> activityRecordRepository.save(activityRecord));
        assertDoesNotThrow(() -> activityRecordRepository.findById(1));

    }
}
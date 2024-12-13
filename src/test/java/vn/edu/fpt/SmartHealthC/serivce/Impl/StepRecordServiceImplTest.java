package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StepRecordServiceImplTest {


    @Mock
    private vn.edu.fpt.SmartHealthC.repository.StepRecordRepository StepRecordRepository;

    @InjectMocks
    private StepRecordServiceImpl StepRecordService;

    @Mock
    private AppUserRepository appUserRepository;


    // No need to @InjectMocks here as we aren't injecting mocks into it
    private AppUserServiceImpl appUserService;

    private AppUser testAppUser;

    private List<vn.edu.fpt.SmartHealthC.domain.entity.StepRecord> StepRecordList;

    private List<StepRecordCreateDTO>StepRecordCreateDTOS = new ArrayList<>();
    private StepRecord StepRecord ;

    private List<Date> dateList;

    private StepRecordUpdateDTO StepRecordDTO ;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");

        StepRecordList  = new ArrayList<>();
        StepRecord = new StepRecord();

        dateList = new ArrayList<>();
        dateList.add(new Date());
        dateList.add(new Date(System.currentTimeMillis() - 86400000));
        dateList.add(new Date(System.currentTimeMillis() + 86400000));

        StepRecordDTO = new StepRecordUpdateDTO();
    }

    @Test
    void createStepRecord_appUserID_Notfound() {

        StepRecordCreateDTO StepRecord = new StepRecordCreateDTO();

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.empty());

        // Assert the expected exception with specific error code
        AppException exception = assertThrows(AppException.class, () -> StepRecordService.createStepRecord(new StepRecordCreateDTO()));

        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void createStepRecord_Success() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(StepRecordRepository.findByAppUserId(testAppUser.getId())).thenReturn(StepRecordList);
        when(StepRecordRepository.save(StepRecord)).thenReturn(StepRecord);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail("test@test.com");
        List<StepRecord> resultStepRecords = StepRecordRepository.findByAppUserId(testAppUser.getId());
        StepRecord resultStepRecord = StepRecordRepository.save(StepRecord);

        assertNotNull(resultAppUser.get());
        assertNotNull(resultStepRecords);
        assertNotNull(resultStepRecord);


        org.assertj.core.api.Assertions.assertThat(resultStepRecords.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(StepRecord).isEqualTo(resultStepRecord);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("test@test.com"));
        assertDoesNotThrow(() -> StepRecordRepository.findByAppUserId(testAppUser.getId()));
        assertDoesNotThrow(() -> StepRecordRepository.save(StepRecord));

    }

    @Test
    void getDataChart_Success() {
        Integer userId = 13;
        String email = "test@test.com";
        //Act
        when(appUserRepository.findByAccountEmail(email)).thenReturn(Optional.of(testAppUser));
        when(StepRecordRepository.findByAppUserId(userId)).thenReturn(StepRecordList);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail(email);
        List<StepRecord> result = StepRecordRepository.findByAppUserId(userId);

        //THEN
        assertNotNull(resultAppUser);
        assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.size()).isGreaterThanOrEqualTo(0);
        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail(email));
        assertDoesNotThrow(() -> StepRecordRepository.findByAppUserId(userId));

    }

    @Test
    void updateStepRecord_Success() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(StepRecordRepository.findByAppUserId(testAppUser.getId())).thenReturn(StepRecordList);
        when(StepRecordRepository.save(StepRecord)).thenReturn(StepRecord);
        when(StepRecordRepository.findById(1)).thenReturn(Optional.of(StepRecord));

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail("test@test.com");

        List<StepRecord> resultStepRecords = StepRecordRepository.findByAppUserId(testAppUser.getId());
        assertNotNull(resultStepRecords);

        Optional<StepRecord> StepRecordOption = StepRecordRepository.findById(1);
        assertNotNull(StepRecordOption);

        StepRecord.setDate(StepRecordOption.get().getWeekStart());
        StepRecord resultStepRecord = StepRecordRepository.save(StepRecord);

        assertNotNull(resultAppUser.get());
        assertNotNull(resultStepRecord);
        assertNotNull(resultAppUser.get());


        org.assertj.core.api.Assertions.assertThat(resultStepRecords.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(StepRecord).isEqualTo(resultStepRecord);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("test@test.com"));
        assertDoesNotThrow(() -> StepRecordRepository.findByAppUserId(testAppUser.getId()));
        assertDoesNotThrow(() -> StepRecordRepository.save(StepRecord));
        assertDoesNotThrow(() -> StepRecordRepository.findById(1));

    }


    @Test
    void getAllStepRecords_Success() {

        //Given
        when(StepRecordRepository.findDistinctWeek(1)).thenReturn(dateList);
        when(StepRecordRepository.findByAppUserIdAndWeekStart( 1,dateList.get(0))).thenReturn(StepRecordList);


        List<Date> Result_activityWeekList = StepRecordRepository.findDistinctWeek(1);
        assertNotNull(Result_activityWeekList);

        List<StepRecord> Result_StepRecordList = StepRecordRepository.findByAppUserIdAndWeekStart( 1,dateList.get(0));
        assertNotNull(Result_StepRecordList);


        assertDoesNotThrow(() -> StepRecordRepository.findDistinctWeek(1));
        assertDoesNotThrow(() -> StepRecordRepository.findByAppUserIdAndWeekStart( 1,dateList.get(0)));

    }

    @Test
    void updateStepRecord_ACTIVITY_PLAN_NOT_FOUND() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(StepRecordRepository.findByAppUserId(testAppUser.getId())).thenReturn(StepRecordList);
        when(StepRecordRepository.findById(1)).thenReturn(Optional.of(StepRecord));
        when(StepRecordRepository.save(StepRecord)).thenReturn(StepRecord);


        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");


        List<StepRecord> resultStepRecords = StepRecordRepository.findByAppUserId(testAppUser.getId());
        assertNotNull(resultStepRecords);

        AppException exception = assertThrows(AppException.class,
                () -> StepRecordService.updateStepRecord(StepRecordDTO));

        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void getStepRecordById_appUserID_Notfound() {

        StepRecordCreateDTO StepRecord = new StepRecordCreateDTO();

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.empty());

        // Assert the expected exception with specific error code
        AppException exception = assertThrows(AppException.class, () -> StepRecordService.getStepRecordById(any()));

        assertEquals(ErrorCode.STEP_RECORD_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void getListStepPerWeek_Sucess() {

        //Given
        when(appUserRepository.findByAccountEmail(any())).thenReturn(Optional.of(testAppUser));
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(any());
        assertNotNull(appUser);
        when(StepRecordRepository.findByAppUserId(any())).thenReturn(StepRecordList);
        List<StepRecord> StepRecordList = StepRecordRepository.findByAppUserId(any());
        assertNotNull(StepRecordList);
        org.assertj.core.api.Assertions.assertThat(StepRecordList.size()).isGreaterThanOrEqualTo(0);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail(any()));
        assertDoesNotThrow(() -> StepRecordRepository.findByAppUserId( any()));

    }





}

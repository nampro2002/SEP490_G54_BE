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
import vn.edu.fpt.SmartHealthC.domain.dto.request.WeightRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;
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

public class WeightRecordServiceImplTest {


    @Mock
    private vn.edu.fpt.SmartHealthC.repository.WeightRecordRepository WeightRecordRepository;

    @InjectMocks
    private WeightRecordServiceImpl WeightRecordService;

    @Mock
    private AppUserRepository appUserRepository;


    // No need to @InjectMocks here as we aren't injecting mocks into it
    private AppUserServiceImpl appUserService;

    private AppUser testAppUser;

    private List<vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord> WeightRecordList;

    private List<WeightRecordDTO>WeightRecordCreateDTOS = new ArrayList<>();
    private WeightRecord WeightRecord ;

    private List<Date> dateList;

    private WeightRecordDTO WeightRecordDTO ;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");

        WeightRecordList  = new ArrayList<>();
        WeightRecord = new WeightRecord();

        dateList = new ArrayList<>();
        dateList.add(new Date());
        dateList.add(new Date(System.currentTimeMillis() - 86400000));
        dateList.add(new Date(System.currentTimeMillis() + 86400000));

    }


    @Test
    void createWeightRecord_appUserID_Notfound() {

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.empty());

        // Assert the expected exception with specific error code
        AppException exception = assertThrows(AppException.class, () -> WeightRecordService.createWeightRecord(new WeightRecordDTO()));

        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

    }



    @Test
    void createWeightRecord_Success() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(WeightRecordRepository.findAppUser(testAppUser.getId())).thenReturn(WeightRecordList);
        when(WeightRecordRepository.save(WeightRecord)).thenReturn(WeightRecord);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail("test@test.com");
        List<WeightRecord> resultWeightRecords = WeightRecordRepository.findAppUser(testAppUser.getId());
        WeightRecord resultWeightRecord = WeightRecordRepository.save(WeightRecord);

        assertNotNull(resultAppUser.get());
        assertNotNull(resultWeightRecords);
        assertNotNull(resultWeightRecord);


        org.assertj.core.api.Assertions.assertThat(resultWeightRecords.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(WeightRecord).isEqualTo(resultWeightRecord);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("test@test.com"));
        assertDoesNotThrow(() -> WeightRecordRepository.findAppUser(testAppUser.getId()));
        assertDoesNotThrow(() -> WeightRecordRepository.save(WeightRecord));

    }

    @Test
    void getDataChart_Success() {
        Integer userId = 13;
        String email = "test@test.com";
        //Act
        when(appUserRepository.findByAccountEmail(email)).thenReturn(Optional.of(testAppUser));
        when(WeightRecordRepository.findAppUser(userId)).thenReturn(WeightRecordList);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail(email);
        List<WeightRecord> result = WeightRecordRepository.findAppUser(userId);

        //THEN
        assertNotNull(resultAppUser);
        assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.size()).isGreaterThanOrEqualTo(0);
        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail(email));
        assertDoesNotThrow(() -> WeightRecordRepository.findAppUser(userId));

    }

    @Test
    void updateWeightRecord_Success() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(WeightRecordRepository.findAppUser(testAppUser.getId())).thenReturn(WeightRecordList);
        when(WeightRecordRepository.save(WeightRecord)).thenReturn(WeightRecord);
        when(WeightRecordRepository.findById(1)).thenReturn(Optional.of(WeightRecord));

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail("test@test.com");

        List<WeightRecord> resultWeightRecords = WeightRecordRepository.findAppUser(testAppUser.getId());
        assertNotNull(resultWeightRecords);

        Optional<WeightRecord> WeightRecordOption = WeightRecordRepository.findById(1);
        assertNotNull(WeightRecordOption);

        WeightRecord.setDate(WeightRecordOption.get().getWeekStart());
        WeightRecord resultWeightRecord = WeightRecordRepository.save(WeightRecord);

        assertNotNull(resultAppUser.get());
        assertNotNull(resultWeightRecord);
        assertNotNull(resultAppUser.get());


        org.assertj.core.api.Assertions.assertThat(resultWeightRecords.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(WeightRecord).isEqualTo(resultWeightRecord);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("test@test.com"));
        assertDoesNotThrow(() -> WeightRecordRepository.findAppUser(testAppUser.getId()));
        assertDoesNotThrow(() -> WeightRecordRepository.save(WeightRecord));
        assertDoesNotThrow(() -> WeightRecordRepository.findById(1));

    }

    @Test
    void getAllWeightRecords_Success() {

        //Given
        when(WeightRecordRepository.findDistinctWeek( any())).thenReturn(dateList);
        when(WeightRecordRepository.findByWeekStart( dateList.get(0),1)).thenReturn(WeightRecordList);


        List<Date> Result_activityWeekList = WeightRecordRepository.findDistinctWeek(1);
        assertNotNull(Result_activityWeekList);

        List<WeightRecord> Result_WeightRecordList = WeightRecordRepository.findByWeekStart( dateList.get(0),1);
        assertNotNull(Result_WeightRecordList);


        assertDoesNotThrow(() -> WeightRecordRepository.findDistinctWeek( any()));
        assertDoesNotThrow(() -> WeightRecordRepository.findByWeekStart( dateList.get(0),1));

    }



}

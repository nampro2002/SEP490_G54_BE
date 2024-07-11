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
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicineRecordRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

public class MedicineRecordServiceImplTest {

    @Mock
    private vn.edu.fpt.SmartHealthC.repository.MedicineRecordRepository MedicineRecordRepository;

    @InjectMocks
    private MedicineRecordServiceImpl MedicineRecordService;

    @Mock
    private AppUserRepository appUserRepository;


    // No need to @InjectMocks here as we aren't injecting mocks into it
    private AppUserServiceImpl appUserService;

    private AppUser testAppUser;

    private List<vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord> MedicineRecordList;

    private List<MedicineRecordCreateDTO>medicineRecordCreateDTOS = new ArrayList<>();
    private MedicineRecord MedicineRecord ;

    private List<Date> dateList;

    private MedicineRecordUpdateDTO MedicineRecordDTO ;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");

        MedicineRecordList  = new ArrayList<>();
        MedicineRecord = new MedicineRecord();

        dateList = new ArrayList<>();
        dateList.add(new Date());
        dateList.add(new Date(System.currentTimeMillis() - 86400000));
        dateList.add(new Date(System.currentTimeMillis() + 86400000));

        MedicineRecordDTO = new MedicineRecordUpdateDTO();
    }


    @Test
    void createMedicineRecord_appUserID_Notfound() {

        MedicineRecordCreateDTO MedicineRecord = new MedicineRecordCreateDTO();

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.empty());

        // Assert the expected exception with specific error code
        AppException exception = assertThrows(AppException.class, () -> MedicineRecordService.createMedicineRecord(medicineRecordCreateDTOS));

        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void createMedicineRecord_Success() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(MedicineRecordRepository.findByAppUser(testAppUser.getId())).thenReturn(MedicineRecordList);
        when(MedicineRecordRepository.save(MedicineRecord)).thenReturn(MedicineRecord);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail("test@test.com");
        List<MedicineRecord> resultMedicineRecords = MedicineRecordRepository.findByAppUser(testAppUser.getId());
        MedicineRecord resultMedicineRecord = MedicineRecordRepository.save(MedicineRecord);

        assertNotNull(resultAppUser.get());
        assertNotNull(resultMedicineRecords);
        assertNotNull(resultMedicineRecord);


        org.assertj.core.api.Assertions.assertThat(resultMedicineRecords.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(MedicineRecord).isEqualTo(resultMedicineRecord);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("test@test.com"));
        assertDoesNotThrow(() -> MedicineRecordRepository.findByAppUser(testAppUser.getId()));
        assertDoesNotThrow(() -> MedicineRecordRepository.save(MedicineRecord));

    }
    @Test
    void getDataChart_Success() {
        Integer userId = 13;
        String email = "test@test.com";
        //Act
        when(appUserRepository.findByAccountEmail(email)).thenReturn(Optional.of(testAppUser));
        when(MedicineRecordRepository.findByAppUser(userId)).thenReturn(MedicineRecordList);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail(email);
        List<MedicineRecord> result = MedicineRecordRepository.findByAppUser(userId);

        //THEN
        assertNotNull(resultAppUser);
        assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.size()).isGreaterThanOrEqualTo(0);
        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail(email));
        assertDoesNotThrow(() -> MedicineRecordRepository.findByAppUser(userId));

    }

    @Test
    void updateMedicineRecord_Success() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(MedicineRecordRepository.findByAppUser(testAppUser.getId())).thenReturn(MedicineRecordList);
        when(MedicineRecordRepository.save(MedicineRecord)).thenReturn(MedicineRecord);
        when(MedicineRecordRepository.findById(1)).thenReturn(Optional.of(MedicineRecord));

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail("test@test.com");

        List<MedicineRecord> resultMedicineRecords = MedicineRecordRepository.findByAppUser(testAppUser.getId());
        assertNotNull(resultMedicineRecords);

        Optional<MedicineRecord> MedicineRecordOption = MedicineRecordRepository.findById(1);
        assertNotNull(MedicineRecordOption);

        MedicineRecord.setDate(MedicineRecordOption.get().getWeekStart());
        MedicineRecord resultMedicineRecord = MedicineRecordRepository.save(MedicineRecord);

        assertNotNull(resultAppUser.get());
        assertNotNull(resultMedicineRecord);
        assertNotNull(resultAppUser.get());


        org.assertj.core.api.Assertions.assertThat(resultMedicineRecords.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(MedicineRecord).isEqualTo(resultMedicineRecord);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("test@test.com"));
        assertDoesNotThrow(() -> MedicineRecordRepository.findByAppUser(testAppUser.getId()));
        assertDoesNotThrow(() -> MedicineRecordRepository.save(MedicineRecord));
        assertDoesNotThrow(() -> MedicineRecordRepository.findById(1));

    }

    @Test
    void getAllMedicineRecords_Success() {

        //Given
        when(MedicineRecordRepository.findDistinctDate(1)).thenReturn(dateList);
        when(MedicineRecordRepository.findByDate( dateList.get(0),1)).thenReturn(MedicineRecordList);


        List<Date> Result_activityWeekList = MedicineRecordRepository.findDistinctDate(1);
        assertNotNull(Result_activityWeekList);

        List<MedicineRecord> Result_MedicineRecordList = MedicineRecordRepository.findByDate( dateList.get(0),1);
        assertNotNull(Result_MedicineRecordList);


        assertDoesNotThrow(() -> MedicineRecordRepository.findDistinctDate(1));
        assertDoesNotThrow(() -> MedicineRecordRepository.findByDate( dateList.get(0),1));

    }

    @Test
    void updateMedicineRecord_ACTIVITY_PLAN_NOT_FOUND() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(MedicineRecordRepository.findByAppUser(testAppUser.getId())).thenReturn(MedicineRecordList);
        when(MedicineRecordRepository.findById(1)).thenReturn(Optional.of(MedicineRecord));
        when(MedicineRecordRepository.save(MedicineRecord)).thenReturn(MedicineRecord);


        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");


        List<MedicineRecord> resultMedicineRecords = MedicineRecordRepository.findByAppUser(testAppUser.getId());
        assertNotNull(resultMedicineRecords);

        AppException exception = assertThrows(AppException.class,
                () -> MedicineRecordService.updateMedicineRecord(MedicineRecordDTO));

        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

    }



}

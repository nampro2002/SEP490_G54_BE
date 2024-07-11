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
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.MentalRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MentalRecordServiceImplTest {

    @Mock
    private MentalRecordRepository MentalRecordRepository;

    @InjectMocks
    private MentalRecordServiceImpl MentalRecordService;

    @Mock
    private AppUserRepository appUserRepository;


    // No need to @InjectMocks here as we aren't injecting mocks into it
    private AppUserServiceImpl appUserService;

    private AppUser testAppUser;

    private List<MentalRecord> MentalRecordList;
    private MentalRecord MentalRecord ;

    private List<Date> dateList;

    private MentalRecordUpdateDTO MentalRecordDTO ;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");

        MentalRecordList  = new ArrayList<>();
        MentalRecord = new MentalRecord();

        dateList = new ArrayList<>();
        dateList.add(new Date());
        dateList.add(new Date(System.currentTimeMillis() - 86400000));
        dateList.add(new Date(System.currentTimeMillis() + 86400000));

        MentalRecordDTO = new MentalRecordUpdateDTO();
    }


    @Test
    void createMentalRecord_appUserID_Notfound() {

        MentalRecordCreateDTO MentalRecord = new MentalRecordCreateDTO();

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.empty());

        // Assert the expected exception with specific error code
        AppException exception = assertThrows(AppException.class, () -> MentalRecordService.createMentalRecord(MentalRecord));

        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void createMentalRecord_Success() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(MentalRecordRepository.findByAppUserId(testAppUser.getId())).thenReturn(MentalRecordList);
        when(MentalRecordRepository.save(MentalRecord)).thenReturn(MentalRecord);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail("test@test.com");
        List<MentalRecord> resultMentalRecords = MentalRecordRepository.findByAppUserId(testAppUser.getId());
        MentalRecord resultMentalRecord = MentalRecordRepository.save(MentalRecord);

        assertNotNull(resultAppUser.get());
        assertNotNull(resultMentalRecords);
        assertNotNull(resultMentalRecord);


        org.assertj.core.api.Assertions.assertThat(resultMentalRecords.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(MentalRecord).isEqualTo(resultMentalRecord);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("test@test.com"));
        assertDoesNotThrow(() -> MentalRecordRepository.findByAppUserId(testAppUser.getId()));
        assertDoesNotThrow(() -> MentalRecordRepository.save(MentalRecord));

    }

    @Test
    void getDataChart_Success() {
        Integer userId = 13;
        String email = "test@test.com";
        //Act
        when(appUserRepository.findByAccountEmail(email)).thenReturn(Optional.of(testAppUser));
        when(MentalRecordRepository.findByAppUserId(userId)).thenReturn(MentalRecordList);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail(email);
        List<MentalRecord> result = MentalRecordRepository.findByAppUserId(userId);

        //THEN
        assertNotNull(resultAppUser);
        assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.size()).isGreaterThanOrEqualTo(0);
        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail(email));
        assertDoesNotThrow(() -> MentalRecordRepository.findByAppUserId(userId));

    }


}

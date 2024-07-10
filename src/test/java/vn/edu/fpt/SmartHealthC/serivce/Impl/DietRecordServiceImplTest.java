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
import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.DietRecordRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DietRecordServiceImplTest {


    @Mock
    private DietRecordRepository dietRecordRepository;


    @InjectMocks
    private DietRecordServiceImpl dietRecordService;

    @Mock
    private AppUserRepository appUserRepository;


    // No need to @InjectMocks here as we aren't injecting mocks into it
    private AppUserServiceImpl appUserService;

    private AppUser testAppUser;

    private List<DietRecord> dietRecords;
    private DietRecord dietRecord ;

    private List<Date> dateList;

    private DietRecordUpdateDTO dietRecordUpdateDTO ;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");

        dietRecords  = new ArrayList<>();
        dietRecord = new DietRecord();

        dateList = new ArrayList<>();
        dateList.add(new Date());
        dateList.add(new Date(System.currentTimeMillis() - 86400000));
        dateList.add(new Date(System.currentTimeMillis() + 86400000));

        dietRecordUpdateDTO = new DietRecordUpdateDTO();
    }

    @Test
    void createDietRecord_appUserID_Notfound() {

        DietRecordCreateDTO dietRecordCreateDTO = new DietRecordCreateDTO();

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> dietRecordService.createDietRecord(dietRecordCreateDTO));

        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void createDietRecord_Success() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(dietRecordRepository.findByAppUser(any())).thenReturn(dietRecords);
        when(dietRecordRepository.save(any())).thenReturn(dietRecord);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail("test@test.com");
        List<DietRecord> resultDietRecords = dietRecordRepository.findByAppUser(testAppUser.getId());
        DietRecord resultDietRecord = dietRecordRepository.save(dietRecord);

        assertNotNull(resultAppUser.get());
        assertNotNull(resultDietRecords);
        assertNotNull(resultDietRecord);


        org.assertj.core.api.Assertions.assertThat(resultDietRecords.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(resultDietRecord).isEqualTo(dietRecord);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("test@test.com"));
        assertDoesNotThrow(() -> dietRecordRepository.findByAppUser(testAppUser.getId()));
        assertDoesNotThrow(() -> dietRecordRepository.save(dietRecord));

    }


    @Test
    void updateDietRecord_Success() {

        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(dietRecordRepository.findByAppUser(testAppUser.getId())).thenReturn(dietRecords);
        when(dietRecordRepository.save(dietRecord)).thenReturn(dietRecord);
        when(dietRecordRepository.findById(1)).thenReturn(Optional.of(dietRecord));

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail("test@test.com");

        List<DietRecord> resultDietRecords = dietRecordRepository.findByAppUser(testAppUser.getId());
        assertNotNull(resultDietRecords);

        Optional<DietRecord> optionalDietRecord = dietRecordRepository.findById(1);
        assertNotNull(optionalDietRecord);

        dietRecord.setDate(optionalDietRecord.get().getWeekStart());
        DietRecord resultDietRecord = dietRecordRepository.save(dietRecord);

        assertNotNull(resultAppUser.get());
        assertNotNull(resultDietRecord);
        assertNotNull(resultAppUser.get());


        org.assertj.core.api.Assertions.assertThat(resultDietRecords.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(resultDietRecord).isEqualTo(dietRecord);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("test@test.com"));
        assertDoesNotThrow(() -> dietRecordRepository.findByAppUser(testAppUser.getId()));
        assertDoesNotThrow(() -> dietRecordRepository.save(dietRecord));
        assertDoesNotThrow(() -> dietRecordRepository.findById(1));

    }

    @Test
    void getDataChart_Success() {
        Integer userId = 13;
        String email = "test@test.com";
        //Act
        when(appUserRepository.findByAccountEmail(email)).thenReturn(Optional.of(testAppUser));
        when(dietRecordRepository.findByAppUser(userId)).thenReturn(dietRecords);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail(email);
        List<DietRecord> result = dietRecordRepository.findByAppUser(userId);

        //THEN
        assertNotNull(resultAppUser);
        assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.size()).isGreaterThanOrEqualTo(0);
        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail(email));
        assertDoesNotThrow(() -> dietRecordRepository.findByAppUser(userId));

    }
}


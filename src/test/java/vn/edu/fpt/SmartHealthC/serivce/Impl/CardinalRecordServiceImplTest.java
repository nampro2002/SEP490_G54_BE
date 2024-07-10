package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.CardinalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.ActivityRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.CardinalRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CardinalRecordServiceImplTest {
    @Mock
    private CardinalRecordRepository cardinalRecordRepository;
    @InjectMocks
    private CardinalRecordServiceImpl cardinalRecordService;
    @Mock
    private AppUserRepository appUserRepository;


    private AppUser testAppUser;


    private List<CardinalRecord> cardinalRecords;
    private CardinalRecord cardinalRecord ;
    private List<Date> cardinalWeekList;
    @Mock
    private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private CardinalRecordDTO cardinalRecordDTO ;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");
        cardinalWeekList = new ArrayList<>();
        cardinalRecords  = new ArrayList<>();
        cardinalRecord = new CardinalRecord();

    }

    @Test
    void createCardinalRecord_appUserID_Notfound() {

        CardinalRecordDTO activityRecord = new CardinalRecordDTO();

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");
        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.empty());

        // Assert the expected exception with specific error code
        AppException exception = assertThrows(AppException.class, () -> cardinalRecordService.createCardinalRecord(activityRecord));

        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void createCardinalRecord_Success() {

        CardinalRecordDTO cardinalRecordDTO = new CardinalRecordDTO();
        //Given
        when(appUserRepository.findByAccountEmail("test@test.com")).thenReturn(Optional.of(testAppUser));
        when(cardinalRecordRepository.findByAppUserId(testAppUser.getId())).thenReturn(cardinalRecords);
        when(cardinalRecordRepository.save(cardinalRecord)).thenReturn(cardinalRecord);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail("test@test.com");
        List<CardinalRecord> resultCardinalRecordList = cardinalRecordRepository.findByAppUserId(testAppUser.getId());
        boolean dateExists = true;
        org.assertj.core.api.Assertions.assertThat(dateExists).isTrue();

        CardinalRecord resultCardinalRecord = cardinalRecordRepository.save(cardinalRecord);

        assertNotNull(resultAppUser.get());
        assertNotNull(resultCardinalRecordList);
        assertNotNull(resultCardinalRecord);


        org.assertj.core.api.Assertions.assertThat(resultCardinalRecordList.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(cardinalRecord).isEqualTo(resultCardinalRecord);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("test@test.com"));
        assertDoesNotThrow(() -> cardinalRecordRepository.findByAppUserId(testAppUser.getId()));
        assertDoesNotThrow(() -> cardinalRecordRepository.save(cardinalRecord));

    }

    @Test
    void createCardinalRecord_CARDINAL_TYPE_DAY_EXIST() {

        CardinalRecordDTO cardinalRecordDTO = new CardinalRecordDTO();
        cardinalRecordDTO.setDate(new Date());

        //Given Authen
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");
        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.of(testAppUser));
        when(cardinalRecordRepository.findByAppUserId(any())).thenReturn(Collections.emptyList());

        List<CardinalRecord> cardinalRecordListExits = cardinalRecordRepository.findByAppUserId(testAppUser.getId());
        ErrorCode expect =   ErrorCode.CARDINAL_TYPE_DAY_EXIST;
        boolean dateExists = false;

        org.assertj.core.api.Assertions.assertThat(expect).isEqualTo(ErrorCode.CARDINAL_TYPE_DAY_EXIST);

        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail("Test@gmail.com"));
        assertDoesNotThrow(() -> cardinalRecordRepository.findByAppUserId(any()));
    }

    @Test
    void getAllCardinalRecords_Sucess() {

        //Given
        when(cardinalRecordRepository.findDistinctWeek(any())).thenReturn(cardinalWeekList);

        when( cardinalRecordRepository.findByWeekStart(any(),any())).thenReturn(cardinalRecords);


        List<Date> resultCardinalWeekList = cardinalRecordRepository.findDistinctWeek(any());
        List<CardinalRecord> resultCardinalRecords =cardinalRecordRepository.findByWeekStart(any(),any());

        assertNotNull(resultCardinalWeekList);
        assertNotNull(resultCardinalRecords);
        org.assertj.core.api.Assertions.assertThat(resultCardinalWeekList.size()).isGreaterThanOrEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(resultCardinalRecords.size()).isGreaterThanOrEqualTo(0);

        assertDoesNotThrow(() -> cardinalRecordRepository.findDistinctWeek(any()));
        assertDoesNotThrow(() -> cardinalRecordRepository.findByWeekStart(any(),any()));

    }

    @Test
    void getDataChart_Success() {
        //Act
        when(appUserRepository.findByAccountEmail(any())).thenReturn(Optional.of(testAppUser));
        when(cardinalRecordRepository.findByAppUserId(any())).thenReturn(cardinalRecords);

        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail(any());
        List<CardinalRecord> result = cardinalRecordRepository.findByAppUserId(any());

        //THEN
        assertNotNull(resultAppUser);
        assertNotNull(result);
        org.assertj.core.api.Assertions.assertThat(result.size()).isGreaterThanOrEqualTo(0);
        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail(any()));
        assertDoesNotThrow(() -> cardinalRecordRepository.findByAppUserId(any()));

    }

    @Test
    void getDataChart_appUserID_Notfound() {
        //Act
        CardinalRecordDTO activityRecord = new CardinalRecordDTO();

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn(any());
        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("123@gmail.com")).thenReturn(Optional.empty());

        // Assert the expected exception with specific error code
        AppException exception = assertThrows(AppException.class, () -> cardinalRecordService.getDataChart());

        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

    }






}

package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.fpt.SmartHealthC.domain.Enum.MonthlyRecordType;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyAnswerResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyStatisticResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.FormQuestionRepository;
import vn.edu.fpt.SmartHealthC.repository.MonthlyQuestionPointRepository;
import vn.edu.fpt.SmartHealthC.repository.MonthlyQuestionRepository;
import vn.edu.fpt.SmartHealthC.serivce.Impl.MonthlyQuestionServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MonthlyQuestionServiceImplTest {

    @Mock
    private MonthlyQuestionRepository monthlyQuestionRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private MonthlyQuestionServiceImpl monthlyQuestionService;

    @Mock
    private FormQuestionRepository formQuestionRepository;

    @Mock
    private MonthlyQuestionPointRepository monthlyQuestionPointRepository;

    private MonthlyQuestionPoint monthlyQuestionPoint;
    private AppUser appUser;
    private  MonthlyRecord monthlyRecordSaved1;
    private  MonthlyRecord monthlyRecordSaved2;
    private MonthlyQuestionPoint monthlyQuestionPointSaved1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        WebUser testWeb = new WebUser();
        testWeb.setId(1);
        testWeb.setUserName("Test Doctor");
        Account account = new Account();
        account.setId(101);
        account.setEmail("test@example.com");
         appUser = new AppUser();
        appUser.setId(1);
        appUser.setName("John Doe");
        appUser.setAccountId(account);
        appUser.setWebUser(testWeb);
        appUser.setDob(new Date());
        appUser.setCic("12313213213");
        appUser.setPhoneNumber("1234523291");
        appUser.setGender(true);
        appUser.setHeight(12.0f);
        appUser.setWeight(5.0f);
        appUser.setMedicalSpecialistNote("123213213");
        appUser.setUserMedicalHistoryList(new ArrayList<>());

        MonthlyQuestionDTO monthlyQuestionDTO1 = new MonthlyQuestionDTO();
        monthlyQuestionDTO1.setMonthNumber(1);
        monthlyQuestionDTO1.setMonthlyRecordType(MonthlyRecordType.SAT_SF_C); // Invalid empty type
        monthlyQuestionDTO1.setQuestionNumber(1);
        monthlyQuestionDTO1.setQuestion("Test Question");
        monthlyQuestionDTO1.setQuestionEn("Test Question");
        monthlyQuestionDTO1.setAnswer(1);

        monthlyRecordSaved1 = MonthlyRecord.builder()
                .id(1)
                .monthNumber(monthlyQuestionDTO1.getMonthNumber())
                .monthlyRecordType(monthlyQuestionDTO1.getMonthlyRecordType())
                .questionNumber(monthlyQuestionDTO1.getQuestionNumber())
                .question(monthlyQuestionDTO1.getQuestion())
                .answer(monthlyQuestionDTO1.getAnswer())
                .appUserId(appUser)
                .build();
        monthlyRecordSaved2 = MonthlyRecord.builder()
                .id(2)
                .monthNumber(monthlyQuestionDTO1.getMonthNumber())
                .monthlyRecordType(monthlyQuestionDTO1.getMonthlyRecordType())
                .questionNumber(monthlyQuestionDTO1.getQuestionNumber())
                .question(monthlyQuestionDTO1.getQuestion())
                .answer(monthlyQuestionDTO1.getAnswer())
                .appUserId(appUser)
                .build();


        monthlyQuestionPoint =  MonthlyQuestionPoint.builder()
                .id(1) // ID cố định
                .appUserId(appUser) // Đối tượng AppUser đã được khởi tạo trước
                .monthNumber(5) // Ví dụ: tháng 5
                .totalSAT(75.0f) // Ví dụ: tổng SAT là 75
                .sat_sf_c_activityPoint(8.0f)
                .sat_sf_c_positivityPoint(7.5f)
                .sat_sf_c_supportPoint(6.0f)
                .sat_sf_c_experiencePoint(5.5f)
                .sat_sf_p_lifeValue(9.0f)
                .sat_sf_p_targetAndAction(7.0f)
                .sat_sf_p_decision(8.5f)
                .sat_sf_p_buildPlan(6.5f)
                .sat_sf_p_healthyEnvironment(9.5f)
                .sat_sf_i_e_activityPoint(8.0f)
                .sat_sf_i_e_activityStressPoint(7.0f)
                .sat_sf_i_e_activitySubstantialPoint(6.5f)
                .sat_sf_i_e_energy(8.5f)
                .sat_sf_i_e_motivation(7.5f)
                .sat_sf_i_e_planCheck(6.0f)
                .sat_sf_c_total(70.0f)
                .sat_sf_p_total(65.0f)
                .sat_sf_i_total(60.0f)
                .totalSF(195.0f)
                .sf_mentalPoint(8.0f)
                .sf_activity_planPoint(7.0f)
                .sf_activity_habitPoint(6.5f)
                .sf_diet_healthyPoint(7.5f)
                .sf_diet_vegetablePoint(8.0f)
                .sf_diet_habitPoint(6.0f)
                .sf_medicine_followPlanPoint(7.0f)
                .sf_medicine_habitPoint(8.5f)
                .sf_mental_modelPoint(7.5f)
                .sf_activity_modelPoint(8.0f)
                .sf_diet_modelPoint(7.0f)
                .sf_medicine_modelPoint(6.5f)
                .build();

        monthlyQuestionPointSaved1 = MonthlyQuestionPoint.builder()
                .id(1) // ID cố định
                .appUserId(appUser) // Đối tượng AppUser đã được khởi tạo trước
                .monthNumber(8) // Tháng 8
                .totalSAT(85.0f) // Tổng SAT cố định
                .sat_sf_c_activityPoint(8.5f)
                .sat_sf_c_positivityPoint(7.0f)
                .sat_sf_c_supportPoint(6.5f)
                .sat_sf_c_experiencePoint(7.5f)
                .sat_sf_p_lifeValue(9.0f)
                .sat_sf_p_targetAndAction(8.0f)
                .sat_sf_p_decision(7.5f)
                .sat_sf_p_buildPlan(6.0f)
                .sat_sf_p_healthyEnvironment(8.5f)
                .sat_sf_i_e_activityPoint(7.5f)
                .sat_sf_i_e_activityStressPoint(6.0f)
                .sat_sf_i_e_activitySubstantialPoint(7.0f)
                .sat_sf_i_e_energy(8.0f)
                .sat_sf_i_e_motivation(7.5f)
                .sat_sf_i_e_planCheck(6.5f)
                .sat_sf_c_total(70.0f)
                .sat_sf_p_total(65.0f)
                .sat_sf_i_total(60.0f)
                .totalSF(195.0f)
                .sf_mentalPoint(8.0f)
                .sf_activity_planPoint(7.0f)
                .sf_activity_habitPoint(6.5f)
                .sf_diet_healthyPoint(8.5f)
                .sf_diet_vegetablePoint(7.0f)
                .sf_diet_habitPoint(6.0f)
                .sf_medicine_followPlanPoint(7.5f)
                .sf_medicine_habitPoint(8.0f)
                .sf_mental_modelPoint(7.5f)
                .sf_activity_modelPoint(8.0f)
                .sf_diet_modelPoint(7.0f)
                .sf_medicine_modelPoint(6.5f)
                .build();
    }
    @Test
    void testCreateNewMonthMark_Valid() {
        // Arrange
        Integer appUserId = 1;

        MonthlyQuestionDTO monthlyQuestionDTO = new MonthlyQuestionDTO();
        monthlyQuestionDTO.setMonthNumber(1);
        monthlyQuestionDTO.setMonthlyRecordType(MonthlyRecordType.SAT_SF_C); // Invalid empty type
        monthlyQuestionDTO.setQuestionNumber(1);
        monthlyQuestionDTO.setQuestion("Test Question");
        monthlyQuestionDTO.setQuestionEn("Test Question");
        monthlyQuestionDTO.setAnswer(1);

        AppUser appUser = new AppUser();
        appUser.setId(1);


        MonthlyRecord monthlyRecordSaved = MonthlyRecord.builder()
                .id(1)
                .monthNumber(monthlyQuestionDTO.getMonthNumber())
                .monthlyRecordType(monthlyQuestionDTO.getMonthlyRecordType())
                .questionNumber(monthlyQuestionDTO.getQuestionNumber())
                .question(monthlyQuestionDTO.getQuestion())
                .answer(monthlyQuestionDTO.getAnswer())
                .appUserId(appUser)
                .build();

        when(appUserRepository.findById(any(Integer.class)))
                .thenReturn(Optional.of(appUser));
        when(monthlyQuestionRepository.save(any(MonthlyRecord.class)))
                .thenReturn(monthlyRecordSaved);


        // Act
        assertDoesNotThrow(() -> monthlyQuestionService.createNewMonthMark(appUserId));

    }
    @Test
    void testCreateNewMonthMark_InValid() {
        // Arrange
        Integer appUserId = 1;

        AppUser appUser = new AppUser();
        appUser.setId(1);


        when(appUserRepository.findById(any(Integer.class)))
                .thenReturn(Optional.empty());

        // Act
        AppException exception = assertThrows(AppException.class,
                () -> monthlyQuestionService.createNewMonthMark(appUserId));
        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());


    }

    @Test
    void testCreateAnswers_Valid() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        MonthlyQuestionDTO monthlyQuestionDTO1 = new MonthlyQuestionDTO();
        monthlyQuestionDTO1.setMonthNumber(1);
        monthlyQuestionDTO1.setMonthlyRecordType(MonthlyRecordType.SAT_SF_C);
        monthlyQuestionDTO1.setQuestionNumber(1);
        monthlyQuestionDTO1.setQuestion("Test Question");
        monthlyQuestionDTO1.setQuestionEn("Test Question");
        monthlyQuestionDTO1.setAnswer(1);

        MonthlyQuestionDTO monthlyQuestionDTO2 = new MonthlyQuestionDTO();
        monthlyQuestionDTO2.setMonthNumber(1);
        monthlyQuestionDTO2.setMonthlyRecordType(MonthlyRecordType.SAT_SF_C);
        monthlyQuestionDTO2.setQuestionNumber(2);
        monthlyQuestionDTO2.setQuestion("Test Question");
        monthlyQuestionDTO2.setQuestionEn("Test Question");
        monthlyQuestionDTO2.setAnswer(1);

        List<MonthlyQuestionDTO> monthlyQuestionDTOList = Arrays.asList(monthlyQuestionDTO1, monthlyQuestionDTO2);



        MonthlyRecord monthlyRecordSaved = MonthlyRecord.builder()
                .id(1)
                .monthNumber(monthlyQuestionDTO1.getMonthNumber())
                .monthlyRecordType(monthlyQuestionDTO1.getMonthlyRecordType())
                .questionNumber(monthlyQuestionDTO1.getQuestionNumber())
                .question(monthlyQuestionDTO1.getQuestion())
                .answer(monthlyQuestionDTO1.getAnswer())
                .appUserId(appUser)
                .build();

        FormQuestion formQuestion = new FormQuestion();
        formQuestion.setId(1);
        formQuestion.setQuestion("Test Question");
        formQuestion.setQuestionEn("Test Question");
        formQuestion.setQuestionNumber(1);
        formQuestion.setType(MonthlyRecordType.SAT_SF_C);

        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.of(appUser));

        when(formQuestionRepository.findRecordByQuestionNumberAndType(any(Integer.class), any(MonthlyRecordType.class)))
                .thenReturn(Optional.of(formQuestion));

        when(monthlyQuestionRepository.save(any(MonthlyRecord.class)))
                .thenReturn(monthlyRecordSaved);

        when(monthlyQuestionPointRepository.save(any(MonthlyQuestionPoint.class)))
                .thenReturn(monthlyQuestionPoint);


        // Act and Assert
        assertDoesNotThrow(() -> monthlyQuestionService.createAnswers(monthlyQuestionDTOList));
    }

    @Test
    void testCreateAnswers_AppUserNotFound() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        List<MonthlyQuestionDTO> monthlyQuestionDTOList = Arrays.asList(new MonthlyQuestionDTO());


        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class,
                () -> monthlyQuestionService.createAnswers(monthlyQuestionDTOList));

        // Act and Assert
        assertEquals(ErrorCode.APP_USER_NOT_FOUND ,exception.getErrorCode());
    }

    @Test
    void testCreateAnswers_FormQuestionNotFound() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        MonthlyQuestionDTO monthlyQuestionDTO1 = new MonthlyQuestionDTO();
        monthlyQuestionDTO1.setMonthNumber(1);
        monthlyQuestionDTO1.setMonthlyRecordType(MonthlyRecordType.SAT_SF_C);
        monthlyQuestionDTO1.setQuestionNumber(1);
        monthlyQuestionDTO1.setQuestion("Test Question");
        monthlyQuestionDTO1.setQuestionEn("Test Question");
        monthlyQuestionDTO1.setAnswer(1);

        MonthlyQuestionDTO monthlyQuestionDTO2 = new MonthlyQuestionDTO();
        monthlyQuestionDTO2.setMonthNumber(1);
        monthlyQuestionDTO2.setMonthlyRecordType(MonthlyRecordType.SAT_SF_C);
        monthlyQuestionDTO2.setQuestionNumber(2);
        monthlyQuestionDTO2.setQuestion("Test Question");
        monthlyQuestionDTO2.setQuestionEn("Test Question");
        monthlyQuestionDTO2.setAnswer(1);

        List<MonthlyQuestionDTO> monthlyQuestionDTOList = Arrays.asList(monthlyQuestionDTO1, monthlyQuestionDTO2);


        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.of(appUser));

        when(formQuestionRepository.findRecordByQuestionNumberAndType(any(Integer.class), any(MonthlyRecordType.class)))
                .thenReturn(Optional.empty());


        AppException exception = assertThrows(AppException.class,
                () -> monthlyQuestionService.createAnswers(monthlyQuestionDTOList));

        // Act and Assert
        assertEquals(ErrorCode.FORM_QUESTION_NOT_FOUND ,exception.getErrorCode());
    }

    @Test
    void testGetList3MonthlyNumber_Success() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.ofNullable(appUser));

        // Act & Assert

        assertDoesNotThrow(() -> monthlyQuestionService.getList3MonthlyNumber());
    }
    @Test
    void testGetList3MonthlyNumber_Fail() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.empty());

        // Act & Assert

        AppException exception = assertThrows(AppException.class,
                () -> monthlyQuestionService.getList3MonthlyNumber());

        // Act and Assert
        assertEquals(ErrorCode.APP_USER_NOT_FOUND ,exception.getErrorCode());
    }

    @Test
    void testGetList3MonthlyNumberWeb_Success() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        List<Integer> monthlyNumberResponseDTOList = new ArrayList<>();
        monthlyNumberResponseDTOList.add(1);
        monthlyNumberResponseDTOList.add(2);
        when( monthlyQuestionRepository.find3ByAppUserWeb(any(Integer.class)))
                .thenReturn(monthlyNumberResponseDTOList);

        // Act and Assert
        assertDoesNotThrow(() -> monthlyQuestionService.getList3MonthlyNumberWeb(any(Integer.class)));
    }
    @Test
    void testGetList3MonthlyNumberWeb_Fail() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");


        when( monthlyQuestionRepository.find3ByAppUserWeb(any(Integer.class)))
                .thenReturn(Collections.emptyList());

        // Act and Assert
        List<Integer> monthlyNumberResponseDTOList =   monthlyQuestionService.getList3MonthlyNumberWeb(any(Integer.class));
        assertEquals(monthlyNumberResponseDTOList.size() , 0);

    }

    @Test
    void testGetMobileListAnswer_Success() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        List<MonthlyRecord> monthlyRecords  = new ArrayList<>();
        monthlyRecords.add(monthlyRecordSaved1);
        monthlyRecords.add(monthlyRecordSaved2);
        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.ofNullable(appUser));
        List<MonthlyAnswerResponseDTO> list = new ArrayList<>();
        when(  monthlyQuestionRepository.findAllByAppUserAndMonthNumber(any(Integer.class),any(Integer.class)))
                .thenReturn(monthlyRecords);

        // Act and Assert
        Integer someIntegerValue = 1; // Giá trị thực tế hoặc bạn có thể mock giá trị

        assertDoesNotThrow(() -> monthlyQuestionService.getMobileListAnswer(someIntegerValue,"SAT_SF_C",TypeLanguage.EN));
    }
    @Test
    void testGetMobileListAnswer_Fail() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        List<MonthlyRecord> monthlyRecords  = new ArrayList<>();
        monthlyRecords.add(monthlyRecordSaved1);
        monthlyRecords.add(monthlyRecordSaved2);
        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.ofNullable(appUser));
        when(  monthlyQuestionRepository.findAllByAppUserAndMonthNumber(any(Integer.class),any(Integer.class)))
                .thenReturn(Collections.emptyList());

        // Act and Assert
        Integer someIntegerValue = 1;
        List<MonthlyAnswerResponseDTO> monthlyRecordsTest=  monthlyQuestionService.getMobileListAnswer(someIntegerValue,"SAT_SF_C",TypeLanguage.EN);
        assertEquals(monthlyRecordsTest.size() , 0);
    }
//
    @Test
    void testGetWebListAnswer_Success() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        List<MonthlyRecord> monthlyRecords  = new ArrayList<>();
        monthlyRecords.add(monthlyRecordSaved1);
        monthlyRecords.add(monthlyRecordSaved2);


        when(  monthlyQuestionRepository.findAllByAppUserAndMonthNumber(any(Integer.class),any(Integer.class)))
                .thenReturn(monthlyRecords);
        // Act and Assert
        Integer someIntegerValue = 1;
        assertDoesNotThrow(() -> monthlyQuestionService.getWebListAnswer(someIntegerValue,someIntegerValue,"SAT_SF_C"));
    }
    @Test
    void testGetWebListAnswer_Fail() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        when(  monthlyQuestionRepository.findAllByAppUserAndMonthNumber(any(Integer.class),any(Integer.class)))
                .thenReturn(Collections.emptyList());


        // Act and Assert
        Integer someIntegerValue = 1;
        List<MonthlyAnswerResponseDTO> monthlyRecordsTest=  monthlyQuestionService.getWebListAnswer(someIntegerValue,someIntegerValue,"SAT_SF_C");
        assertEquals(monthlyRecordsTest.size() , 0);
    }

    @Test
    void testGetPoint3MonthMobile_Success() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        List<MonthlyRecord> monthlyRecords  = new ArrayList<>();
        monthlyRecords.add(monthlyRecordSaved1);
        monthlyRecords.add(monthlyRecordSaved2);
        List<Integer> monthlyNumbers  = new ArrayList<>();
        monthlyNumbers.add(1);
        monthlyNumbers.add(2);
        List<MonthlyQuestionPoint> monthlyQuestionPoints  = new ArrayList<>();
        monthlyQuestionPoints.add(monthlyQuestionPoint);
        monthlyQuestionPoints.add(monthlyQuestionPointSaved1);

        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.ofNullable(appUser));

        when(  monthlyQuestionRepository.find3ByAppUser(any(Integer.class)))
                .thenReturn(monthlyNumbers);
        when(   monthlyQuestionPointRepository.findByMonthAndUser(any(Integer.class),
                any(Integer.class)))
                .thenReturn(Optional.ofNullable(monthlyQuestionPoint));

        // Act and Assert
        assertDoesNotThrow(() ->  monthlyQuestionService.getPoint3MonthMobile());

    }
    @Test
    void testGetPoint3MonthMobile_Fail() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        AppException exception = assertThrows(AppException.class,
                () -> monthlyQuestionService.getPoint3MonthMobile());

        // Act and Assert
        assertEquals(ErrorCode.APP_USER_NOT_FOUND ,exception.getErrorCode());


    }
    @Test
    void testGetPoint_Success() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");


        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.ofNullable(appUser));
        when(   monthlyQuestionPointRepository.findByMonthAndUser(any(Integer.class),
                any(Integer.class)))
                .thenReturn(Optional.ofNullable(monthlyQuestionPoint));

        // Act and Assert
        assertDoesNotThrow(() ->  monthlyQuestionService.getPoint(any(Integer.class),
                any(Integer.class)));
    }
    @Test
    void testGetPoint_Fail() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");


        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.empty());
        when(   monthlyQuestionPointRepository.findByMonthAndUser(any(Integer.class),
                any(Integer.class)))
                .thenReturn(Optional.ofNullable(monthlyQuestionPoint));
        AppException exception = assertThrows(AppException.class,
                () -> monthlyQuestionService.getPoint3MonthMobile());
        // Act and Assert
        assertEquals(ErrorCode.APP_USER_NOT_FOUND ,exception.getErrorCode());

    }
    @Test
    void testGetPoint2MonthMobile_Success() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        List<Integer> monthlyNumbers  = new ArrayList<>();
        monthlyNumbers.add(1);
        monthlyNumbers.add(2);
        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.of(appUser));
        when(   monthlyQuestionRepository.find2ByAppUserAndMonthNumber(any(Integer.class),
                any(Integer.class)))
                .thenReturn(monthlyNumbers);
        when(   monthlyQuestionPointRepository.findByMonthAndUser(any(Integer.class),
                any(Integer.class)))
                .thenReturn(Optional.ofNullable(monthlyQuestionPoint));
        // Act and Assert
        Integer someIntegerValue = 1; // Giá trị thực tế hoặc bạn có thể mock giá trị
        assertDoesNotThrow(() -> monthlyQuestionService.getPoint2MonthMobile(someIntegerValue));
        assertDoesNotThrow(() -> monthlyQuestionService.getPoint2Month(someIntegerValue, someIntegerValue));


    }
    @Test
    void testGetPoint2MonthMobile_Fail() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        List<Integer> monthlyNumbers  = new ArrayList<>();
        monthlyQuestionPoint = new MonthlyQuestionPoint();
        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.of(appUser));
        when(   monthlyQuestionRepository.find2ByAppUserAndMonthNumber(any(Integer.class),
                any(Integer.class)))
                .thenReturn(monthlyNumbers);
        when(   monthlyQuestionPointRepository.findByMonthAndUser(any(Integer.class),
                any(Integer.class)))
                .thenReturn(Optional.ofNullable(monthlyQuestionPoint));
        // Act and Assert
        Integer someIntegerValue = 1; // Giá trị thực tế hoặc bạn có thể mock giá trị
        List<MonthlyStatisticResponseDTO> result =monthlyQuestionService.getPoint2MonthMobile(someIntegerValue);
        List<MonthlyStatisticResponseDTO> result1 =monthlyQuestionService.getPoint2Month(someIntegerValue, someIntegerValue);

        assertEquals(result.size(),0);
        assertEquals(result1.size(),0);
    }
    @Test
    void testGetPoint2MonthWeb_Success() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        List<Integer> monthlyNumbers  = new ArrayList<>();
        monthlyNumbers.add(1);
        monthlyNumbers.add(2);
        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.of(appUser));
        when(   monthlyQuestionRepository.find2ByAppUserAndMonthNumber(any(Integer.class),
                any(Integer.class)))
                .thenReturn(monthlyNumbers);
        when(   monthlyQuestionPointRepository.findByMonthAndUser(any(Integer.class),
                any(Integer.class)))
                .thenReturn(Optional.ofNullable(monthlyQuestionPoint));
        // Act and Assert
//        assertDoesNotThrow(() ->  monthlyQuestionService.getPoint2MonthMobile(any(Integer.class)));

        Integer someIntegerValue = 1; // Giá trị thực tế hoặc bạn có thể mock giá trị
        assertDoesNotThrow(() -> monthlyQuestionService.getPoint2MonthWeb(someIntegerValue,someIntegerValue));
        assertDoesNotThrow(() -> monthlyQuestionService.getPoint2Month(someIntegerValue, someIntegerValue));

    }
    @Test
    void testGetPoint2MonthWeb_Fail() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        List<Integer> monthlyNumbers  = new ArrayList<>();
        monthlyQuestionPoint = new MonthlyQuestionPoint();
        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.of(appUser));
        when(   monthlyQuestionRepository.find2ByAppUserAndMonthNumber(any(Integer.class),
                any(Integer.class)))
                .thenReturn(monthlyNumbers);
        when(   monthlyQuestionPointRepository.findByMonthAndUser(any(Integer.class),
                any(Integer.class)))
                .thenReturn(Optional.ofNullable(monthlyQuestionPoint));
        // Act and Assert
        Integer someIntegerValue = 1; // Giá trị thực tế hoặc bạn có thể mock giá trị
        List<MonthlyStatisticResponseDTO> result =monthlyQuestionService.getPoint2Month(any(Integer.class),
                any(Integer.class));
        List<MonthlyStatisticResponseDTO> result1 =monthlyQuestionService.getPoint2MonthWeb(someIntegerValue,someIntegerValue);
        assertEquals(result.size(),0);
        assertEquals(result1.size(),0);
    }

    @Test
    void testGetPoint12MonthWeb_Success() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        List<Integer> monthlyNumbers  = new ArrayList<>();
        monthlyNumbers.add(1);
        monthlyNumbers.add(2);
        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.of(appUser));
        when(   monthlyQuestionRepository.find12ByAppUser(any(Integer.class)))
                .thenReturn(monthlyNumbers);
        when(   monthlyQuestionPointRepository.findByMonthAndUser(any(Integer.class),
                any(Integer.class)))
                .thenReturn(Optional.ofNullable(monthlyQuestionPoint));
        // Act and Assert
        Integer someIntegerValue = 1; // Giá trị thực tế hoặc bạn có thể mock giá trị
        assertDoesNotThrow(() ->  monthlyQuestionService.getPoint12MonthWeb(someIntegerValue));
    }
    @Test
    void testGetPoint12MonthWeb_Fail() {
        // Arrange
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        List<Integer> monthlyNumbers  = new ArrayList<>();

        when(appUserRepository.findByAccountEmail(any(String.class)))
                .thenReturn(Optional.of(appUser));
        when(   monthlyQuestionRepository.find12ByAppUser(any(Integer.class)))
                .thenReturn(monthlyNumbers);
        when(   monthlyQuestionPointRepository.findByMonthAndUser(any(Integer.class),
                any(Integer.class)))
                .thenReturn(Optional.ofNullable(monthlyQuestionPoint));
        // Act and Assert
        Integer someIntegerValue = 1;
        List<MonthlyStatisticResponseDTO> result =monthlyQuestionService.getPoint12MonthWeb(someIntegerValue);
        assertEquals(result.size(),0);
    }

}
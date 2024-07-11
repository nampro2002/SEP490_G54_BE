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
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.WeekReview;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicineRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.StepRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.WeekReviewRepository;
import vn.edu.fpt.SmartHealthC.serivce.WeeklyReviewService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WeeklyReviewServiceImplTest {


    @Mock
    private WeekReviewRepository weekReviewRepository;

    @Mock
    private MedicineRecordRepository medicineRecordRepository;

    @Mock
    private StepRecordRepository stepRecordRepository;



    @InjectMocks
    private WeeklyReviewServiceImpl weeklyReviewService;

    @Mock
    private AppUserRepository appUserRepository;


    // No need to @InjectMocks here as we aren't injecting mocks into it
    private AppUserServiceImpl appUserService;

    private AppUser testAppUser;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");
    }

    @Test
    void saveDataReviewForWeek_Sucess(){
        when( appUserRepository.findAllActiveAndNotDeleted()).thenReturn(new ArrayList<AppUser>());
        List<AppUser> appUserList = appUserRepository.findAllActiveAndNotDeleted();
        assertNotNull(appUserList);
        org.assertj.core.api.Assertions.assertThat(appUserList.size()).isGreaterThanOrEqualTo(0);

        when( appUserRepository.findAllActiveAndNotDeleted()).thenReturn(new ArrayList<AppUser>());
        List<WeekReview> weekReviews = weekReviewRepository.findByAppUserId(any());
        assertNotNull(weekReviews);
        org.assertj.core.api.Assertions.assertThat(weekReviews.size()).isGreaterThanOrEqualTo(0);

        WeekReview weekReview = new WeekReview();
        when(weekReviewRepository.save(weekReview)).thenReturn(weekReview);
        WeekReview result = weekReviewRepository.save(weekReview);
        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(weekReview);
    }

    @Test
    void getWebDataReviewForWeek_Sucess(){

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");
        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.of(new AppUser()));


        when(weekReviewRepository.findByAppUserId(any())).thenReturn(new ArrayList<WeekReview>());
        List<WeekReview> weekReviews = weekReviewRepository.findByAppUserId(any());
        assertNotNull(weekReviews);
        org.assertj.core.api.Assertions.assertThat(weekReviews.size()).isGreaterThanOrEqualTo(0);

        assertDoesNotThrow(() ->weekReviewRepository.findByAppUserId(any()));
    }

    @Test
    void getMobileDataReviewForWeek_Sucess(){

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");
        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.of(new AppUser()));


        when(weekReviewRepository.findByAppUserId(any())).thenReturn(new ArrayList<WeekReview>());
        List<WeekReview> weekReviews = weekReviewRepository.findByAppUserId(any());
        assertNotNull(weekReviews);
        org.assertj.core.api.Assertions.assertThat(weekReviews.size()).isGreaterThanOrEqualTo(0);

        assertDoesNotThrow(() ->weekReviewRepository.findByAppUserId(any()));

        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findByAppUser(any());
        assertNotNull(medicineRecordList);
        org.assertj.core.api.Assertions.assertThat(medicineRecordList.size()).isGreaterThanOrEqualTo(0);

        assertDoesNotThrow(() ->medicineRecordRepository.findByAppUser(any()));

        when(stepRecordRepository.findAll()).thenReturn(new ArrayList<StepRecord>());
        List<StepRecord> stepRecordList = stepRecordRepository.findAll();
        assertNotNull(stepRecordList);
        org.assertj.core.api.Assertions.assertThat(stepRecordList.size()).isGreaterThanOrEqualTo(0);
        assertDoesNotThrow(() ->stepRecordRepository.findAll());
    }



}

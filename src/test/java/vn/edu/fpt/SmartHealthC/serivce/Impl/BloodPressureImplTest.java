package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalHistory;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.BloodPressureRecordRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BloodPressureImplTest {

    @MockBean
    private BloodPressureRecordRepository bloodPressureRecordRepository;

    @MockBean
    private AppUserRepository appUserRepository;


    private BloodPressureRecord bloodPressureRecordInput;
    private BloodPressureRecord bloodPressureRecordOutput;
    private AppUser testAppUser;
    private  Date testWeekStart;
    private  Date testDate;
    private List<Date> testBloodPressureWeekList;
    private  List<BloodPressureRecord> testBloodPressureRecords;
    @BeforeEach
    public void setUp() throws ParseException {

        LocalDateTime futureDateTime = LocalDateTime.now();
        LocalDateTime futureDate = LocalDateTime.now().plusMinutes(5);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            testWeekStart = dateFormat.parse(futureDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
         testDate = dateFormat.parse(futureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

         bloodPressureRecordInput = BloodPressureRecord.builder()
                .diastole(123f)
                .systole(123f)
                .weekStart(testWeekStart)
                .date(testDate).build();
         bloodPressureRecordOutput = BloodPressureRecord.builder()
                .diastole(123f)
                .systole(123f)
                .weekStart(testWeekStart)
                .date(testDate).build();

        // Khởi tạo các đối tượng test
        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");

        testBloodPressureWeekList= new ArrayList<>();
        testBloodPressureWeekList.add(testWeekStart);
        testBloodPressureWeekList.add(testDate);

        testBloodPressureRecords = new ArrayList<>();
        testBloodPressureRecords.add(bloodPressureRecordInput);
        testBloodPressureRecords.add(bloodPressureRecordOutput);



    }

    @Test
    void createBloodPressureRecord_Success() {
        String email = "test@test.com";

        //Act
        when(appUserRepository.findByAccountEmail(email)).thenReturn(Optional.of(testAppUser));
        when(bloodPressureRecordRepository.save(bloodPressureRecordInput)).thenReturn(bloodPressureRecordOutput);
        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail(email);
        BloodPressureRecord result = bloodPressureRecordRepository.save(bloodPressureRecordInput);

        //THEN
        assertNotNull(resultAppUser.get());
        assertNotNull(result);
        Assertions.assertThat(result.getWeekStart()).isEqualTo(bloodPressureRecordOutput.getWeekStart());
        assertDoesNotThrow(() -> bloodPressureRecordRepository.save(bloodPressureRecordInput));

    }

    @Test
    void getListBloodPressureRecordsByUser_Success() {
        Integer userId = 13;

        //Act
        when(bloodPressureRecordRepository.findDistinctWeek(userId)).thenReturn(testBloodPressureWeekList);
        when(bloodPressureRecordRepository.findByWeekStart(testWeekStart,userId)).thenReturn(testBloodPressureRecords);
        List<Date> resultDate = bloodPressureRecordRepository.findDistinctWeek(userId);
        List<BloodPressureRecord> result = bloodPressureRecordRepository.findByWeekStart(testWeekStart,userId);

        //THEN
        assertNotNull(resultDate);
        assertNotNull(result);
        Assertions.assertThat(result.size()).isGreaterThan(0);
        assertDoesNotThrow(() -> bloodPressureRecordRepository.findDistinctWeek(userId));
        assertDoesNotThrow(() -> bloodPressureRecordRepository.findByWeekStart(testWeekStart,userId));

    }
    @Test
    void getDataChart_Success() {
        Integer userId = 13;
        String email = "test@test.com";
        //Act
        when(appUserRepository.findByAccountEmail(email)).thenReturn(Optional.of(testAppUser));
        when(bloodPressureRecordRepository.findAllByUserId(userId)).thenReturn(testBloodPressureRecords);
        Optional<AppUser> resultAppUser = appUserRepository.findByAccountEmail(email);
        List<BloodPressureRecord> result = bloodPressureRecordRepository.findAllByUserId(userId);

        //THEN
        assertNotNull(resultAppUser);
        assertNotNull(result);
        Assertions.assertThat(result.size()).isGreaterThan(0);
        assertDoesNotThrow(() -> appUserRepository.findByAccountEmail(email));
        assertDoesNotThrow(() -> bloodPressureRecordRepository.findAllByUserId(userId));

    }

}

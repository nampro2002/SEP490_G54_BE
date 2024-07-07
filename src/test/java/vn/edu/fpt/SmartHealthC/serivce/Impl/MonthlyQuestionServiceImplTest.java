package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vn.edu.fpt.SmartHealthC.domain.Enum.MonthlyRecordType;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MonthlyQuestionRepository;
import vn.edu.fpt.SmartHealthC.serivce.Impl.MonthlyQuestionServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MonthlyQuestionServiceImplTest {

    @Mock
    private MonthlyQuestionRepository monthlyQuestionRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private MonthlyQuestionServiceImpl monthlyQuestionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    void testCreateMonthlyQuestion_Valid() {
//        // Arrange
//        MonthlyQuestionDTO monthlyQuestionDTO = new MonthlyQuestionDTO();
//        Date updatedDate = new Date();
//        monthlyQuestionDTO.setMonthStart(updatedDate);
//        monthlyQuestionDTO.setMonthlyRecordType(MonthlyRecordType.SAT_SF_C); // Invalid empty type
//        monthlyQuestionDTO.setQuestionNumber(1);
//        monthlyQuestionDTO.setQuestion("Test Question");
//        monthlyQuestionDTO.setAnswer(1);
////        monthlyQuestionDTO.setAppUserId(1);
//
//        AppUser appUser = new AppUser();
//        appUser.setId(1);
////        when(appUserRepository.findById(monthlyQuestionDTO.getAppUserId()))
////                .thenReturn(Optional.of(appUser));
//
//        MonthlyRecord monthlyRecordSaved = MonthlyRecord.builder()
//                .id(1)
//                .monthStart(monthlyQuestionDTO.getMonthStart())
//                .monthlyRecordType(monthlyQuestionDTO.getMonthlyRecordType())
//                .questionNumber(monthlyQuestionDTO.getQuestionNumber())
//                .question(monthlyQuestionDTO.getQuestion())
//                .answer(monthlyQuestionDTO.getAnswer())
//                .appUserId(appUser)
//                .build();
//
//        when(monthlyQuestionRepository.save(any(MonthlyRecord.class)))
//                .thenReturn(monthlyRecordSaved);
//
//        // Act
//        MonthlyRecord result = monthlyQuestionService.createMonthlyQuestion(monthlyQuestionDTO);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(monthlyQuestionDTO.getMonthStart(), result.getMonthStart());
//        assertEquals(monthlyQuestionDTO.getMonthlyRecordType(), result.getMonthlyRecordType());
//        assertEquals(monthlyQuestionDTO.getQuestionNumber(), result.getQuestionNumber());
//        assertEquals(monthlyQuestionDTO.getQuestion(), result.getQuestion());
//        assertEquals(monthlyQuestionDTO.getAnswer(), result.getAnswer());
//        assertEquals(appUser.getId(), result.getAppUserId().getId());
//    }

//    @Test
//    void testCreateMonthlyQuestion_AppUserNotFound() {
//        // Arrange
//        MonthlyQuestionDTO monthlyQuestionDTO = new MonthlyQuestionDTO();
////        monthlyQuestionDTO.setAppUserId(999); // Non-existent user ID
//
////        when(appUserRepository.findById(monthlyQuestionDTO.getAppUserId()))
////                .thenReturn(Optional.empty());
//
//        // Act & Assert
//        AppException exception = assertThrows(AppException.class,
//                () -> monthlyQuestionService.createMonthlyQuestion(monthlyQuestionDTO));
//
//        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());
//    }

    @Test
    void testGetMonthlyQuestionById_ValidId() {
        // Arrange
        int id = 1;
        MonthlyRecord mockRecord = new MonthlyRecord();
        mockRecord.setId(id);
        when(monthlyQuestionRepository.findById(id))
                .thenReturn(Optional.of(mockRecord));

        // Act
        MonthlyRecord result = monthlyQuestionService.getMonthlyQuestionById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testGetMonthlyQuestionById_InvalidId() {
        // Arrange
        int id = 999; // Non-existent ID
        when(monthlyQuestionRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> monthlyQuestionService.getMonthlyQuestionById(id));

        assertEquals(ErrorCode.MONTHLY_QUESTION_NOTFOUND, exception.getErrorCode());
    }

    @Test
    void testGetAllMonthlyQuestionsMobile() {
        // Arrange
        List<MonthlyRecord> mockList = new ArrayList<>();
        mockList.add(new MonthlyRecord());
        mockList.add(new MonthlyRecord());
        when(monthlyQuestionRepository.findAll())
                .thenReturn(mockList);

        // Act
        List<MonthlyRecord> result = monthlyQuestionService.getAllMonthlyQuestionsMobile();

        // Assert
        assertNotNull(result);
        assertEquals(mockList.size(), result.size());
    }
//    @Test
//    void testCreateMonthlyQuestion_InvalidMonthlyRecordType() {
//        // Arrange
//        MonthlyQuestionDTO monthlyQuestionDTO = new MonthlyQuestionDTO();
//        Date updatedDate = new Date();
//        monthlyQuestionDTO.setMonthStart(updatedDate);
//        monthlyQuestionDTO.setMonthlyRecordType(MonthlyRecordType.SAT_SF_C); // Invalid empty type
//        monthlyQuestionDTO.setQuestionNumber(1);
//        monthlyQuestionDTO.setQuestion("Test Question");
//        monthlyQuestionDTO.setAnswer(1);
////        monthlyQuestionDTO.setAppUserId(1);
//
//        AppUser appUser = new AppUser();
//        appUser.setId(1);
////        when(appUserRepository.findById(monthlyQuestionDTO.getAppUserId()))
////                .thenReturn(Optional.of(appUser));
//
//        // Act & Assert
//        try {
//            monthlyQuestionService.createMonthlyQuestion(monthlyQuestionDTO);
//        } catch (AppException exception) {
//            assertEquals(ErrorCode.MONTHLY_QUESTION_NOTFOUND, exception.getErrorCode());
//        }
//    }

    @Test
    void testUpdateMonthlyQuestion_MonthlyQuestionNotFound() {
        // Arrange
        int id = 999; // Non-existent ID
        MonthlyQuestionDTO updateDTO = new MonthlyQuestionDTO();
        Date updatedDate = new Date(); // Replace with your actual date instance
        updateDTO.setMonthStart(updatedDate);
        updateDTO.setMonthlyRecordType(MonthlyRecordType.SAT_SF_C);
        updateDTO.setQuestionNumber(2);
        updateDTO.setQuestion("Updated Question");
        updateDTO.setAnswer(1);

        when(monthlyQuestionRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> monthlyQuestionService.updateMonthlyQuestion(id, updateDTO));

        assertEquals(ErrorCode.MONTHLY_QUESTION_NOTFOUND, exception.getErrorCode());
    }

    @Test
    void testDeleteMonthlyQuestion_MonthlyQuestionNotFound() {
        // Arrange
        int id = 999; // Non-existent ID

        when(monthlyQuestionRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> monthlyQuestionService.deleteMonthlyQuestion(id));

        assertEquals(ErrorCode.MONTHLY_QUESTION_NOTFOUND, exception.getErrorCode());
    }

    @Test
    void testUpdateMonthlyQuestion_ValidId() {
        // Arrange
        int id = 1;
        MonthlyQuestionDTO updateDTO = new MonthlyQuestionDTO();
        Date updatedDate = new Date(); // Replace with your actual date instance
        updateDTO.setMonthStart(updatedDate);
        updateDTO.setMonthlyRecordType(MonthlyRecordType.SAT_SF_C);
        updateDTO.setQuestionNumber(2);
        updateDTO.setQuestion("Updated Question");
        updateDTO.setAnswer(1);

        MonthlyRecord existingRecord = new MonthlyRecord();
        existingRecord.setId(id);
        existingRecord.setMonthStart(updatedDate);
        existingRecord.setMonthlyRecordType(MonthlyRecordType.SAT_SF_C);
        existingRecord.setQuestionNumber(1);
        existingRecord.setQuestion("Initial Question");
        existingRecord.setAnswer(1);

        when(monthlyQuestionRepository.findById(id))
                .thenReturn(Optional.of(existingRecord));

        when(monthlyQuestionRepository.save(any(MonthlyRecord.class)))
                .thenAnswer(invocation -> {
                    MonthlyRecord updatedRecord = invocation.getArgument(0);
                    updatedRecord.setMonthStart(updateDTO.getMonthStart());
                    updatedRecord.setMonthlyRecordType(updateDTO.getMonthlyRecordType());
                    updatedRecord.setQuestionNumber(updateDTO.getQuestionNumber());
                    updatedRecord.setQuestion(updateDTO.getQuestion());
                    updatedRecord.setAnswer(updateDTO.getAnswer());
                    return updatedRecord;
                });

        // Act
        MonthlyRecord result = monthlyQuestionService.updateMonthlyQuestion(id, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(updateDTO.getMonthStart(), result.getMonthStart());
        assertEquals(updateDTO.getMonthlyRecordType(), result.getMonthlyRecordType());
        assertEquals(updateDTO.getQuestionNumber(), result.getQuestionNumber());
        assertEquals(updateDTO.getQuestion(), result.getQuestion());
        assertEquals(updateDTO.getAnswer(), result.getAnswer());
    }

    @Test
    void testDeleteMonthlyQuestion_ValidId() {
        // Arrange
        int id = 1;
        MonthlyRecord existingRecord = new MonthlyRecord();
        existingRecord.setId(id);

        when(monthlyQuestionRepository.findById(id))
                .thenReturn(Optional.of(existingRecord));

        // Act
        MonthlyRecord result = monthlyQuestionService.deleteMonthlyQuestion(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }
}
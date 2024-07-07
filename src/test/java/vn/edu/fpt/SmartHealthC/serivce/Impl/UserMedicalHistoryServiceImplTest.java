package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserMedicalHistoryDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalHistory;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.repository.UserMedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.serivce.Impl.UserMedicalHistoryServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserMedicalHistoryServiceImplTest {

    @Mock
    private UserMedicalHistoryRepository userMedicalHistoryRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;

    @InjectMocks
    private UserMedicalHistoryServiceImpl userMedicalHistoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testCreateUserMedicalHistory_ValidData() {
//        // Arrange
//        UserMedicalHistoryDTO userMedicalHistoryDTO = new UserMedicalHistoryDTO();
////        userMedicalHistoryDTO.setAppUserId(1);
//        userMedicalHistoryDTO.setConditionId(1);
//
//        AppUser appUser = new AppUser();
//        appUser.setId(1);
//
//        MedicalHistory medicalHistory = new MedicalHistory();
//        medicalHistory.setId(1);
//
//        when(appUserRepository.findById(1)).thenReturn(Optional.of(appUser));
//        when(medicalHistoryRepository.findById(1)).thenReturn(Optional.of(medicalHistory));
//        when(userMedicalHistoryRepository.save(any(UserMedicalHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act
//        UserMedicalHistory createdUserMedicalHistory = userMedicalHistoryService.createUserMedicalHistory(userMedicalHistoryDTO);
//
//        // Assert
//        assertNotNull(createdUserMedicalHistory);
//        assertEquals(appUser, createdUserMedicalHistory.getAppUserId());
//        assertEquals(medicalHistory, createdUserMedicalHistory.getConditionId());
//    }

//    @Test
//    void testCreateUserMedicalHistory_AppUserNotFound() {
//        // Arrange
//        UserMedicalHistoryDTO userMedicalHistoryDTO = new UserMedicalHistoryDTO();
////        userMedicalHistoryDTO.setAppUserId(1);
//        userMedicalHistoryDTO.setConditionId(1);
//
//        when(appUserRepository.findById(1)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(AppException.class, () -> userMedicalHistoryService.createUserMedicalHistory(userMedicalHistoryDTO));
//    }

//    @Test
//    void testCreateUserMedicalHistory_MedicalHistoryNotFound() {
//        // Arrange
//        UserMedicalHistoryDTO userMedicalHistoryDTO = new UserMedicalHistoryDTO();
////        userMedicalHistoryDTO.setAppUserId(1);
//        userMedicalHistoryDTO.setConditionId(1);
//
//        AppUser appUser = new AppUser();
//        appUser.setId(1);
//
//        when(appUserRepository.findById(1)).thenReturn(Optional.of(appUser));
//        when(medicalHistoryRepository.findById(1)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(AppException.class, () -> userMedicalHistoryService.createUserMedicalHistory(userMedicalHistoryDTO));
//    }

//    @Test
//    void testGetUserMedicalHistoryById_ValidId() {
//        // Arrange
//        UserMedicalHistory userMedicalHistory = new UserMedicalHistory();
//        userMedicalHistory.setId(1);
//
//        when(userMedicalHistoryRepository.findById(1)).thenReturn(Optional.of(userMedicalHistory));
//
//        // Act
//        UserMedicalHistory retrievedUserMedicalHistory = userMedicalHistoryService.getUserMedicalHistoryById(1);
//
//        // Assert
//        assertNotNull(retrievedUserMedicalHistory);
//        assertEquals(userMedicalHistory.getId(), retrievedUserMedicalHistory.getId());
//    }

    @Test
    void testGetUserMedicalHistoryById_InvalidId() {
        // Arrange
        when(userMedicalHistoryRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> userMedicalHistoryService.getUserMedicalHistoryById(1));
    }
}
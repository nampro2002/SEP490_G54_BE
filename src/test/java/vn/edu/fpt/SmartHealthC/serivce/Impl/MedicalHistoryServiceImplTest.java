package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalHistory;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalHistoryRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicalHistoryResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRuleResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.MedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.serivce.Impl.MedicalHistoryServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MedicalHistoryServiceImplTest {

    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;

    @InjectMocks
    private MedicalHistoryServiceImpl medicalHistoryService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetMedicalHistoryEntityById_ValidId() {
        // Arrange
        Integer id = 1;
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setId(id);
        medicalHistory.setName("Test Medical History");
        medicalHistory.setType(TypeMedicalHistory.CARDINAL);

        when(medicalHistoryRepository.findById(id)).thenReturn(Optional.of(medicalHistory));

        // Act
        MedicalHistory result = medicalHistoryService.getMedicalHistoryEntityById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Test Medical History", result.getName());
        assertEquals(TypeMedicalHistory.CARDINAL, result.getType());
    }

    @Test
    void testGetMedicalHistoryEntityById_InvalidId() {
        // Arrange
        Integer id = 999;

        when(medicalHistoryRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AppException.class, () -> {
            medicalHistoryService.getMedicalHistoryEntityById(id);
        });

        assertEquals(ErrorCode.MEDICAL_HISTORY_NOT_FOUND, ((AppException) exception).getErrorCode());
    }

    @Test
    void testGetAllMedicalHistory_ValidData() {
        // Arrange
        Integer pageNo = 0;
        String search = "";

        MedicalHistory medicalHistory1 = new MedicalHistory();
        medicalHistory1.setId(1);
        medicalHistory1.setName("Medical History 1");
        medicalHistory1.setType(TypeMedicalHistory.CARDINAL);

        MedicalHistory medicalHistory2 = new MedicalHistory();
        medicalHistory2.setId(2);
        medicalHistory2.setName("Medical History 2");
        medicalHistory2.setType(TypeMedicalHistory.CARDINAL);

        List<MedicalHistory> medicalHistories = new ArrayList<>();
        medicalHistories.add(medicalHistory1);
        medicalHistories.add(medicalHistory2);

        Page<MedicalHistory> pagedResult = new PageImpl<>(medicalHistories);
        when(medicalHistoryRepository.findAllNotDeleted(any(Pageable.class), anyString())).thenReturn(pagedResult);

        // Act
        ResponsePaging<List<MedicalHistoryResDTO>> response = medicalHistoryService.getAllMedicalHistory(pageNo, search);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getCurrentPage());
        assertEquals(1, response.getTotalPages());
        assertEquals(2, response.getTotalItems());
        assertEquals(2, response.getDataResponse().size());
        assertEquals("Medical History 1", response.getDataResponse().get(0).getName());
        assertEquals(TypeMedicalHistory.CARDINAL, response.getDataResponse().get(0).getType());
        assertEquals("Medical History 2", response.getDataResponse().get(1).getName());
        assertEquals(TypeMedicalHistory.CARDINAL, response.getDataResponse().get(1).getType());
    }

    @Test
    void testGetAllMedicalHistory_EmptyData() {
        // Arrange
        Integer pageNo = 0;
        String search = "";

        Page<MedicalHistory> pagedResult = new PageImpl<>(Collections.emptyList());
        when(medicalHistoryRepository.findAllNotDeleted(any(Pageable.class), anyString())).thenReturn(pagedResult);

        // Act
        ResponsePaging<List<MedicalHistoryResDTO>> response = medicalHistoryService.getAllMedicalHistory(pageNo, search);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getCurrentPage());
        assertEquals(1, response.getTotalPages());
        assertEquals(0, response.getTotalItems());
        assertTrue(response.getDataResponse().isEmpty());
    }

    @Test
    void testUpdateMedicalHistory_ValidId() {
        // Arrange
        Integer id = 1;
        MedicalHistoryRequestDTO requestDTO = new MedicalHistoryRequestDTO();
        requestDTO.setName("Updated Medical History");
        requestDTO.setType(TypeMedicalHistory.CARDINAL);
        requestDTO.setDeleted(false);

        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setId(id);
        medicalHistory.setName("Original Medical History");
        medicalHistory.setType(TypeMedicalHistory.CARDINAL);

        when(medicalHistoryRepository.findById(id)).thenReturn(Optional.of(medicalHistory));
        when(medicalHistoryRepository.save(any())).thenReturn(medicalHistory);

        // Act
        MedicalHistoryResDTO updatedDTO = medicalHistoryService.updateMedicalHistory(id, requestDTO);

        // Assert
        assertNotNull(updatedDTO);
        assertEquals(id, updatedDTO.getId());
        assertEquals("Updated Medical History", updatedDTO.getName());
        assertEquals(TypeMedicalHistory.CARDINAL, updatedDTO.getType());
    }

    @Test
    void testUpdateMedicalHistory_InvalidId() {
        // Arrange
        Integer id = 999;
        MedicalHistoryRequestDTO requestDTO = new MedicalHistoryRequestDTO();
        requestDTO.setName("Updated Medical History");
        requestDTO.setType(TypeMedicalHistory.CARDINAL);
        requestDTO.setDeleted(false);

        when(medicalHistoryRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AppException.class, () -> {
            medicalHistoryService.updateMedicalHistory(id, requestDTO);
        });

        assertEquals(ErrorCode.MEDICAL_HISTORY_NOT_FOUND, ((AppException) exception).getErrorCode());
    }

    @Test
    void testDeleteMedicalHistory_ValidId() {
        // Arrange
        Integer id = 5;
        MedicalHistory medicalHistory1 = new MedicalHistory();
        medicalHistory1.setId(id);
        medicalHistory1.setName("Medical History 1");
        medicalHistory1.setNameEn("Medical History 1 En");
        medicalHistory1.setImageUrl("123");
        medicalHistory1.setUserMedicalHistories(new ArrayList<>());
        medicalHistory1.setType(TypeMedicalHistory.CARDINAL);


        when(medicalHistoryRepository.findById(id)).thenReturn(Optional.of(medicalHistory1));
        when(medicalHistoryRepository.save(medicalHistory1)).thenReturn(medicalHistory1);

        // Act
        MedicalHistoryResDTO deletedDTO = medicalHistoryService.deleteMedicalHistory(id);

        // Assert
        assertNotNull(deletedDTO);
        assertEquals(id, deletedDTO.getId());
        assertEquals("Medical History 1", deletedDTO.getName());
        assertEquals(TypeMedicalHistory.CARDINAL, deletedDTO.getType());
    }

    @Test
    void testDeleteMedicalHistory_InvalidId() {
        // Arrange
        Integer id = 999;

        when(medicalHistoryRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AppException.class, () -> {
            medicalHistoryService.deleteMedicalHistory(id);
        });

        assertEquals(ErrorCode.MEDICAL_HISTORY_NOT_FOUND, ((AppException) exception).getErrorCode());
    }

    @Test
    void testGetAllMedicalHistoryMobile() {
        // Arrange
        MedicalHistory medicalHistory1 = new MedicalHistory();
        medicalHistory1.setId(1);
        medicalHistory1.setName("Medical History 1");
        medicalHistory1.setNameEn("Medical History 1 En");
        medicalHistory1.setImageUrl("123");
        medicalHistory1.setUserMedicalHistories(new ArrayList<>());
        medicalHistory1.setType(TypeMedicalHistory.CARDINAL);

        MedicalHistory medicalHistory2 = new MedicalHistory();
        medicalHistory2.setId(2);
        medicalHistory2.setName("Medical History 2");
        medicalHistory2.setNameEn("Medical History 2 En");
        medicalHistory2.setImageUrl("123");
        medicalHistory2.setUserMedicalHistories(new ArrayList<>());
        medicalHistory2.setType(TypeMedicalHistory.CARDINAL);

        List<MedicalHistory> medicalHistories = new ArrayList<>();
        medicalHistories.add(medicalHistory1);
        medicalHistories.add(medicalHistory2);
        // Act
        when(medicalHistoryRepository.findAllNotDeleted()).thenReturn(medicalHistories);
        List<MedicalHistoryResDTO> response = medicalHistoryService.getAllMedicalHistoryMobile(TypeLanguage.EN);




        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Medical History 1 En", response.get(0).getNameEn());
        assertEquals(TypeMedicalHistory.CARDINAL, response.get(0).getType());
        assertEquals("Medical History 2 En", response.get(1).getNameEn());
        assertEquals(TypeMedicalHistory.CARDINAL, response.get(1).getType());
    }

    @Test
    void testGetAllMedicalHistoryMobile_EmptyList() {
        // Arrange
        when(medicalHistoryRepository.findAllNotDeleted()).thenReturn(Collections.emptyList());

        // Act
        List<MedicalHistoryResDTO> response = medicalHistoryService.getAllMedicalHistoryMobile(TypeLanguage.EN);

        // Assert
        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void testCreateMedicalHistory() {
        // Arrange
        MedicalHistoryRequestDTO requestDTO = new MedicalHistoryRequestDTO();
        requestDTO.setName("Medical History 1");
        requestDTO.setType(TypeMedicalHistory.CARDINAL);

        MedicalHistory medicalHistory1 = new MedicalHistory();
        medicalHistory1.setId(1);
        medicalHistory1.setName("Medical History 1");
        medicalHistory1.setNameEn("Medical History 1 En");
        medicalHistory1.setImageUrl("123");
        medicalHistory1.setUserMedicalHistories(new ArrayList<>());
        medicalHistory1.setType(TypeMedicalHistory.CARDINAL);
        when(medicalHistoryRepository.save(any(MedicalHistory.class))).thenReturn(medicalHistory1);

        // Act
        MedicalHistoryResDTO response = medicalHistoryService.createMedicalHistory(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Medical History 1", response.getName());
        assertEquals(TypeMedicalHistory.CARDINAL, response.getType());
        assertFalse(response.isDeleted());
    }



    @Test
    void testGetMedicalHistoryById() {
        // Arrange
        Integer medicalHistoryId = 1;
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setId(medicalHistoryId);
        medicalHistory.setName("Medical History 1");
        medicalHistory.setType(TypeMedicalHistory.ARTHRITIS);

        when(medicalHistoryRepository.findById(medicalHistoryId)).thenReturn(Optional.of(medicalHistory));

        // Act
        MedicalHistoryResDTO response = medicalHistoryService.getMedicalHistoryById(medicalHistoryId);

        // Assert
        assertNotNull(response);
        assertEquals(medicalHistoryId, response.getId());
        assertEquals("Medical History 1", response.getName());
        assertEquals(TypeMedicalHistory.ARTHRITIS, response.getType());
    }

    @Test
    void testGetMedicalHistoryById_NotFound() {
        // Arrange
        Integer medicalHistoryId = 1;

        when(medicalHistoryRepository.findById(medicalHistoryId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AppException.class, () -> {
            medicalHistoryService.getMedicalHistoryById(medicalHistoryId);
        });

        assertEquals(ErrorCode.MEDICAL_HISTORY_NOT_FOUND, ((AppException) exception).getErrorCode());
    }




}
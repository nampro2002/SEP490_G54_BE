package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineTypeRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineTypeResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineType;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.MedicineTypeRepository;
import vn.edu.fpt.SmartHealthC.serivce.Impl.MedicineTypeServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicineTypeServiceImplTest {

    @Mock
    private MedicineTypeRepository medicineTypeRepository;

    @InjectMocks
    private MedicineTypeServiceImpl medicineTypeService;

    @Test
    void testCreateMedicineType_ValidData() {
        // Arrange
        MedicineTypeRequestDTO requestDTO = new MedicineTypeRequestDTO();
        requestDTO.setTitle("Test Medicine Type");
        requestDTO.setDescription("Test Description");

        MedicineType medicineType = new MedicineType();
        medicineType.setId(1);
        medicineType.setTitle(requestDTO.getTitle());
        medicineType.setDescription(requestDTO.getDescription());
        medicineType.setDeleted(false);

        when(medicineTypeRepository.save(any(MedicineType.class))).thenReturn(medicineType);

        // Act
        MedicineTypeResponseDTO responseDTO = medicineTypeService.createMedicineType(requestDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(1, responseDTO.getId());
        assertEquals("Test Medicine Type", responseDTO.getTitle());
        assertEquals("Test Description", responseDTO.getDescription());
        assertFalse(responseDTO.isDeleted());
    }

//    @Test
//    void testCreateMedicineType_NullTitle() {
//        // Arrange
//        MedicineTypeRequestDTO requestDTO = new MedicineTypeRequestDTO();
//        requestDTO.setTitle(null);
//        requestDTO.setDescription("Test Description");
//
//        // Act & Assert
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            medicineTypeService.createMedicineType(requestDTO);
//        });
//
//        assertEquals("Title must not be null or empty", exception.getMessage());
//        verify(medicineTypeRepository, never()).save(any());
//    }

    @Test
    void testGetMedicineTypeById_ValidId() {
        // Arrange
        Integer id = 1;
        MedicineType medicineType = new MedicineType();
        medicineType.setId(id);
        medicineType.setTitle("Test Medicine Type");
        medicineType.setDescription("Test Description");
        medicineType.setDeleted(false);

        when(medicineTypeRepository.findById(id)).thenReturn(Optional.of(medicineType));

        // Act
        MedicineTypeResponseDTO responseDTO = medicineTypeService.getMedicineTypeById(id);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(id, responseDTO.getId());
        assertEquals("Test Medicine Type", responseDTO.getTitle());
        assertEquals("Test Description", responseDTO.getDescription());
        assertFalse(responseDTO.isDeleted());
    }

    @Test
    void testGetMedicineTypeById_InvalidId() {
        // Arrange
        Integer id = 999;

        when(medicineTypeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AppException.class, () -> {
            medicineTypeService.getMedicineTypeById(id);
        });

        assertEquals(ErrorCode.MEDICINE_TYPE_NOT_FOUND, ((AppException) exception).getErrorCode());
    }

    @Test
    void testGetAllMedicineTypes_ValidData() {
        // Arrange
        Integer pageNo = 0;
        String search = "";

        MedicineType medicineType1 = new MedicineType();
        medicineType1.setId(1);
        medicineType1.setTitle("Medicine Type 1");
        medicineType1.setDescription("Description 1");
        medicineType1.setDeleted(false);

        MedicineType medicineType2 = new MedicineType();
        medicineType2.setId(2);
        medicineType2.setTitle("Medicine Type 2");
        medicineType2.setDescription("Description 2");
        medicineType2.setDeleted(false);

        List<MedicineType> medicineTypeList = new ArrayList<>();
        medicineTypeList.add(medicineType1);
        medicineTypeList.add(medicineType2);

        Page<MedicineType> pagedResult = new PageImpl<>(medicineTypeList);
        when(medicineTypeRepository.findAllNotDeleted(any(Pageable.class), anyString())).thenReturn(pagedResult);

        // Act
        ResponsePaging<List<MedicineTypeResponseDTO>> response = medicineTypeService.getAllMedicineTypes(pageNo, search);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getCurrentPage());
        assertEquals(1, response.getTotalPages());
        assertEquals(2, response.getTotalItems());
        assertEquals(2, response.getDataResponse().size());
        assertEquals("Medicine Type 1", response.getDataResponse().get(0).getTitle());
        assertEquals("Description 1", response.getDataResponse().get(0).getDescription());
        assertEquals("Medicine Type 2", response.getDataResponse().get(1).getTitle());
        assertEquals("Description 2", response.getDataResponse().get(1).getDescription());
    }

    @Test
    void testGetAllMedicineTypes_EmptyData() {
        // Arrange
        Integer pageNo = 0;
        String search = "";

        Page<MedicineType> pagedResult = new PageImpl<>(Collections.emptyList());
        when(medicineTypeRepository.findAllNotDeleted(any(Pageable.class), anyString())).thenReturn(pagedResult);

        // Act
        ResponsePaging<List<MedicineTypeResponseDTO>> response = medicineTypeService.getAllMedicineTypes(pageNo, search);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getCurrentPage());
        assertEquals(1, response.getTotalPages());
        assertEquals(0, response.getTotalItems());
        assertTrue(response.getDataResponse().isEmpty());
    }

    @Test
    void testUpdateMedicineType_ValidId() {
        // Arrange
        Integer id = 1;
        MedicineTypeRequestDTO requestDTO = new MedicineTypeRequestDTO();
        requestDTO.setTitle("Updated Medicine Type");
        requestDTO.setDescription("Updated Description");
        requestDTO.setDeleted(false);

        MedicineType medicineType = new MedicineType();
        medicineType.setId(id);
        medicineType.setTitle("Original Medicine Type");
        medicineType.setDescription("Original Description");
        medicineType.setDeleted(false);

        when(medicineTypeRepository.findById(id)).thenReturn(Optional.of(medicineType));
        when(medicineTypeRepository.save(any())).thenReturn(medicineType);

        // Act
        MedicineTypeResponseDTO updatedDTO = medicineTypeService.updateMedicineType(id, requestDTO);

        // Assert
        assertNotNull(updatedDTO);
        assertEquals(id, updatedDTO.getId());
        assertEquals("Updated Medicine Type", updatedDTO.getTitle());
        assertEquals("Updated Description", updatedDTO.getDescription());
        assertFalse(updatedDTO.isDeleted());
    }

    @Test
    void testUpdateMedicineType_InvalidId() {
        // Arrange
        Integer id = 999;
        MedicineTypeRequestDTO requestDTO = new MedicineTypeRequestDTO();
        requestDTO.setTitle("Updated Medicine Type");
        requestDTO.setDescription("Updated Description");
        requestDTO.setDeleted(false);

        when(medicineTypeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AppException.class, () -> {
            medicineTypeService.updateMedicineType(id, requestDTO);
        });

        assertEquals(ErrorCode.MEDICINE_TYPE_NOT_FOUND, ((AppException) exception).getErrorCode());
    }

    @Test
    void testDeleteMedicineType_ValidId() {
        // Arrange
        Integer id = 1;
        MedicineType medicineType = new MedicineType();
        medicineType.setId(id);
        medicineType.setTitle("Test Medicine Type");
        medicineType.setTitleEn("12312321as");
        medicineType.setDescription("Test Description");
        medicineType.setDeleted(true);

//        when(medicineTypeService.getMedicineTypeEntityById(id)).thenReturn(medicineType);
        when(medicineTypeRepository.findById(id)).thenReturn(Optional.of(medicineType));
        when(medicineTypeRepository.save(medicineType)).thenReturn(medicineType);

        // Act
        MedicineTypeResponseDTO deletedDTO = medicineTypeService.deleteMedicineType(id);

        // Assert
        assertNotNull(deletedDTO);
        assertEquals(id, deletedDTO.getId());
        assertEquals("Test Medicine Type", deletedDTO.getTitle());
        assertEquals("Test Description", deletedDTO.getDescription());
        assertTrue(deletedDTO.isDeleted());

//        verify(medicineTypeRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteMedicineType_InvalidId() {
        // Arrange
        Integer id = 999;

        when(medicineTypeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AppException.class, () -> {
            medicineTypeService.deleteMedicineType(id);
        });

        assertEquals(ErrorCode.MEDICINE_TYPE_NOT_FOUND, ((AppException) exception).getErrorCode());
    }

    @Test
    void testGetAllMedicineTypesMobile() {
        // Arrange
        MedicineType medicineType1 = new MedicineType();
        medicineType1.setId(1);
        medicineType1.setTitle("Medicine Type 1");
        medicineType1.setTitleEn("Medicine Type 1");
        medicineType1.setDescription("Description 1");
        medicineType1.setDeleted(false);

        MedicineType medicineType2 = new MedicineType();
        medicineType2.setId(2);
        medicineType2.setTitle("Medicine Type 2");
        medicineType2.setTitleEn("Medicine Type 2");
        medicineType2.setDescription("Description 2");
        medicineType2.setDeleted(false);

        List<MedicineType> medicineTypeList = new ArrayList<>();
        medicineTypeList.add(medicineType1);
        medicineTypeList.add(medicineType2);

        when(medicineTypeRepository.findAllNotDeleted()).thenReturn(medicineTypeList);

        // Act
        List<MedicineTypeResponseDTO> response = medicineTypeService.getAllMedicineTypesMobile(TypeLanguage.EN);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Medicine Type 1", response.get(0).getTitle());
        assertEquals("Description 1", response.get(0).getDescription());
        assertEquals("Medicine Type 2", response.get(1).getTitle());
        assertEquals("Description 2", response.get(1).getDescription());
    }

    @Test
    void testGetAllMedicineTypesMobile_EmptyList() {
        // Arrange
        when(medicineTypeRepository.findAllNotDeleted()).thenReturn(Collections.emptyList());

        // Act
        List<MedicineTypeResponseDTO> response = medicineTypeService.getAllMedicineTypesMobile(TypeLanguage.EN);

        // Assert
        assertNotNull(response);
        assertTrue(response.isEmpty());
    }
}
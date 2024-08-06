//package vn.edu.fpt.SmartHealthC.serivce.Impl;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRuleRequestDTO;
//import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRuleResponseDTO;
//import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
//import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;
//import vn.edu.fpt.SmartHealthC.exception.AppException;
//import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
//import vn.edu.fpt.SmartHealthC.repository.MentalRuleRepository;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class MentalRuleServiceImplTest {
//
//    @Mock
//    private MentalRuleRepository mentalRuleRepository;
//
//    @InjectMocks
//    private MentalRuleServiceImpl mentalRuleService;
//
//    @Test
//    void testCreateMentalRule_ValidData() {
//        // Arrange
//        MentalRuleRequestDTO requestDTO = new MentalRuleRequestDTO();
//        requestDTO.setTitle("Test Title");
//        requestDTO.setDescription("Test Description");
//
//        MentalRule mentalRuleToSave = MentalRule.builder()
//                .title(requestDTO.getTitle())
//                .description(requestDTO.getDescription())
//                .isDeleted(false)
//                .build();
//
//        MentalRule savedMentalRule = MentalRule.builder()
//                .id(1)
//                .title(requestDTO.getTitle())
//                .description(requestDTO.getDescription())
//                .isDeleted(false)
//                .build();
//
//        when(mentalRuleRepository.save(any())).thenReturn(savedMentalRule);
//
//        // Act
//        MentalRuleResponseDTO result = mentalRuleService.createMentalRule(requestDTO);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(savedMentalRule.getId(), result.getId());
//        assertEquals(savedMentalRule.getTitle(), result.getTitle());
//        assertEquals(savedMentalRule.getDescription(), result.getDescription());
//        assertFalse(result.isDeleted());
//
//        verify(mentalRuleRepository, times(1)).save(any());
//    }
//
////    @Test
////    void testCreateMentalRule_NullTitle() {
////        // Arrange
////        MentalRuleRequestDTO requestDTO = new MentalRuleRequestDTO();
////        requestDTO.setTitle(null);
////        requestDTO.setDescription("Test Description");
////
////        // Act & Assert
////        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
////            mentalRuleService.createMentalRule(requestDTO);
////        });
////
////        assertEquals("Title must not be null or empty", exception.getMessage());
////        verify(mentalRuleRepository, never()).save(any());
////    }
//
//    @Test
//    void testGetMentalRuleById_ValidId() {
//        // Arrange
//        Integer id = 1;
//        MentalRule mentalRule = MentalRule.builder()
//                .id(id)
//                .title("Test Title")
//                .description("Test Description")
//                .isDeleted(false)
//                .build();
//
//        when(mentalRuleRepository.findById(id)).thenReturn(Optional.of(mentalRule));
//
//        // Act
//        MentalRuleResponseDTO result = mentalRuleService.getMentalRuleById(id);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(mentalRule.getId(), result.getId());
//        assertEquals(mentalRule.getTitle(), result.getTitle());
//        assertEquals(mentalRule.getDescription(), result.getDescription());
//        assertFalse(result.isDeleted());
//    }
//
//    @Test
//    void testGetMentalRuleById_InvalidId() {
//        // Arrange
//        Integer id = 999;
//
//        when(mentalRuleRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        Exception exception = assertThrows(AppException.class, () -> {
//            mentalRuleService.getMentalRuleById(id);
//        });
//
//        assertEquals(ErrorCode.MENTAL_RULE_NOT_FOUND, ((AppException) exception).getErrorCode());
//    }
//
//    @Test
//    void testGetAllMentalRules_FirstPage() {
//        // Arrange
//        int pageNo = 0;
//        String search = "";
//
//        List<MentalRule> mentalRuleList = new ArrayList<>();
//        mentalRuleList.add(MentalRule.builder()
//                .id(1)
//                .title("Test Title 1")
//                .description("Test Description 1")
//                .isDeleted(false)
//                .build());
//        mentalRuleList.add(MentalRule.builder()
//                .id(2)
//                .title("Test Title 2")
//                .description("Test Description 2")
//                .isDeleted(false)
//                .build());
//
//        Page<MentalRule> page = new PageImpl<>(mentalRuleList);
//
//        when(mentalRuleRepository.findAllNotDeleted(any(), anyString()))
//                .thenReturn(page);
//
//        // Act
//        ResponsePaging<List<MentalRuleResponseDTO>> result = mentalRuleService.getAllMentalRules(pageNo, search);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.getTotalPages());
//        assertEquals(1, result.getCurrentPage());
//        assertEquals(mentalRuleList.size(), result.getTotalItems());
//        assertEquals(mentalRuleList.size(), result.getDataResponse().size());
//        assertEquals(1, result.getDataResponse().get(0).getId());
//        assertEquals("Test Title 1", result.getDataResponse().get(0).getTitle());
//        assertEquals("Test Description 1", result.getDataResponse().get(0).getDescription());
//        assertFalse(result.getDataResponse().get(0).isDeleted());
//    }
//
//    @Test
//    void testGetAllMentalRules_EmptyResult() {
//        // Arrange
//        int pageNo = 0;
//        String search = "";
//
//        Page<MentalRule> emptyPage = new PageImpl<>(Collections.emptyList());
//
//        when(mentalRuleRepository.findAllNotDeleted(any(), anyString()))
//                .thenReturn(emptyPage);
//
//        // Act
//        ResponsePaging<List<MentalRuleResponseDTO>> result = mentalRuleService.getAllMentalRules(pageNo, search);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.getTotalPages());
//        assertEquals(1, result.getCurrentPage());
//        assertEquals(0, result.getTotalItems());
//        assertTrue(result.getDataResponse().isEmpty());
//    }
//
//    @Test
//    void testUpdateMentalRule_ValidId() {
//        // Arrange
//        Integer id = 1;
//        MentalRuleRequestDTO requestDTO = new MentalRuleRequestDTO();
//        requestDTO.setTitle("Updated Title");
//        requestDTO.setDescription("Updated Description");
////        requestDTO.setDeleted(false);
//
//        MentalRule existingMentalRule = MentalRule.builder()
//                .id(id)
//                .title("Original Title")
//                .description("Original Description")
//                .isDeleted(false)
//                .build();
//
//        MentalRule updatedMentalRule = MentalRule.builder()
//                .id(id)
//                .title(requestDTO.getTitle())
//                .description(requestDTO.getDescription())
////                .isDeleted(requestDTO.isDeleted())
//                .build();
//
//        when(mentalRuleRepository.findById(id)).thenReturn(Optional.of(existingMentalRule));
//        when(mentalRuleRepository.save(any())).thenReturn(updatedMentalRule);
//
//        // Act
//        MentalRuleResponseDTO result = mentalRuleService.updateMentalRule(id, requestDTO);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(updatedMentalRule.getId(), result.getId());
//        assertEquals(updatedMentalRule.getTitle(), result.getTitle());
//        assertEquals(updatedMentalRule.getDescription(), result.getDescription());
//        assertEquals(updatedMentalRule.isDeleted(), result.isDeleted());
//    }
//
//    @Test
//    void testUpdateMentalRule_InvalidId() {
//        // Arrange
//        Integer id = 999;
//        MentalRuleRequestDTO requestDTO = new MentalRuleRequestDTO();
//        requestDTO.setTitle("Updated Title");
//        requestDTO.setDescription("Updated Description");
////        requestDTO.setDeleted(false);
//
//        when(mentalRuleRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        Exception exception = assertThrows(AppException.class, () -> {
//            mentalRuleService.updateMentalRule(id, requestDTO);
//        });
//
//        assertEquals(ErrorCode.MENTAL_RULE_NOT_FOUND, ((AppException) exception).getErrorCode());
//        verify(mentalRuleRepository, never()).save(any());
//    }
//
//    @Test
//    void testDeleteMentalRule_ValidId() {
//        // Arrange
//        Integer id = 1;
//        MentalRule existingMentalRule = MentalRule.builder()
//                .id(id)
//                .title("Test Title")
//                .description("Test Description")
//                .isDeleted(false)
//                .build();
//
//        when(mentalRuleRepository.findById(id)).thenReturn(Optional.of(existingMentalRule));
//        when(mentalRuleRepository.save(any())).thenReturn(existingMentalRule);
//
//        // Act
//        MentalRuleResponseDTO result = mentalRuleService.deleteMentalRule(id);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(existingMentalRule.getId(), result.getId());
//        assertEquals(existingMentalRule.getTitle(), result.getTitle());
//        assertEquals(existingMentalRule.getDescription(), result.getDescription());
//        assertTrue(result.isDeleted());
//    }
//
//    @Test
//    void testDeleteMentalRule_InvalidId() {
//        // Arrange
//        Integer id = 999;
//
//        when(mentalRuleRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        Exception exception = assertThrows(AppException.class, () -> {
//            mentalRuleService.deleteMentalRule(id);
//        });
//
//        assertEquals(ErrorCode.MENTAL_RULE_NOT_FOUND, ((AppException) exception).getErrorCode());
//        verify(mentalRuleRepository, never()).save(any());
//    }
//
//    @Test
//    void testGetAllMentalRulesMobile() {
//        // Arrange
//        List<MentalRule> mentalRuleList = new ArrayList<>();
//        mentalRuleList.add(MentalRule.builder()
//                .id(1)
//                .title("Test Title 1")
//                .description("Test Description 1")
//                .isDeleted(false)
//                .build());
//        mentalRuleList.add(MentalRule.builder()
//                .id(2)
//                .title("Test Title 2")
//                .description("Test Description 2")
//                .isDeleted(false)
//                .build());
//
//        when(mentalRuleRepository.findAllNotDeleted()).thenReturn(mentalRuleList);
//
//        // Act
//        List<MentalRuleResponseDTO> result = mentalRuleService.getAllMentalRulesMobile();
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(mentalRuleList.size(), result.size());
//        assertEquals(1, result.get(0).getId());
//        assertEquals("Test Title 1", result.get(0).getTitle());
//        assertEquals("Test Description 1", result.get(0).getDescription());
//        assertFalse(result.get(0).isDeleted());
//    }
//
//    @Test
//    void testGetAllMentalRulesMobile_EmptyList() {
//        // Arrange
//        when(mentalRuleRepository.findAllNotDeleted()).thenReturn(Collections.emptyList());
//
//        // Act
//        List<MentalRuleResponseDTO> result = mentalRuleService.getAllMentalRulesMobile();
//
//        // Assert
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//}
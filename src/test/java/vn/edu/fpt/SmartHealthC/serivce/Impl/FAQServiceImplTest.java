package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FAQRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FAQResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.FAQRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FAQServiceImplTest {

    @Mock
    private FAQRepository faqRepository;

    @InjectMocks
    private FAQServiceImpl faqService;

    private FAQ testFAQ;
    private FAQRequestDTO testFAQRequestDTO;

    @BeforeEach
    void setUp() {
        testFAQ = FAQ.builder()
                .id(1)
                .question("What is SmartHealth?")
                .answer("SmartHealth is a health management system.")
                .build();

        testFAQRequestDTO = FAQRequestDTO.builder()
                .question("What is SmartHealth?")
                .answer("SmartHealth is a health management system.")
                .build();
    }

    @Test
    void testCreateFAQ_Success() {
        // Arrange
        when(faqRepository.save(any(FAQ.class))).thenReturn(testFAQ);

        // Act
        FAQResponseDTO result = faqService.createFAQ(testFAQRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testFAQ.getId(), result.getId());
        assertEquals(testFAQ.getQuestion(), result.getQuestion());
        assertEquals(testFAQ.getAnswer(), result.getAnswer());
    }

    @Test
    void testCreateFAQ_Failure() {
        // Arrange
        when(faqRepository.save(any(FAQ.class))).thenThrow(new AppException(ErrorCode.NULL_ANSWER));

        // Act and Assert
        assertThrows(AppException.class, () -> faqService.createFAQ(testFAQRequestDTO));
    }

    @Test
    void testGetFAQById_Success() {
        // Arrange
        when(faqRepository.findById(anyInt())).thenReturn(Optional.of(testFAQ));

        // Act
        FAQResponseDTO result = faqService.getFAQById(1);

        // Assert
        assertNotNull(result);
        assertEquals(testFAQ.getId(), result.getId());
        assertEquals(testFAQ.getQuestion(), result.getQuestion());
        assertEquals(testFAQ.getAnswer(), result.getAnswer());
    }

    @Test
    void testGetFAQById_NotFound() {
        // Arrange
        when(faqRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act and Assert
        AppException exception = assertThrows(AppException.class, () -> faqService.getFAQById(1));
        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testGetAllFAQs_Success() {
        // Arrange
        when(faqRepository.findAll()).thenReturn(Arrays.asList(testFAQ));

        // Act
        List<FAQResponseDTO> result = faqService.getAllFAQs();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testFAQ.getId(), result.get(0).getId());
    }

    @Test
    void testGetAllFAQs_EmptyList() {
        // Arrange
        when(faqRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<FAQResponseDTO> result = faqService.getAllFAQs();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testUpdateFAQ_Success() {
        // Arrange
        when(faqRepository.findById(anyInt())).thenReturn(Optional.of(testFAQ));
        when(faqRepository.save(any(FAQ.class))).thenReturn(testFAQ);

        // Act
        FAQResponseDTO result = faqService.updateFAQ(1, testFAQRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testFAQ.getId(), result.getId());
        assertEquals(testFAQ.getQuestion(), result.getQuestion());
        assertEquals(testFAQ.getAnswer(), result.getAnswer());
    }

    @Test
    void testUpdateFAQ_NotFound() {
        // Arrange
        when(faqRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act and Assert
        AppException exception = assertThrows(AppException.class, () -> faqService.updateFAQ(1, testFAQRequestDTO));
        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testDeleteFAQ_Success() {
        // Arrange
        when(faqRepository.findById(anyInt())).thenReturn(Optional.of(testFAQ));
        doNothing().when(faqRepository).deleteById(anyInt());

        // Act
        FAQResponseDTO result = faqService.deleteFAQ(1);

        // Assert
        assertNotNull(result);
        assertEquals(testFAQ.getId(), result.getId());
        assertEquals(testFAQ.getQuestion(), result.getQuestion());
        assertEquals(testFAQ.getAnswer(), result.getAnswer());
    }

    @Test
    void testDeleteFAQ_NotFound() {
        // Arrange
        when(faqRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act and Assert
        AppException exception = assertThrows(AppException.class, () -> faqService.deleteFAQ(1));
        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testGetAllFAQsMobile_Success() {
        // Arrange
        when(faqRepository.findAll()).thenReturn(Arrays.asList(testFAQ));

        // Act
        List<FAQResponseDTO> result = faqService.getAllFAQsMobile();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testFAQ.getId(), result.get(0).getId());
    }

    @Test
    void testGetAllFAQsMobile_EmptyList() {
        // Arrange
        when(faqRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<FAQResponseDTO> result = faqService.getAllFAQsMobile();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testGetFAQEntityById_Success() {
        // Arrange
        when(faqRepository.findById(anyInt())).thenReturn(Optional.of(testFAQ));

        // Act
        FAQ result = faqService.getFAQEntityById(1);

        // Assert
        assertNotNull(result);
        assertEquals(testFAQ.getId(), result.getId());
        assertEquals(testFAQ.getQuestion(), result.getQuestion());
        assertEquals(testFAQ.getAnswer(), result.getAnswer());
    }

    @Test
    void testGetFAQEntityById_NotFound() {
        // Arrange
        when(faqRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act and Assert
        AppException exception = assertThrows(AppException.class, () -> faqService.getFAQEntityById(1));
        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
    }
}
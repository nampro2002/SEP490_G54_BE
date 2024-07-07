package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeQuestion;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FormQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FormQuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.FormQuestionRepository;
import vn.edu.fpt.SmartHealthC.serivce.FormQuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class FormQuestionServiceImplTest {

    @Mock
    private FormQuestionRepository formQuestionRepository;

    @InjectMocks
    private FormQuestionServiceImpl formQuestionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFormQuestion_Success() {
        // Arrange
        FormQuestionRequestDTO requestDTO = new FormQuestionRequestDTO();
        requestDTO.setQuestion("Test question");
        requestDTO.setQuestionNumber(1);
        requestDTO.setType(TypeQuestion.SAT_SF);

        FormQuestion formQuestionSaved = FormQuestion.builder()
                .id(1)
                .question(requestDTO.getQuestion())
                .questionNumber(requestDTO.getQuestionNumber())
                .type(requestDTO.getType())
                .build();

        when(formQuestionRepository.save(any(FormQuestion.class))).thenReturn(formQuestionSaved);

        // Act
        FormQuestionResponseDTO responseDTO = formQuestionService.createFormQuestion(requestDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(formQuestionSaved.getId(), responseDTO.getId());
        assertEquals(formQuestionSaved.getQuestion(), responseDTO.getQuestion());
        assertEquals(formQuestionSaved.getQuestionNumber(), responseDTO.getQuestionNumber());
    }

    @Test
    void testCreateFormQuestion_NullType() {
        // Arrange
        FormQuestionRequestDTO requestDTO = new FormQuestionRequestDTO();
        requestDTO.setQuestion("Test question");
        requestDTO.setQuestionNumber(1);
        requestDTO.setType(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> formQuestionService.createFormQuestion(requestDTO));
    }

    @Test
    void testGetFormQuestionById_Success() {
        // Arrange
        int id = 1;
        FormQuestion formQuestion = FormQuestion.builder()
                .id(id)
                .question("Test question")
                .questionNumber(1)
                .type(TypeQuestion.SAT_SF)
                .build();

        when(formQuestionRepository.findById(id)).thenReturn(Optional.of(formQuestion));

        // Act
        FormQuestionResponseDTO responseDTO = formQuestionService.getFormQuestionById(id);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(formQuestion.getId(), responseDTO.getId());
        assertEquals(formQuestion.getQuestion(), responseDTO.getQuestion());
        assertEquals(formQuestion.getQuestionNumber(), responseDTO.getQuestionNumber());
    }

    @Test
    void testGetFormQuestionById_NotFound() {
        // Arrange
        int id = 1;
        when(formQuestionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> formQuestionService.getFormQuestionById(id));
    }

    @Test
    void testGetAllFormQuestions_Success() {
        // Arrange
        int pageNo = 0;
        String search = "";

        List<FormQuestion> formQuestionList = new ArrayList<>();
        formQuestionList.add(FormQuestion.builder().id(1).question("Question 1").questionNumber(1).type(TypeQuestion.SAT_SF).build());
        formQuestionList.add(FormQuestion.builder().id(2).question("Question 2").questionNumber(2).type(TypeQuestion.SF).build());

        Page<FormQuestion> page = new PageImpl<>(formQuestionList);

        when(formQuestionRepository.findAll(any(Pageable.class), eq(search))).thenReturn(page);

        // Act
        ResponsePaging<List<FormQuestionResponseDTO>> response = formQuestionService.getAllFormQuestions(pageNo, search);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getCurrentPage());
        assertEquals(2, response.getTotalItems());
        assertEquals(1, response.getTotalPages());
        assertEquals(2, response.getDataResponse().size());
        assertEquals(formQuestionList.get(0).getId(), response.getDataResponse().get(0).getId());
        assertEquals(formQuestionList.get(1).getId(), response.getDataResponse().get(1).getId());
    }

    @Test
    void testGetAllFormQuestions_EmptyResult() {
        // Arrange
        int pageNo = 0;
        String search = "";

        Page<FormQuestion> page = Page.empty();

        when(formQuestionRepository.findAll(any(Pageable.class), eq(search))).thenReturn(page);

        // Act
        ResponsePaging<List<FormQuestionResponseDTO>> response = formQuestionService.getAllFormQuestions(pageNo, search);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getCurrentPage());
        assertEquals(0, response.getTotalItems());
        assertEquals(1, response.getTotalPages());
        assertTrue(response.getDataResponse().isEmpty());
    }

    @Test
    void testUpdateFormQuestion_Success() {
        // Arrange
        int id = 1;
        FormQuestionRequestDTO requestDTO = new FormQuestionRequestDTO();
        requestDTO.setQuestion("Updated question");
        requestDTO.setQuestionNumber(2);
        requestDTO.setType(TypeQuestion.SF);

        FormQuestion existingFormQuestion = FormQuestion.builder()
                .id(id)
                .question("Old question")
                .questionNumber(1)
                .type(TypeQuestion.SAT_SF)
                .build();

        FormQuestion updatedFormQuestion = FormQuestion.builder()
                .id(id)
                .question(requestDTO.getQuestion())
                .questionNumber(requestDTO.getQuestionNumber())
                .type(requestDTO.getType())
                .build();

        when(formQuestionRepository.findById(id)).thenReturn(Optional.of(existingFormQuestion));
        when(formQuestionRepository.save(any(FormQuestion.class))).thenReturn(updatedFormQuestion);

        // Act
        FormQuestionResponseDTO responseDTO = formQuestionService.updateFormQuestion(id, requestDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(updatedFormQuestion.getId(), responseDTO.getId());
        assertEquals(updatedFormQuestion.getQuestion(), responseDTO.getQuestion());
        assertEquals(updatedFormQuestion.getQuestionNumber(), responseDTO.getQuestionNumber());
    }

    @Test
    void testUpdateFormQuestion_NotFound() {
        // Arrange
        int id = 1;
        FormQuestionRequestDTO requestDTO = new FormQuestionRequestDTO();
        requestDTO.setQuestion("Updated question");
        requestDTO.setQuestionNumber(2);
        requestDTO.setType(TypeQuestion.SF);

        when(formQuestionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> formQuestionService.updateFormQuestion(id, requestDTO));
    }

    @Test
    void testDeleteFormQuestion_Success() {
        // Arrange
        int id = 1;
        FormQuestion existingFormQuestion = FormQuestion.builder()
                .id(id)
                .question("Test question")
                .questionNumber(1)
                .type(TypeQuestion.SAT_SF)
                .build();

        when(formQuestionRepository.findById(id)).thenReturn(Optional.of(existingFormQuestion));

        // Act
        FormQuestionResponseDTO responseDTO = formQuestionService.deleteFormQuestion(id);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(existingFormQuestion.getId(), responseDTO.getId());
        assertEquals(existingFormQuestion.getQuestion(), responseDTO.getQuestion());
        assertEquals(existingFormQuestion.getQuestionNumber(), responseDTO.getQuestionNumber());
    }

    @Test
    void testDeleteFormQuestion_NotFound() {
        // Arrange
        int id = 1;
        when(formQuestionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> formQuestionService.deleteFormQuestion(id));
    }

    @Test
    void testGetAllFormQuestionsMobile() {
        // Arrange
        List<FormQuestion> formQuestions = new ArrayList<>();
        formQuestions.add(FormQuestion.builder().id(1).question("Question 1").questionNumber(1).type(TypeQuestion.SAT_SF).build());
        formQuestions.add(FormQuestion.builder().id(2).question("Question 2").questionNumber(2).type(TypeQuestion.SF).build());

        when(formQuestionRepository.findAll()).thenReturn(formQuestions);

        // Act
        List<FormQuestionResponseDTO> responseDTOS = formQuestionService.getAllFormQuestionsMobile();

        // Assert
        assertNotNull(responseDTOS);
        assertEquals(formQuestions.size(), responseDTOS.size());
        assertEquals(formQuestions.get(0).getId(), responseDTOS.get(0).getId());
        assertEquals(formQuestions.get(1).getId(), responseDTOS.get(1).getId());
    }
    @Test
    void testGetFormQuestionEntityById_Success() {
        // Arrange
        int id = 1;
        FormQuestion formQuestion = FormQuestion.builder()
                .id(id)
                .question("Test question")
                .questionNumber(1)
                .type(TypeQuestion.SAT_SF)
                .build();

        when(formQuestionRepository.findById(id)).thenReturn(Optional.of(formQuestion));

        // Act
        FormQuestion result = formQuestionService.getFormQuestionEntityById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Test question", result.getQuestion());
        assertEquals(1, result.getQuestionNumber());
    }

    @Test
    void testGetFormQuestionEntityById_NotFound() {
        // Arrange
        int id = 1;
        when(formQuestionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> formQuestionService.getFormQuestionEntityById(id));
    }
    @Test
    void testGetAllFormQuestionsMobile_EmptyList() {
        // Arrange
        List<FormQuestion> formQuestions = new ArrayList<>();
        when(formQuestionRepository.findAll()).thenReturn(formQuestions);

        // Act
        List<FormQuestionResponseDTO> responseDTOS = formQuestionService.getAllFormQuestionsMobile();

        // Assert
        assertNotNull(responseDTOS);
        assertTrue(responseDTOS.isEmpty());
    }

    // Add more tests for other methods as needed...

}
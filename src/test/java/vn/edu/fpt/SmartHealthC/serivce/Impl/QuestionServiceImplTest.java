package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNotification;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AnswerQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.QuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.QuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.QuestionRepository;
import vn.edu.fpt.SmartHealthC.repository.WebUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AppUserService appUserService;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private WebUserRepository webUserRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private Account account;
    private AppUser testAppUser;
    private WebUser testWebUser;
    @BeforeEach
    void setUp() {
        account = Account.builder()
                .Id(1)
                .email("john.doe@example.com")
                .password("encoded_password")
                .type(TypeAccount.USER)
                .isActive(true)
                .isDeleted(false)
                .build();
        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");
        // Khởi tạo WebUser mock
        testWebUser = new WebUser();
        testWebUser.setId(1);
        testWebUser.setUserName("Test Doctor");
        testAppUser.setAccountId(account);

    }
    @Test
    void testRemoveAnswer_ValidId() {
        // Arrange
        AppUser appUser = new AppUser();
        appUser.setName("App User");

        Question question = new Question();
        question.setId(1);
        question.setAppUserId(appUser);
        question.setTitle("Title 1");
        question.setBody("Body 1");
        question.setAnswer("Answer 1");

        when(questionRepository.findById(1)).thenReturn(Optional.of(question));
        when(questionRepository.save(any(Question.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        QuestionResponseDTO result = questionService.removeAnswer(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("App User", result.getAppUserName());
        assertEquals("Title 1", result.getTitle());
        assertEquals("Body 1", result.getBody());
        assertEquals("", result.getAnswer());
        assertEquals("", result.getWebUserName());
    }

    @Test
    void testRemoveAnswer_InvalidId() {
        // Arrange
        when(questionRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> questionService.removeAnswer(1));
    }


    @Test
    void testGetQuestionByAppUserId_NoQuestions() {
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");
        // Arrange
        when(appUserRepository.findByAccountEmail(any(String.class))).thenReturn(Optional.of(testAppUser));
        when(appUserService.findAppUserByEmail(any(String.class))).thenReturn(testAppUser);
        when(questionRepository.findByUserId(1)).thenReturn(Collections.emptyList());


        // Act
        List<QuestionResponseDTO> result = questionService.getQuestionByAppUserId();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    void testGetQuestionsByType_NoMatchingTypeQuestions() {
        // Arrange
        TypeUserQuestion typeUserQuestion = TypeUserQuestion.ASSIGN_MS;
        TypeUserQuestion differentType = TypeUserQuestion.ASSIGN_ADMIN;
        AppUser appUser = new AppUser();
        appUser.setName("App User");

        Question question1 = new Question();
        question1.setId(1);
        question1.setAppUserId(appUser);
        question1.setTitle("Title 1");
        question1.setBody("Body 1");
        question1.setTypeUserQuestion(differentType);
        question1.setAnswer("Answer 1");

        List<Question> questionList = List.of(question1);
        when(questionRepository.findAll()).thenReturn(questionList);

        // Act
        List<QuestionResponseDTO> result = questionService.getQuestionsByType(typeUserQuestion, "123");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void testGetAllPendingQuestionsByType_WithPendingQuestions() {
        // Arrange
        TypeUserQuestion typeUserQuestion = TypeUserQuestion.ASSIGN_ADMIN;
        AppUser appUser = new AppUser();
        appUser.setName("App User");

        Question question1 = new Question();
        question1.setId(1);
        question1.setAppUserId(appUser);
        question1.setTitle("Title 1");
        question1.setBody("Body 1");
        question1.setTypeUserQuestion(typeUserQuestion);
        question1.setAnswer(""); // Pending question

        List<Question> questionList = List.of(question1);
        when(questionRepository.findAll()).thenReturn(questionList);

        // Act
        List<QuestionResponseDTO> result = questionService.getAllPendingQuestionsByType(typeUserQuestion);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("App User", result.get(0).getAppUserName());
        assertEquals("Title 1", result.get(0).getTitle());
        assertEquals("Body 1", result.get(0).getBody());
        assertEquals("", result.get(0).getAnswer());
    }

    @Test
    void testGetAllQuestionsByType_WithMatchingTypeQuestions() {
        // Arrange
        TypeUserQuestion typeUserQuestion = TypeUserQuestion.ASSIGN_ADMIN;
        AppUser appUser = new AppUser();
        appUser.setName("App User");

        Question question1 = new Question();
        question1.setId(1);
        question1.setAppUserId(appUser);
        question1.setTitle("Title 1");
        question1.setBody("Body 1");
        question1.setTypeUserQuestion(typeUserQuestion);
        question1.setAnswer(""); // Pending question

        List<Question> questionList = List.of(question1);
        when(questionRepository.findAll()).thenReturn(questionList);

        // Act
        List<QuestionResponseDTO> result = questionService.getAllQuestionsByType(typeUserQuestion);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("App User", result.get(0).getAppUserName());
        assertEquals("Title 1", result.get(0).getTitle());
        assertEquals("Body 1", result.get(0).getBody());
        assertEquals("", result.get(0).getAnswer());
    }

    @Test
    void testGetAllQuestionsByType_NoMatchingTypeQuestions() {
        // Arrange
        TypeUserQuestion typeUserQuestion = TypeUserQuestion.ASSIGN_ADMIN;
        TypeUserQuestion differentType = TypeUserQuestion.ASSIGN_MS;
        AppUser appUser = new AppUser();
        appUser.setName("App User");

        Question question1 = new Question();
        question1.setId(1);
        question1.setAppUserId(appUser);
        question1.setTitle("Title 1");
        question1.setBody("Body 1");
        question1.setTypeUserQuestion(differentType); // Different type
        question1.setAnswer(""); // Pending question

        List<Question> questionList = List.of(question1);
        when(questionRepository.findAll()).thenReturn(questionList);

        // Act
        List<QuestionResponseDTO> result = questionService.getAllQuestionsByType(typeUserQuestion);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void testGetAllPendingQuestionsByType_NoPendingQuestions() {
        // Arrange
        TypeUserQuestion typeUserQuestion = TypeUserQuestion.ASSIGN_ADMIN;
        AppUser appUser = new AppUser();
        appUser.setName("App User");

        Question question1 = new Question();
        question1.setId(1);
        question1.setAppUserId(appUser);
        question1.setTitle("Title 1");
        question1.setBody("Body 1");
        question1.setTypeUserQuestion(typeUserQuestion);
        question1.setAnswer("Answered"); // Not a pending question

        List<Question> questionList = List.of(question1);
        when(questionRepository.findAll()).thenReturn(questionList);

        // Act
        List<QuestionResponseDTO> result = questionService.getAllPendingQuestionsByType(typeUserQuestion);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllQuestions() {
        // Arrange
        Question question1 = new Question();
        question1.setId(1);
        Question question2 = new Question();
        question2.setId(2);
        when(questionRepository.findAll()).thenReturn(Arrays.asList(question1, question2));

        // Act
        List<Question> result = questionService.getAllQuestions();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    void testGetAllQuestions_Empty() {
        // Arrange
        when(questionRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Question> result = questionService.getAllQuestions();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }



    @Test
    void testUpdateQuestion_InvalidWebUserId() {
        // Arrange
        Question question = new Question();
        question.setId(1);
        when(questionRepository.findById(1)).thenReturn(Optional.of(question));

        AnswerQuestionRequestDTO answerRequest = new AnswerQuestionRequestDTO();
        answerRequest.setAnswer("Test Answer");
        answerRequest.setWebUserId(999);

        when(webUserRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> questionService.updateQuestion(1, answerRequest));

        assertEquals(ErrorCode.WEB_USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testDeleteQuestion_ValidId() {
        // Arrange
        Question question = new Question();
        question.setId(1);
        AppUser appUser = new AppUser();
        appUser.setName("App User");
        question.setAppUserId(appUser);
        question.setAnswer("");  // Set answer to an empty string to avoid NullPointerException

        when(questionRepository.findById(1)).thenReturn(Optional.of(question));

        // Act
        QuestionResponseDTO result = questionService.deleteQuestion(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("App User", result.getAppUserName());
        verify(questionRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteQuestion_InvalidId() {
        // Arrange
        when(questionRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> questionService.deleteQuestion(999));

        assertEquals(ErrorCode.QUESTION_NOT_FOUND, exception.getErrorCode());
    }
    @Test
    void testGetQuestionByIdEntity_ValidId() {
        // Arrange
        Question question = new Question();
        question.setId(1);
        when(questionRepository.findById(1)).thenReturn(Optional.of(question));

        // Act
        Question result = questionService.getQuestionByIdEntity(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetQuestionByIdEntity_QuestionNotFound() {
        // Arrange
        when(questionRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> questionService.getQuestionByIdEntity(999));

        assertEquals(ErrorCode.QUESTION_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testGetQuestionById_ValidId() {
        // Arrange
        AppUser appUser = new AppUser();
        appUser.setName("Test User");
        WebUser webUser = new WebUser();
        webUser.setUserName("Test Web User");

        Question question = new Question();
        question.setId(1);
        question.setAppUserId(appUser);
        question.setWebUserId(webUser);
        question.setTitle("Test Title");
        question.setBody("Test Body");
        question.setAnswer("Test Answer");
        question.setQuestionDate(new Date());
        question.setAnswerDate(new Date());

        when(questionRepository.findById(1)).thenReturn(Optional.of(question));

        // Act
        QuestionResponseDTO result = questionService.getQuestionById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test User", result.getAppUserName());
        assertEquals("Test Web User", result.getWebUserName());
        assertEquals("Test Title", result.getTitle());
        assertEquals("Test Body", result.getBody());
        assertEquals("Test Answer", result.getAnswer());
        assertNotNull(result.getQuestionDate());
        assertNotNull(result.getAnswerDate());
    }

    @Test
    void testGetQuestionById_QuestionNotFound() {
        // Arrange
        when(questionRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> questionService.getQuestionById(999));

        assertEquals(ErrorCode.QUESTION_NOT_FOUND, exception.getErrorCode());
    }
    @Test
    void testCreateQuestion_ValidQuestionRequestDTO() {
        // Arrange
        QuestionRequestDTO questionRequestDTO = new QuestionRequestDTO();
        questionRequestDTO.setTitle("Test Title");
        questionRequestDTO.setBody("Test Body");
        questionRequestDTO.setTypeUserQuestion(TypeUserQuestion.ASSIGN_ADMIN);
//        questionRequestDTO.setAppUserId(1);

        AppUser appUser = new AppUser();
        appUser.setId(1);
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");
        // Arrange
        when(appUserRepository.findByAccountEmail(any(String.class))).thenReturn(Optional.of(testAppUser));
        when(appUserService.findAppUserByEmail(any(String.class))).thenReturn(testAppUser);

        when(questionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        QuestionResponseDTO responseDTO = questionService.createQuestion(questionRequestDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals("Test Title", responseDTO.getTitle());
        assertEquals("Test Body", responseDTO.getBody());
        assertNotNull(responseDTO.getQuestionDate());
    }

    @Test
    void testCreateQuestion_AppUserNotFound() {
        // Arrange
        QuestionRequestDTO questionRequestDTO = new QuestionRequestDTO();
//        questionRequestDTO.setAppUserId(999); // Non-existent AppUser ID

        AppUser appUser = new AppUser();
        appUser.setId(1);
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");
        // Arrange
        when(appUserRepository.findByAccountEmail(any(String.class))).thenReturn(Optional.empty());
        when(appUserService.findAppUserByEmail(any(String.class))).thenThrow(new AppException(ErrorCode.APP_USER_NOT_FOUND));;

        // Act & Assert
        AppException exception = assertThrows(AppException.class,
                () -> questionService.createQuestion(questionRequestDTO));

        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

    }
}
package vn.edu.fpt.SmartHealthC.serivce.Impl;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNotification;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AnswerQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.QuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.DeviceNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.response.QuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.*;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.NotificationService;
import vn.edu.fpt.SmartHealthC.serivce.QuestionService;

import java.util.*;
import java.util.concurrent.ExecutionException;
@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private WebUserRepository webUserRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private NotificationService notificationService;


    @Override
    public QuestionResponseDTO createQuestion(QuestionRequestDTO questionRequestDTO) {
        Question question = Question.builder()
                .title(questionRequestDTO.getTitle())
                .body(questionRequestDTO.getBody())
                .typeUserQuestion(questionRequestDTO.getTypeUserQuestion())
                .questionDate(new Date())
                .answer("")
                .answerDate(null)
                .build();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        AppUser appUser = appUserService.findAppUserByEmail(email);
        question.setAppUserId(appUser);
        question = questionRepository.save(question);
        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.getId());
        dto.setAppUserName(question.getAppUserId().getName());
        dto.setTitle(question.getTitle());
        dto.setBody(question.getBody());
        dto.setAnswer("");
        dto.setQuestionDate(question.getQuestionDate());
        return dto;
    }

    @Override
    public Question getQuestionByIdEntity(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isEmpty()) {
            throw new AppException(ErrorCode.QUESTION_NOT_FOUND);
        }
        return question.get();
    }

    @Override
    public QuestionResponseDTO getQuestionById(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isEmpty()) {
            throw new AppException(ErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.get().getId());
        dto.setAppUserName(question.get().getAppUserId().getName());
        if (!question.get().getAnswer().isBlank()) {
            dto.setWebUserName(question.get().getWebUserId().getUserName());
        }
        dto.setTitle(question.get().getTitle());
        dto.setBody(question.get().getBody());
        dto.setAnswer(question.get().getAnswer());
        dto.setQuestionDate(question.get().getQuestionDate());
        dto.setAnswerDate(question.get().getAnswerDate());
        if (!question.get().getAnswer().isEmpty()) {
            dto.setAnswerDate(question.get().getAnswerDate());
        }
        return dto;
    }


    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public QuestionResponseDTO updateQuestion(Integer id, AnswerQuestionRequestDTO answer) {
        Question question = getQuestionByIdEntity(id);
        if (answer == null || answer.getAnswer().isEmpty()) {
            throw new AppException(ErrorCode.NULL_ANSWER);
        }
        Optional<WebUser> webUser = webUserRepository.findById(answer.getWebUserId());
        if (webUser.isEmpty()) {
            throw new AppException(ErrorCode.WEB_USER_NOT_FOUND);
        }
        question.setWebUserId(webUser.get());
        question.setAnswer(answer.getAnswer());
        question.setAnswerDate(new Date());
        question = questionRepository.save(question);
        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.getId());
        dto.setAppUserName(question.getAppUserId().getName());
        if (!question.getAnswer().isBlank()) {
            dto.setWebUserName(question.getWebUserId().getUserName());
        } else {
            dto.setWebUserName("");
        }
        dto.setTitle(question.getTitle());
        dto.setBody(question.getBody());
        dto.setAnswer(question.getAnswer());
        dto.setQuestionDate(question.getQuestionDate());
        dto.setAnswerDate(question.getAnswerDate());
        //send notification to user
        Account account = question.getAppUserId().getAccountId();
        Map<String, String> data = new HashMap<>();
        Question finalQuestion = question;
        NotificationSetting notificationSetting = notificationService.findByAccountIdAndType(account.getId(), TypeNotification.QUESTION_NOTIFICATION);
        if (notificationSetting.isStatus()) {
            refreshTokenRepository.findRecordByAccountId(account.getId()).forEach(refreshToken -> {
                try {
                    notificationService.sendNotificationToDevice(DeviceNotificationRequest.builder()
                            .deviceToken(refreshToken.getDeviceToken())
                            .title("Question Answered")
                            .body("Your question about " + finalQuestion.getTitle() + "has been answered")
                            .data(data)
                            .build());
                } catch (FirebaseMessagingException e) {
//                throw new RuntimeException(e);
                    log.error("RuntimeException", e);
                } catch (ExecutionException e) {
                    log.error("ExecutionException", e);
//                throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    log.error("InterruptedException", e);
//                throw new RuntimeException(e);
                }
            });
        }
        return dto;
    }

    @Override
    public QuestionResponseDTO deleteQuestion(Integer id) {
        Question question = getQuestionByIdEntity(id);
        questionRepository.deleteById(id);
        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.getId());
        dto.setAppUserName(question.getAppUserId().getName());
        if (!question.getAnswer().isBlank()) {
            dto.setWebUserName(question.getWebUserId().getUserName());
        }
        dto.setTitle(question.getTitle());
        dto.setBody(question.getBody());
        dto.setAnswer(question.getAnswer());
        dto.setQuestionDate(question.getQuestionDate());
        dto.setAnswerDate(question.getAnswerDate());
        if (!question.getAnswer().isEmpty()) {
            dto.setAnswerDate(question.getAnswerDate());
        }

        return dto;
    }

    @Override
    public List<QuestionResponseDTO> getAllPendingQuestionsByType(TypeUserQuestion typeUserQuestion) {
        List<Question> questionList = getAllQuestions();
        List<QuestionResponseDTO> responseDTOList = questionList.stream()
                .filter(question -> (question.getAnswer() == null || question.getAnswer().isEmpty()) &&
                        question.getTypeUserQuestion() == typeUserQuestion)
                .map(question -> {
                    QuestionResponseDTO dto = new QuestionResponseDTO();
                    dto.setId(question.getId());
                    dto.setAppUserName(question.getAppUserId().getName());
                    if (!question.getAnswer().isBlank()) {
                        dto.setWebUserName(question.getWebUserId().getUserName());
                    } else {
                        dto.setWebUserName("");
                    }
                    dto.setTitle(question.getTitle());
                    dto.setBody(question.getBody());
                    dto.setAnswer(question.getAnswer());
                    dto.setQuestionDate(question.getQuestionDate());
                    if (!question.getAnswer().isBlank()) {
                        dto.setAnswerDate(question.getAnswerDate());
                    }
                    return dto;
                })
                .toList();
        return responseDTOList;
    }


    @Override
    public List<QuestionResponseDTO> getAllQuestionsByType(TypeUserQuestion typeUserQuestion) {
        List<Question> questionList = getAllQuestions();
        List<QuestionResponseDTO> responseDTOList = questionList.stream()
                .filter(question -> (question.getAnswer() == null || question.getAnswer().isEmpty()) &&
                        question.getTypeUserQuestion() == typeUserQuestion)
                .map(question -> {
                    QuestionResponseDTO dto = new QuestionResponseDTO();
                    dto.setId(question.getId());
                    dto.setAppUserName(question.getAppUserId().getName());
                    if (!question.getAnswer().isBlank()) {
                        dto.setWebUserName(question.getWebUserId().getUserName());
                    } else {
                        dto.setWebUserName("");
                    }
                    dto.setTitle(question.getTitle());
                    dto.setBody(question.getBody());
                    dto.setAnswer(question.getAnswer());
                    dto.setQuestionDate(question.getQuestionDate());
                    if (!question.getAnswer().isBlank()) {
                        dto.setAnswerDate(question.getAnswerDate());
                    }
                    return dto;
                })
                .toList();
        return responseDTOList;
    }

    @Override
    public List<QuestionResponseDTO> getQuestionsByType(TypeUserQuestion typeUserQuestion) {
        List<Question> questionList = getAllQuestions();
        List<QuestionResponseDTO> responseDTOList = questionList.stream()
                .filter(question ->
                        question.getTypeUserQuestion() == typeUserQuestion)
                .map(question -> {
                    QuestionResponseDTO dto = new QuestionResponseDTO();
                    dto.setId(question.getId());
                    dto.setAppUserName(question.getAppUserId().getName());
                    if (!question.getAnswer().isBlank()) {
                        dto.setWebUserName(question.getWebUserId().getUserName());
                    } else {
                        dto.setWebUserName("");
                    }
                    dto.setTitle(question.getTitle());
                    dto.setBody(question.getBody());
                    dto.setAnswer(question.getAnswer());
                    dto.setAnswer(question.getAnswer());
                    dto.setQuestionDate(question.getQuestionDate());
                    if (!question.getAnswer().isBlank()) {
                        dto.setAnswerDate(question.getAnswerDate());
                    }
                    return dto;
                })
                .toList();
        return responseDTOList;
    }

    @Override
    public QuestionResponseDTO removeAnswer(Integer id) {
        Question question = getQuestionByIdEntity(id);
        question.setWebUserId(null);
        question.setAnswer("");
        question.setAnswerDate(null);
        question = questionRepository.save(question);
        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.getId());
        dto.setAppUserName(question.getAppUserId().getName());
        if (!question.getAnswer().isBlank()) {
            dto.setWebUserName(question.getWebUserId().getUserName());
        } else {
            dto.setWebUserName("");
        }
        dto.setTitle(question.getTitle());
        dto.setBody(question.getBody());
        dto.setAnswer(question.getAnswer());
        dto.setQuestionDate(question.getQuestionDate());
        dto.setAnswerDate(question.getAnswerDate());
        return dto;
    }

    @Override
    public List<QuestionResponseDTO> getQuestionByAppUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AppUser appUser = appUserService.findAppUserByEmail(email);
        List<Question> questionList = questionRepository.findByUserId(appUser.getId());
        List<QuestionResponseDTO> questionResponseDTOList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionResponseDTO dto = new QuestionResponseDTO();
            dto.setId(question.getId());
            dto.setAppUserName(question.getAppUserId().getName());
            if (question.getAnswer() != null  && !question.getAnswer().isBlank()) {
                dto.setWebUserName(question.getWebUserId().getUserName());
            }
            dto.setTitle(question.getTitle());
            dto.setBody(question.getBody());
            dto.setAnswer(question.getAnswer());
            dto.setQuestionDate(question.getQuestionDate());
            dto.setAnswerDate(question.getAnswerDate());
            if (!question.getAnswer().isEmpty()) {
                dto.setAnswerDate(question.getAnswerDate());
            }
            questionResponseDTOList.add(dto);
        }
        return questionResponseDTOList;
    }
}
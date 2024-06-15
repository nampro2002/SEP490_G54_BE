package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import vn.edu.fpt.SmartHealthC.serivce.QuestionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private WebUserRepository webUserRepository;

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
        Optional<AppUser> appUser = appUserRepository.findById(questionRequestDTO.getAppUserId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        question.setAppUserId(appUser.get());
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
        }else{
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
                    }else{
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
                    }else{
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
                    }else{
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
        }else{
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
    public List<QuestionResponseDTO> getQuestionByAppUserId(Integer userId) {
        List<Question> questionList = questionRepository.findByUserId(userId);
        List<QuestionResponseDTO> questionResponseDTOList = new ArrayList<>();
        for (Question question : questionList) {
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
            questionResponseDTOList.add(dto);
        }
        return questionResponseDTOList;
    }
}
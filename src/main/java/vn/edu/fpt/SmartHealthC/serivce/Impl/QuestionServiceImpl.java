package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.QuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.QuestionRepository;
import vn.edu.fpt.SmartHealthC.repository.WebUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.QuestionService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

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
    public Question createQuestion(QuestionDTO questionDTO)
    {
        Question question =  Question.builder()
                .title(questionDTO.getTitle())
                .body(questionDTO.getBody())
                .answer(questionDTO.getAnswer()).build();
        Optional<AppUser> appUser = appUserRepository.findById(questionDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        question.setAppUserId(appUser.get());
        Optional<WebUser> webUser = webUserRepository.findById(questionDTO.getAppUserId());
        if(webUser.isEmpty()) {
            throw new AppException(ErrorCode.WEB_USER_NOT_FOUND);
        }
        question.setWebUserId(webUser.get());
        return  questionRepository.save(question);
    }

    @Override
    public Question getQuestionById(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        if(question.isEmpty()) {
            throw new AppException(ErrorCode.QUESTION_NOT_FOUND);
        }
        return question.get();
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question updateQuestion(Integer id,QuestionDTO questionDTO) {
        Question question = getQuestionById(id);
        question.setTitle(questionDTO.getTitle());
        question.setBody(questionDTO.getBody());
        question.setAnswer(questionDTO.getAnswer());
        return  questionRepository.save(question);
    }

    @Override
    public Question deleteQuestion(Integer id) {
        Question question  = getQuestionById(id);
        questionRepository.deleteById(id);
        return question;
    }


}
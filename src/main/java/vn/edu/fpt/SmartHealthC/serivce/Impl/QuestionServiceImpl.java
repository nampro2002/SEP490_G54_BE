package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.QuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.Question;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
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
        AppUser appUser = appUserRepository.findById(questionDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));
        WebUser webUser = webUserRepository.findById(questionDTO.getWebUserId())
                .orElseThrow(() -> new IllegalArgumentException("WebUser not found"));

        question.setAppUserId(appUser);
        question.setWebUserId(webUser);
        return  questionRepository.save(question);
    }

    @Override
    public Optional<Question> getQuestionById(Integer id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question updateQuestion(QuestionDTO questionDTO) {
        Question question =  Question.builder()
                .Id(questionDTO.getId())
                .title(questionDTO.getTitle())
                .body(questionDTO.getBody())
                .answer(questionDTO.getAnswer()).build();
        AppUser appUser = appUserRepository.findById(questionDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));
        WebUser webUser = webUserRepository.findById(questionDTO.getWebUserId())
                .orElseThrow(() -> new IllegalArgumentException("WebUser not found"));

        question.setAppUserId(appUser);
        question.setWebUserId(webUser);
        return  questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Integer id) {
        questionRepository.deleteById(id);
    }


}
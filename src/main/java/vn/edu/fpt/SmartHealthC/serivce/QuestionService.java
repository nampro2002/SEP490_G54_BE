package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.QuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question createQuestion(QuestionDTO questionDTO);
    Optional<Question> getQuestionById(Integer id);
    List<Question> getAllQuestions();
    Question updateQuestion(QuestionDTO questionDTO);
    void deleteQuestion(Integer id);
}
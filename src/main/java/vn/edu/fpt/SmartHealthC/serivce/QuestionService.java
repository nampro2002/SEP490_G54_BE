package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;
import vn.edu.fpt.SmartHealthC.domain.dto.request.QuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.QuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question createQuestion(QuestionDTO questionDTO);
    Question getQuestionById(Integer id);
    List<Question> getAllQuestions();
    Question updateQuestion(Integer id,QuestionDTO questionDTO);
    Question deleteQuestion(Integer id);
    List<QuestionResponseDTO> getAllQuestionsByType(TypeUserQuestion typeUserQuestion);
}
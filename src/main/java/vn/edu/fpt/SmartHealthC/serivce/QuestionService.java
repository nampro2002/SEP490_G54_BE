package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AnswerQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.QuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.QuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    QuestionResponseDTO createQuestion(QuestionDTO questionDTO);
    QuestionResponseDTO getQuestionById(Integer id);
    Question getQuestionByIdEntity(Integer id);
    List<Question> getAllQuestions();
    QuestionResponseDTO updateQuestion(Integer id, AnswerQuestionRequestDTO answer);
    QuestionResponseDTO deleteQuestion(Integer id);
    List<QuestionResponseDTO> getAllPendingQuestionsByType(TypeUserQuestion typeUserQuestion);
    List<QuestionResponseDTO> getAllQuestionsByType(TypeUserQuestion typeUserQuestion);
    List<QuestionResponseDTO> getQuestionsByType(TypeUserQuestion typeUserQuestion);

    QuestionResponseDTO removeAnswer(Integer id);

    List<QuestionResponseDTO> getQuestionByUserId(Integer userId);
}
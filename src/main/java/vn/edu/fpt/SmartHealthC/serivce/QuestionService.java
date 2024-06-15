package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AnswerQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.QuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.QuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Question;

import java.util.List;

public interface QuestionService {
    QuestionResponseDTO createQuestion(QuestionRequestDTO questionRequestDTO);
    QuestionResponseDTO getQuestionById(Integer id);
    Question getQuestionByIdEntity(Integer id);
    List<Question> getAllQuestions();
    QuestionResponseDTO updateQuestion(Integer id, AnswerQuestionRequestDTO answer);
    QuestionResponseDTO deleteQuestion(Integer id);
    List<QuestionResponseDTO> getAllPendingQuestionsByType(TypeUserQuestion typeUserQuestion);
    List<QuestionResponseDTO> getAllQuestionsByType(TypeUserQuestion typeUserQuestion);
    List<QuestionResponseDTO> getQuestionsByType(TypeUserQuestion typeUserQuestion);

    QuestionResponseDTO removeAnswer(Integer id);

    List<QuestionResponseDTO> getQuestionByAppUserId(Integer userId);
}
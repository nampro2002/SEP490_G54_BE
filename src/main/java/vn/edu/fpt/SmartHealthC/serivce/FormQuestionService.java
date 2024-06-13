package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.FormQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;

import java.util.List;
import java.util.Optional;

public interface FormQuestionService {
    FormQuestion createFormQuestion(FormQuestionRequestDTO formQuestion);
    FormQuestion  getFormQuestionById(Integer id);
    List<FormQuestion> getAllFormQuestions();
    FormQuestion updateFormQuestion(Integer id,FormQuestionRequestDTO formQuestion);
    FormQuestion deleteFormQuestion(Integer id);
}

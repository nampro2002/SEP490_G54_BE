package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;

import java.util.List;
import java.util.Optional;

public interface FormQuestionService {
    FormQuestion createFormQuestion(FormQuestion formQuestion);
    Optional<FormQuestion> getFormQuestionById(Integer id);
    List<FormQuestion> getAllFormQuestions();
    FormQuestion updateFormQuestion(FormQuestion formQuestion);
    void deleteFormQuestion(Integer id);
}

package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyQuestion;

import java.util.List;
import java.util.Optional;

public interface MonthlyQuestionService {
    MonthlyQuestion createMonthlyQuestion(MonthlyQuestionDTO monthlyQuestionDTO);
    Optional<MonthlyQuestion> getMonthlyQuestionById(Integer id);
    List<MonthlyQuestion> getAllMonthlyQuestions();
    MonthlyQuestion updateMonthlyQuestion(MonthlyQuestionDTO monthlyQuestionDTO);
    void deleteMonthlyQuestion(Integer id);
}
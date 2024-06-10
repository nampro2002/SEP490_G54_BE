package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyQuestion;

import java.util.List;
import java.util.Optional;

public interface MonthlyQuestionService {
    MonthlyQuestion createMonthlyQuestion(MonthlyQuestionDTO monthlyQuestionDTO);
   MonthlyQuestion getMonthlyQuestionById(Integer id);
    List<MonthlyQuestion> getAllMonthlyQuestions();
    MonthlyQuestion updateMonthlyQuestion(Integer id,MonthlyQuestionDTO monthlyQuestionDTO);
    MonthlyQuestion deleteMonthlyQuestion(Integer id);
}
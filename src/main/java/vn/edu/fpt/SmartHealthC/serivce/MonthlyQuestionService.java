package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyRecord;

import java.util.List;

public interface MonthlyQuestionService {
    MonthlyRecord createMonthlyQuestion(MonthlyQuestionDTO monthlyQuestionDTO);
   MonthlyRecord getMonthlyQuestionById(Integer id);
    List<MonthlyRecord> getAllMonthlyQuestionsMobile();
    MonthlyRecord updateMonthlyQuestion(Integer id, MonthlyQuestionDTO monthlyQuestionDTO);
    MonthlyRecord deleteMonthlyQuestion(Integer id);
}
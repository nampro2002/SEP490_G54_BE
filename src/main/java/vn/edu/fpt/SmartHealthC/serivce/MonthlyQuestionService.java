package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyAnswerResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyNumberResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyRecord;

import java.util.List;

public interface MonthlyQuestionService {
    void createNewMonthMark(int appUserId);

    void create40MonthlyQuestion(List<MonthlyQuestionDTO> monthlyQuestionDTO);

    List<MonthlyNumberResponseDTO> getList3MonthlyNumber();


    List<MonthlyAnswerResponseDTO> getWebListAnswer(int userId, int monthNumber);

    List<MonthlyAnswerResponseDTO> getMobileListAnswer(int monthNumber);
}
package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyAnswerResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyNumberResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyStatisticResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyRecord;

import java.util.List;

public interface MonthlyQuestionService {
    void createNewMonthMark(int appUserId);

    void createAnswers(List<MonthlyQuestionDTO> monthlyQuestionDTO);

    List<MonthlyNumberResponseDTO> getList3MonthlyNumber();


    List<MonthlyAnswerResponseDTO> getWebListAnswer(int userId, int monthNumber);

    List<MonthlyAnswerResponseDTO> getMobileListAnswer(int monthNumber);

    MonthlyStatisticResponseDTO getPoint(Integer monthNumber);



    List<MonthlyStatisticResponseDTO> getPoint3MonthMobile();

    List<Integer> getList3MonthlyNumberWeb(Integer appUserId);


    List<MonthlyStatisticResponseDTO> getPoint2MonthMobile(Integer monthNumber);

    List<MonthlyStatisticResponseDTO> getPoint2MonthWeb(Integer appUserId, Integer monthNumber);

    List<MonthlyStatisticResponseDTO> getPoint12MonthWeb(Integer appUserId);
}
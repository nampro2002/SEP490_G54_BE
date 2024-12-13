package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MobileGeneralChartResponseDTO;
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

    List<Integer> getList3MonthlyNumberWeb(Integer appUserId);

    List<MonthlyAnswerResponseDTO> getMobileListAnswer(int monthNumber, String type, TypeLanguage language);


    List<MonthlyAnswerResponseDTO> getWebListAnswer(int userId, int monthNumber,String type);

    MobileGeneralChartResponseDTO getPoint3MonthMobile();

    MonthlyStatisticResponseDTO getPoint(Integer monthNumber, Integer appUser);





    List<MonthlyStatisticResponseDTO> getPoint2MonthMobile(Integer monthNumber);

    List<MonthlyStatisticResponseDTO> getPoint2MonthWeb(Integer appUserId, Integer monthNumber);

    List<MonthlyStatisticResponseDTO> getPoint12MonthWeb(Integer appUserId);

}
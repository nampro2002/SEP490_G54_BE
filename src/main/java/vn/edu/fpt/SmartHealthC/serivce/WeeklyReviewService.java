package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.WeeklyReviewResponseDTO;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface WeeklyReviewService {
    WeeklyReviewResponseDTO getWeekDate(Integer id) throws ParseException;

    List<Date> getListWeekStart(Integer id) throws ParseException;

    WeeklyReviewResponseDTO getDataReviewForWeekDate(Integer id,String weekstart) throws ParseException;
}
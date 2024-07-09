package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.WeekCheckPlanResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.WeeklyMoblieChartResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.WeeklyReviewResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.WeekReview;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface WeeklyReviewService {
    WeekReview getWeek(Integer id) throws ParseException;

    List<Date> getListWeekStart(Integer id) throws ParseException;

//    WeeklyReviewResponseDTO getDataReviewForWeek(Integer id,String weekstart) throws ParseException;
    Date findSmallestWeekStart(AppUser appUser);
    Date findSmallestWeekStartForJob(AppUser appUser);
    WeekReview getWebDataReviewForWeek(String weekstart) throws ParseException;
    WeeklyReviewResponseDTO getMobileDataReviewForWeek(String weekstart) throws ParseException;
    void saveDataReviewForWeek(String weekStart) throws ParseException;

    WeeklyMoblieChartResponseDTO getMobileChartReviewForWeek() throws ParseException;

    List<Date> getMobileListWeekStart() throws ParseException;

    WeekCheckPlanResponseDTO checkWeeklyPlanExist(String weekStart) throws ParseException;

}
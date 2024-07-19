package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.WeekCheckPlanResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.WeeklyMoblieChartResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.WeeklyReviewResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.WeeklyReviewWebResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.WeekReview;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface WeeklyReviewService {
    WeekReview getDataOfNearestWeek(Integer id) throws ParseException;

    List<Date> getListWeekStart(Integer id) throws ParseException;


    //    WeeklyReviewResponseDTO getDataReviewForWeek(Integer id,String weekstart) throws ParseException;
    Date findSmallestWeekStart(AppUser appUser);
    Date findSmallestWeekStartForJob(AppUser appUser);
    WeeklyReviewWebResponseDTO getWebDataReviewForWeek(Integer id , String weekstart) throws ParseException;
    WeeklyReviewResponseDTO getMobileDataReviewForWeek(String weekstart) throws ParseException;
    void saveDataReviewForWeek(Integer appUserId,String weekStart) throws ParseException;

    WeeklyMoblieChartResponseDTO getMobileChartReviewForWeek() throws ParseException;

//    List<Date> allList() throws ParseException;

    WeekCheckPlanResponseDTO checkWeeklyPlanExist(String weekStart) throws ParseException;

    Integer checkWhichPlansMade(String weekStart) throws ParseException;

    List<Date> get5NearestWeekStart() throws ParseException;
}
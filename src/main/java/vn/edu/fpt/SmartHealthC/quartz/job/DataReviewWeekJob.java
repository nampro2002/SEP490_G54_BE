package vn.edu.fpt.SmartHealthC.quartz.job;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.TopicNotificationRequest;
import vn.edu.fpt.SmartHealthC.quartz.quartzService.TriggerExecutionService;
import vn.edu.fpt.SmartHealthC.serivce.NotificationService;
import vn.edu.fpt.SmartHealthC.serivce.WeeklyReviewService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DataReviewWeekJob implements Job {
    @Autowired
    private WeeklyReviewService weeklyReviewService;
    @Autowired
    private SimpleDateFormat simpleDateFormat;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Executing 7.45PM Sunday Job at: " + new Date());
        Date date = new Date();
        Date weekStart = getFirstDayOfWeek(date);
        //parse weekStart to String
        String weekStartStr = simpleDateFormat.format(weekStart);
        try {
            weeklyReviewService.saveDataReviewForWeek(weekStartStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Set the first day of the week to Monday
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // Get the current day of the week
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // Calculate the difference to Monday
        int diff = Calendar.MONDAY - dayOfWeek;
        // If the current day is Sunday, adjust the difference
        if (dayOfWeek == Calendar.SUNDAY) {
            diff = -6;
        }
        // Add the difference to the calendar date
        calendar.add(Calendar.DAY_OF_MONTH, diff);
        return calendar.getTime();
    }
}

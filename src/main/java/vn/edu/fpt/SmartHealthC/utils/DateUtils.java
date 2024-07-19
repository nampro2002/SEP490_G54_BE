package vn.edu.fpt.SmartHealthC.utils;

import com.google.type.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Component
public class DateUtils {
        public static Date getToday(SimpleDateFormat formatDate) throws ParseException {
            Date today = new Date();
            String dateStr= formatDate.format(today);
            Date date = formatDate.parse(dateStr);
            return date;
        }
    public static Date getTime() throws ParseException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Chuyển đổi chuỗi thành LocalDateTime
        String formattedDateTime = currentDateTime.format(formatter);
        LocalDateTime localDateTime = LocalDateTime.parse(formattedDateTime, formatter);
        // Chuyển đổi LocalDateTime thành Date
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return date;
    }
    public static Date normalizeDate(SimpleDateFormat formatDate,String date) throws ParseException {
        formatDate.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        return formatDate.parse(date);
    }

    public static Date getFirstDayOfWeek(Date date) {
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

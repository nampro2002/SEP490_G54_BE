package vn.edu.fpt.SmartHealthC.utils;

import com.google.type.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
}

package vn.edu.fpt.SmartHealthC.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public static Date normalizeDate(SimpleDateFormat formatDate,String date) throws ParseException {
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatDate.parse(date);
    }

}

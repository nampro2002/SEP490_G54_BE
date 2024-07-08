package vn.edu.fpt.SmartHealthC.quartz.trigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataReviewWeekTriggerConfig {

    @Bean(name = "dataWeekReviewTrigger")
    public Trigger dataWeekReviewTrigger(@Qualifier("dataReviewWeekJobDetail") JobDetail jobDetail) {
        System.out.println("execute 7.45PM sunday trigger jobBoot");
        try {
            String time = "0/10 * * * * ?";
//            String time = "0 45 19 ? * SUN";
            return TriggerBuilder.newTrigger().forJob(jobDetail)
                    .withIdentity("dataforweek", "JOB_GROUP")
                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(time))
                    .build();
        }catch (Exception e){
            System.out.println("error in trigger daily morning Job");
            e.printStackTrace();
            return null;
        }
    }

}

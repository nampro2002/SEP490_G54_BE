package vn.edu.fpt.SmartHealthC.quartz.trigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyJobTriggerConfig {
    @Bean(name = "weeklyJobTrigger")
    public Trigger weeklyJobTrigger(@Qualifier("weeklyJobDetail") JobDetail jobDetail) {
        System.out.println("execute trigger jobBoot");
        try {
            String time = "0/25 * * * * ?";
//            String time = "0 0 8 ? * MON";
            return TriggerBuilder.newTrigger().forJob(jobDetail)
                    .withIdentity("weekly_job", "JOB_GROUP")
                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(time))
                    .build();
        }catch (Exception e){
            System.out.println("error in trigger weekly Job");
            e.printStackTrace();
            return null;
        }
    }
}
package vn.edu.fpt.SmartHealthC.quartz.trigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DailyJobTriggerConfig {
    @Bean(name = "dailyJobTrigger")
    public Trigger dailyJobTrigger(@Qualifier("dailyJobDetail") JobDetail jobDetail) {
        System.out.println("execute trigger jobBoot");
        try {
            String time = "0/10 * * * * ?";
//            String time = "0 0 8 * * ?";
            return TriggerBuilder.newTrigger().forJob(jobDetail)
                    .withIdentity("daily_job", "JOB_GROUP")
                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(time))
                    .build();
        }catch (Exception e){
            System.out.println("error in trigger daily Job");
            e.printStackTrace();
            return null;
        }
    }
}

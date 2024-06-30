package vn.edu.fpt.SmartHealthC.quartz.detail;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.fpt.SmartHealthC.quartz.job.WeeklyJob;

@Configuration
public class WeeklyJobConfig {
    @Bean(name = "weeklyJobDetail")
    public JobDetail weeklyJobDetail() {
        return JobBuilder.newJob(WeeklyJob.class)
                .withIdentity("weekly_job", "JOB_GROUP")
                .storeDurably()
                .build();
    }
}

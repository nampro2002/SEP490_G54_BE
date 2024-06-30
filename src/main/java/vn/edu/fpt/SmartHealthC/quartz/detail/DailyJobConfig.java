package vn.edu.fpt.SmartHealthC.quartz.detail;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.fpt.SmartHealthC.quartz.job.DailyJob;

@Configuration
public class DailyJobConfig {
    @Bean(name = "dailyJobDetail")
    public JobDetail dailyJobDetail() {
        return JobBuilder.newJob(DailyJob.class)
                .withIdentity("dailyJob", "JOB_GROUP")
                .storeDurably()
                .build();
    }
}

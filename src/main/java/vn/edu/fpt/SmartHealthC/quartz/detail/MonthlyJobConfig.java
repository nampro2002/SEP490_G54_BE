package vn.edu.fpt.SmartHealthC.quartz.detail;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.fpt.SmartHealthC.quartz.job.MonthlyJob;

@Configuration
public class MonthlyJobConfig {
    @Bean(name = "monthlyJobDetail")
    public JobDetail monthlyJobDetail() {
        return JobBuilder.newJob(MonthlyJob.class)
                .withIdentity("monthlyJob", "JOB_GROUP")
                .storeDurably()
                .build();
    }
}

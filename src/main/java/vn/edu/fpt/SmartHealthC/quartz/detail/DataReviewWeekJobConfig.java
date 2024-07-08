package vn.edu.fpt.SmartHealthC.quartz.detail;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.fpt.SmartHealthC.quartz.job.DataReviewWeekJob;
import vn.edu.fpt.SmartHealthC.quartz.job.MonthlyJob;

@Configuration
public class DataReviewWeekJobConfig {
    @Bean(name = "dataReviewWeekJobDetail")
    public JobDetail dataReviewWeekJobDetail() {
        return JobBuilder.newJob(DataReviewWeekJob.class)
                .withIdentity("dataforweek", "JOB_GROUP")
                .storeDurably()
                .build();
    }
}

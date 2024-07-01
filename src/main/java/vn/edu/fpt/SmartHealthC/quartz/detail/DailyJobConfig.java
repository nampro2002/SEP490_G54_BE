package vn.edu.fpt.SmartHealthC.quartz.detail;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.fpt.SmartHealthC.quartz.job.DailyEveningJob;
import vn.edu.fpt.SmartHealthC.quartz.job.DailyJob;
import vn.edu.fpt.SmartHealthC.quartz.job.DailyMorningJob;

@Configuration
public class DailyJobConfig {
    @Bean(name = "dailyJobDetail")
    public JobDetail dailyJobDetail() {
        return JobBuilder.newJob(DailyJob.class)
                .withIdentity("dailyJob", "JOB_GROUP")
                .storeDurably()
                .build();
    }
    //morning & evening job
    @Bean(name = "morningJobDetail")
    public JobDetail morningJobDetail() {
        return JobBuilder.newJob(DailyMorningJob.class)
                .withIdentity("morningJob", "JOB_GROUP")
                .storeDurably()
                .build();
    }
    @Bean(name = "eveningJobDetail")
    public JobDetail eveningJobDetail() {
        return JobBuilder.newJob(DailyEveningJob.class)
                .withIdentity("eveningJob", "JOB_GROUP")
                .storeDurably()
                .build();
    }
    //sunday evening job & monday morning job
    @Bean(name = "sundayEveningJobDetail")
    public JobDetail sundayEveningJobDetail() {
        return JobBuilder.newJob(DailyEveningJob.class)
                .withIdentity("sundayEveningJob", "JOB_GROUP")
                .storeDurably()
                .build();
    }
    @Bean(name = "mondayMorningJobDetail")
    public JobDetail mondayMorningJobDetail() {
        return JobBuilder.newJob(DailyMorningJob.class)
                .withIdentity("mondayMorningJob", "JOB_GROUP")
                .storeDurably()
                .build();
    }

}

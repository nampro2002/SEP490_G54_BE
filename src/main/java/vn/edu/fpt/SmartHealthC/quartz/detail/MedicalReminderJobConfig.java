package vn.edu.fpt.SmartHealthC.quartz.detail;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.fpt.SmartHealthC.quartz.job.DailyMorningJob;
import vn.edu.fpt.SmartHealthC.quartz.job.MedicalAppointmentJob;

@Configuration
public class MedicalReminderJobConfig {
    @Bean(name = "medicalReminderJobDetail")
    public JobDetail medicalReminderJobDetail() {
        return JobBuilder.newJob(MedicalAppointmentJob.class)
                .withIdentity("remind", "JOB_GROUP")
                .storeDurably()
                .build();
    }
}

package vn.edu.fpt.SmartHealthC.quartz.trigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MedicalAppointmentTriggerConfig {
    @Bean(name = "medicalAppointmentTrigger")
    public Trigger weeklyJobTrigger(@Qualifier("medicalReminderJobDetail") JobDetail jobDetail) {
        System.out.println("execute trigger jobBoot");
        try {
            String time = "0 0 13 * * ?";
//            String time = "0/10 * * * * ?";
            return TriggerBuilder.newTrigger().forJob(jobDetail)
                    .withIdentity("remind", "JOB_GROUP")
                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(time))
                    .build();
        }catch (Exception e){
            System.out.println("error in trigger medical reminder Job");
            e.printStackTrace();
            return null;
        }
    }
}
package vn.edu.fpt.SmartHealthC.quartz.trigger;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MedicineJobTriggerConfig {

    @Bean(name = "medicineJobTrigger")
    public Trigger dataWeekReviewTrigger(@Qualifier("medicineJobDetail") JobDetail jobDetail) {
        System.out.println("execute medicine trigger jobBoot");
        try {
            //job mỗi 1 phút
//            String time = "0 0/1 * 1/1 * ? *";
//            String time = "0 45 19 ? * SUN";
            return TriggerBuilder.newTrigger().forJob(jobDetail)
                    .withIdentity("medicineJob", "JOB_GROUP")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInMinutes(1)
                            .repeatForever())
                    .build();
        }catch (Exception e){
            System.out.println("error in trigger medicine Job");
            e.printStackTrace();
            return null;
        }
    }

}

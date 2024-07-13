package vn.edu.fpt.SmartHealthC.quartz.trigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.TimeZone;

@Configuration
public class DailyJobTriggerConfig {
//    @Bean(name = "dailyJobTrigger")
//    public Trigger dailyJobTrigger(@Qualifier("dailyJobDetail") JobDetail jobDetail) {
//        System.out.println("execute trigger jobBoot");
//        try {
//            String time = "0/10 * * * * ?";
////            String time = "0 0 8,20 * * ?";
//            return TriggerBuilder.newTrigger().forJob(jobDetail)
//                    .withIdentity("daily_job", "JOB_GROUP")
//                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(time))
//                    .build();
//        }catch (Exception e){
//            System.out.println("error in trigger daily Job");
//            e.printStackTrace();
//            return null;
//        }
//    }
    @Bean(name = "morningTrigger")
    public Trigger morningTrigger(@Qualifier("morningJobDetail") JobDetail jobDetail) {
        System.out.println("execute morning trigger jobBoot at: "+ new Date());
        try {
//            String time = "0/10 * * * * ?";
            String time = "0 0 8 * * ?";
            return TriggerBuilder.newTrigger().forJob(jobDetail)
                    .withIdentity("daily_morning", "JOB_GROUP")
                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(time))
                    .build();
        }catch (Exception e){
            System.out.println("error in trigger daily morning Job");
            e.printStackTrace();
            return null;
        }
    }
    @Bean(name = "eveningTrigger")
    public Trigger eveningTrigger(@Qualifier("eveningJobDetail") JobDetail jobDetail) {
        System.out.println("execute evening trigger jobBoot");
        try {
//            String time = "0/10 * * * * ?";
            String time = "0 0 20 * * ?";
            return TriggerBuilder.newTrigger().forJob(jobDetail)
                    .withIdentity("daily_evening", "JOB_GROUP")
                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(time))
                    .build();
        }catch (Exception e){
            System.out.println("error in trigger daily evening Job");
            e.printStackTrace();
            return null;
        }
    }
    @Bean(name = "mondayMorningTrigger")
    public Trigger mondayMorningTrigger(@Qualifier("mondayMorningJobDetail") JobDetail jobDetail) {
        System.out.println("execute monday morning trigger jobBoot");
        try {
//            String time = "0/10 * * * * ?";
            String time = "0 0 8 ? * MON *";
            return TriggerBuilder.newTrigger().forJob(jobDetail)
                    .withIdentity("daily_monday_am", "JOB_GROUP")
                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(time))
                    .build();
        }catch (Exception e){
            System.out.println("error in trigger monday morning Job");
            e.printStackTrace();
            return null;
        }
    }
    @Bean(name = "sundayEveningTrigger")
    public Trigger sundayEveningTrigger(@Qualifier("sundayEveningJobDetail") JobDetail jobDetail) {
        System.out.println("execute sunday evening trigger jobBoot");
        try {
//            String time = "0/10 * * * * ?";
            String time = "0 0 20 ? * SUN *";
            return TriggerBuilder.newTrigger().forJob(jobDetail)
                    .withIdentity("daily_sunday_pm", "JOB_GROUP")
                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(time))
                    .build();
        }catch (Exception e){
            System.out.println("error in trigger sunday evening Job");
            e.printStackTrace();
            return null;
        }
    }

//    @Bean(name = "monthlyTrigger")
//    public Trigger monthlyTrigger(@Qualifier("monthlyJobDetail") JobDetail jobDetail) {
//        try {
//            String time = "0 21 * * * ?";
//            return TriggerBuilder.newTrigger().forJob(jobDetail)
//                    .withIdentity("monthly", "JOB_GROUP")
//                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(time))
//                    .build();
//        }catch (Exception e){
//            System.out.println("error in trigger monthly Job");
//            e.printStackTrace();
//            return null;
//        }
//    }
//    @Bean(name = "medicalReminderTrigger")
//    public Trigger medicalReminderTrigger(@Qualifier("medicalReminderJobDetail") JobDetail jobDetail) {
//        try {
//            String time = "0 13 * * * ?";
//            return TriggerBuilder.newTrigger().forJob(jobDetail)
//                    .withIdentity("remind", "JOB_GROUP")
//                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(time))
//                    .build();
//        }catch (Exception e){
//            System.out.println("error in trigger monthly Job");
//            e.printStackTrace();
//            return null;
//        }
//    }
//    @Bean
//    public Trigger mondayMorningTrigger() {
//        return TriggerBuilder.newTrigger()
//                .forJob(planReminderJobDetail())
//                .withIdentity("mondayMorningTrigger")
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 8 ? * MON *"))
//                .build();
//    }
//    @Bean
//    public Trigger morningTrigger() {
//        return TriggerBuilder.newTrigger()
//                .forJob(generalNotificationJobDetail())
//                .withIdentity("morningTrigger")
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 8 ? * * *"))
//                .build();
//    }
//
//    @Bean
//    public Trigger eveningTrigger() {
//        return TriggerBuilder.newTrigger()
//                .forJob(generalNotificationJobDetail())
//                .withIdentity("eveningTrigger")
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 20 ? * * *"))
//                .build();
//    }
}

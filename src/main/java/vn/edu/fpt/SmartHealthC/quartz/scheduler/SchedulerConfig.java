package vn.edu.fpt.SmartHealthC.quartz.scheduler;

import org.checkerframework.checker.units.qual.A;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import vn.edu.fpt.SmartHealthC.config.AutowiringSpringBeanJobFactory;

@Configuration
public class SchedulerConfig {
    @Autowired
    private JobFactory jobFactory;

    @Autowired
    private Trigger monthlyJobTrigger;

    @Autowired
    private JobDetail monthlyJobDetail;

    @Autowired
    private JobDetail morningJobDetail;

    @Autowired
    private JobDetail eveningJobDetail;

    @Autowired
    private Trigger morningTrigger;

    @Autowired
    private Trigger eveningTrigger;

    @Autowired
    private JobDetail sundayEveningJobDetail;

    @Autowired
    private JobDetail mondayMorningJobDetail;

    @Autowired
    private Trigger sundayEveningTrigger;

    @Autowired
    private Trigger mondayMorningTrigger;

    @Autowired
    private Trigger medicalAppointmentTrigger;

    @Autowired
    private JobDetail medicalReminderJobDetail;

    @Autowired
    private JobDetail dataReviewWeekJobDetail;

    @Autowired
    private Trigger dataWeekReviewTrigger;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        // Additional configuration for SchedulerFactoryBean
        return factory;
    }
    @Bean
    public void initialJob() {
        try {
            Scheduler scheduler = schedulerFactoryBean().getScheduler();
            scheduler.scheduleJob(medicalReminderJobDetail, medicalAppointmentTrigger);
            scheduler.scheduleJob(monthlyJobDetail, monthlyJobTrigger);
            scheduler.scheduleJob(morningJobDetail, morningTrigger);
            scheduler.scheduleJob(eveningJobDetail, eveningTrigger);
            scheduler.scheduleJob(sundayEveningJobDetail, sundayEveningTrigger);
            scheduler.scheduleJob(mondayMorningJobDetail, mondayMorningTrigger);
            scheduler.scheduleJob(dataReviewWeekJobDetail, dataWeekReviewTrigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

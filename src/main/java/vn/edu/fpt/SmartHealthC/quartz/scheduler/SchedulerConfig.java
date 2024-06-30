package vn.edu.fpt.SmartHealthC.quartz.scheduler;

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
    private Trigger dailyJobTrigger;

    @Autowired
    private Trigger weeklyJobTrigger;

    @Autowired
    private Trigger monthlyJobTrigger;

    @Autowired
    private JobDetail dailyJobDetail;

    @Autowired
    private JobDetail weeklyJobDetail;

    @Autowired
    private JobDetail monthlyJobDetail;
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
            scheduler.scheduleJob(dailyJobDetail, dailyJobTrigger);
            scheduler.scheduleJob(weeklyJobDetail, weeklyJobTrigger);
            scheduler.scheduleJob(monthlyJobDetail, monthlyJobTrigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

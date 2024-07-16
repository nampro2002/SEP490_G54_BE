package vn.edu.fpt.SmartHealthC.quartz.detail;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.fpt.SmartHealthC.quartz.job.*;

@Configuration
public class MedicineJobConfig {

    //morning & evening job
    @Bean(name = "medicineJobDetail")
    public JobDetail medicineJobDetail() {
        return JobBuilder.newJob(MedicineJob.class)
                .withIdentity("medicineJob", "JOB_GROUP")
                .storeDurably()
                .build();
    }


}

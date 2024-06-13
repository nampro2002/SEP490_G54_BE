package vn.edu.fpt.SmartHealthC.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class FlywayConfig {

    @Value("${spring.flyway.locations}")
    private String location;
    @Value("${spring.datasource.url}")
    private String datasource;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    @Bean
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource())
                .locations(location)
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .load();
        flyway.repair();
        flyway.migrate();
        return flyway;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(datasource);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}

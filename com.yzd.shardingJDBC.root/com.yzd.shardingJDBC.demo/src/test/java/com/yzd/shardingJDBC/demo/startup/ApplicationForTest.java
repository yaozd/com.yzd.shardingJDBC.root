package com.yzd.shardingJDBC.demo.startup;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan("com.yzd.shardingJDBC.demo")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableTransactionManagement(proxyTargetClass = true)
public class ApplicationForTest {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApplicationForTest.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}

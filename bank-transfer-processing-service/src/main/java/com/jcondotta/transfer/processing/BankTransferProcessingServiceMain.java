package com.jcondotta.transfer.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@ConfigurationPropertiesScan(basePackages = "com.jcondotta")
@SpringBootApplication(scanBasePackages = "com.jcondotta")
public class BankTransferProcessingServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(BankTransferProcessingServiceMain.class, args);
    }
}
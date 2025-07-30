package com.jcondotta.transfer.request;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "com.jcondotta")
@ConfigurationPropertiesScan(basePackages = "com.jcondotta")
public class BankTransferRequestServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(BankTransferRequestServiceMain.class, args);
    }
}

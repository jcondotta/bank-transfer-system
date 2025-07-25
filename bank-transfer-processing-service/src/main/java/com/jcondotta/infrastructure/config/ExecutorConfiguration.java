package com.jcondotta.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfiguration {

    @Bean(name = "internalBankTransferExecutorService")
    public ExecutorService internalBankTransferExecutorService() {
        return Executors.newFixedThreadPool(10);
    }
}
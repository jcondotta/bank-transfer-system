package com.jcondotta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

    @Bean(name = "errorMessageSource")
    public ResourceBundleMessageSource errorMessageSource() {
        var messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("i18n/exceptions/exceptions");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(false);

        return messageSource;
    }
}

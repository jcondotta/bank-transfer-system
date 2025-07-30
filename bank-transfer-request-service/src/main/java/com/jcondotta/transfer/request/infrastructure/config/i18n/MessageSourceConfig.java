package com.jcondotta.transfer.request.infrastructure.config.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

    @Bean(name = "infoMessageSource")
    public ResourceBundleMessageSource infoMessageSource() {
        var messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("i18n/messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(false);

        return messageSource;
    }

    @Bean(name = "errorMessageSource")
    public ResourceBundleMessageSource errorMessageSource() {
        var messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("i18n/exceptions/exceptions");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(false);

        return messageSource;
    }
}

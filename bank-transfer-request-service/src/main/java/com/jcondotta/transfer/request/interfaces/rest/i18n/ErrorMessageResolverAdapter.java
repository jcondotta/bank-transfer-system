package com.jcondotta.transfer.request.interfaces.rest.i18n;

import com.jcondotta.transfer.application.ports.output.i18n.MessageResolverPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component("errorMessageResolver")
public class ErrorMessageResolverAdapter implements MessageResolverPort {

    private final MessageSource errorMessageSource;

    public ErrorMessageResolverAdapter(@Qualifier("errorMessageSource") MessageSource errorMessageSource) {
        this.errorMessageSource = errorMessageSource;
    }

    @Override
    public Optional<String> resolveMessage(String messageCode, Object[] args, Locale locale) {
        return Optional.ofNullable(
            errorMessageSource.getMessage(messageCode, args, messageCode, locale)
        );
    }
}

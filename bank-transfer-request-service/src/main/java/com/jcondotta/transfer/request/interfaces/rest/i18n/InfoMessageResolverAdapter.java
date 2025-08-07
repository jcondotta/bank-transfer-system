package com.jcondotta.transfer.request.interfaces.rest.i18n;

import com.jcondotta.transfer.application.ports.output.i18n.MessageResolverPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component("infoMessageResolver")
public class InfoMessageResolverAdapter implements MessageResolverPort {

    private final MessageSource infoMessageSource;

    public InfoMessageResolverAdapter(@Qualifier("infoMessageSource") MessageSource infoMessageSource) {
        this.infoMessageSource = infoMessageSource;
    }

    @Override
    public Optional<String> resolveMessage(String messageCode, Object[] args, Locale locale) {
        return Optional.ofNullable(
            infoMessageSource.getMessage(messageCode, args, messageCode, locale)
        );
    }
}
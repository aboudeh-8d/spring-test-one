package com.bezkoder.spring.security.login.service.helper;

import com.bezkoder.spring.security.login.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TranslationService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key) {

        Locale locale = LocaleContextHolder.getLocale();  // ✅ Automatically uses your MyLocaleResolver

        try {
            return messageSource.getMessage(key, null, locale);
        } catch (NoSuchMessageException e) {
            return key; // fallback to key itself
        }
    }
}
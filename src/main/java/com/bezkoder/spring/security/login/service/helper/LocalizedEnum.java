package com.bezkoder.spring.security.login.service.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class LocalizedEnum {
    private String code;
    private String value;

    public LocalizedEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Autowired
    private static MessageSource messageSource;
    @Autowired
    private TranslationService translationService;

    public LocalizedEnum localizeEnum(Enum<?> enumValue, String prefix) {
        String key = enumValue.name();
        String translationKey = prefix + "." + key.toLowerCase();
        String value = translationService.getMessage(translationKey);
        return new LocalizedEnum(key, value);
    }


    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}

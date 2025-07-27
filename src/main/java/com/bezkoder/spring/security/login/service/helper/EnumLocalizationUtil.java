package com.bezkoder.spring.security.login.service.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class EnumLocalizationUtil {

    private static TranslationService translationServiceStatic;

    @Autowired
    public EnumLocalizationUtil(TranslationService translationService) {
        EnumLocalizationUtil.translationServiceStatic = translationService;
    }

    public static LocalizedEnum localizeEnum(Enum<?> enumValue, String prefix) {
        if (enumValue == null) {
            return new LocalizedEnum(null, null);  // return empty values if enum is null
        }
        String key = enumValue.name();
        String translationKey = prefix + "." + key.toLowerCase();
        Locale locale = LocaleContextHolder.getLocale();
//        myLocalResolver.resolveLocale(request)
        String value = translationServiceStatic.getMessage(translationKey);
        return new LocalizedEnum(key, value);
    }
}

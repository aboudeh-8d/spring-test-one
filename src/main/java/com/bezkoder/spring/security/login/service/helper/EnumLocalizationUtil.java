package com.bezkoder.spring.security.login.service.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;


@Component
public class EnumLocalizationUtil {

    private static TranslationService translationServiceStatic;

    @Autowired
    public EnumLocalizationUtil(TranslationService translationService) {
        EnumLocalizationUtil.translationServiceStatic = translationService;
    }

    public static LocalizedEnum localizeEnum(Enum<?> enumValue, String prefix) {
        if (enumValue == null) {
            return new LocalizedEnum(null, null);
        }
        String key = enumValue.name();
        String translationKey = prefix + "." + key.toLowerCase();
        String value = translationServiceStatic.getMessage(translationKey);
        return new LocalizedEnum(key, value);
    }
}

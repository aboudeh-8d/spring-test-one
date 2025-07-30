package com.bezkoder.spring.security.login.service.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;


public class LocalizedEnum {
    private String code;
    private String display;

    public LocalizedEnum() {
    }

    public LocalizedEnum(String code, String display) {
        this.code = code;
        this.display = display;
    }

    public String getCode() {
        return code;
    }

    public String getDisplay() {
        return display;
    }
}


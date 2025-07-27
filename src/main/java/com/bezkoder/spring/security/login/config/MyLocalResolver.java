package com.bezkoder.spring.security.login.config;

import com.bezkoder.spring.security.login.security.services.UserDetailsImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Component
public class MyLocalResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDetailsImpl user) {
            String lang = user.getLanguage().toString();
            if (lang != null && !lang.isEmpty()) {
                return Locale.forLanguageTag(lang);
            }
        }

        String language = request.getHeader("Accept-Language");
        if(language == null || language.isEmpty()){
            return Locale.forLanguageTag("en");
        }
        Locale locale = Locale.forLanguageTag(language);
        if(LanguageConfig.LOCALS.contains(locale)){
            return locale;
        }
        return Locale.forLanguageTag("en");
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}

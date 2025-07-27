package com.bezkoder.spring.security.login.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.ResourceBundle;

@Configuration
public class MessageConfiguration {
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasenames("messages","enum_messages"); // Load both
        messageSource.setBasenames("enum_messages"); // Load both
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
//    @Bean
//    public MessageSource messageSource(){
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasenames("messages", "enum_messages"); // Load both
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }
}

package com.bezkoder.spring.security.login.dto.user;

public interface UserSummary {
    Long getId();
    String getUsername();
    String getFullname();
    String getEmail();
}

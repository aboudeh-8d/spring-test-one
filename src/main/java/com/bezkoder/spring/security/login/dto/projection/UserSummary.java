package com.bezkoder.spring.security.login.dto.projection;

public interface UserSummary {
    Long getId();
    String getUsername();
    String getFullname();
    String getEmail();
}

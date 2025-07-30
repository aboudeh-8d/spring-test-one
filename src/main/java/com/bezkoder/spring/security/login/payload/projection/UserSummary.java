package com.bezkoder.spring.security.login.payload.projection;

public interface UserSummary {
    Long getId();
    String getUsername();
    String getFullname();
    String getEmail();
}

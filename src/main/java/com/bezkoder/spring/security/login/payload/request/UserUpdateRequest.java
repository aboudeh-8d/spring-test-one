package com.bezkoder.spring.security.login.payload.request;

import com.bezkoder.spring.security.login.enums.EUserLanguage;
import com.bezkoder.spring.security.login.enums.EUserStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserUpdateRequest {

    @Size(max = 20, message = "Username must be at most 20 characters")
    private String username;

    @Size(max = 20, message = "Full name must be at most 20 characters")
    private String fullname;

    @Email(message = "Email must be valid")
    @Size(max = 50, message = "Email must be at most 50 characters")
    private String email;

    @Size(max = 120, message = "Password must be at most 120 characters")
    private String password;

    private EUserStatus status;

    private Boolean isEnabled;

    private EUserLanguage language;

    private Set<Long> roleIds;

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EUserStatus getStatus() {
        return status;
    }

    public void setStatus(EUserStatus status) {
        this.status = status;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public EUserLanguage getLanguage() {
        return language;
    }

    public void setLanguage(EUserLanguage language) {
        this.language = language;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }
}

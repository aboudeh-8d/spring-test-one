package com.bezkoder.spring.security.login.payload.request;

import com.bezkoder.spring.security.login.enums.EUserLanguage;
import com.bezkoder.spring.security.login.enums.EUserStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserUpdateRequest {
    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 20)
    private String fullname;

    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @Size(max = 120)
    private String password;

    private EUserStatus status;

    private Boolean isEnabled;

    private EUserLanguage language;

    private Set<Long> roleIds; // Role IDs to update roles

    // Getters and Setters

    public @NotBlank @Size(max = 20) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(max = 20) String username) {
        this.username = username;
    }

    public @NotBlank @Size(max = 20) String getFullname() {
        return fullname;
    }

    public void setFullname(@NotBlank @Size(max = 20) String fullname) {
        this.fullname = fullname;
    }

    public @Email @NotBlank @Size(max = 50) String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank @Size(max = 50) String email) {
        this.email = email;
    }

    public @Size(max = 120) String getPassword() {
        return password;
    }

    public void setPassword(@Size(max = 120) String password) {
        this.password = password;
    }

    public EUserStatus getStatus() {
        return status;
    }

    public void setStatus(EUserStatus status) {
        this.status = status;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
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

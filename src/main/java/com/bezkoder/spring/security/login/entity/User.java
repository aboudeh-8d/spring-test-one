package com.bezkoder.spring.security.login.entity;

import com.bezkoder.spring.security.login.entity.base.BaseEntity;
import com.bezkoder.spring.security.login.enums.EUserLanguage;
import com.bezkoder.spring.security.login.enums.EUserStatus;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User extends BaseEntity {

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 20)
    private String fullname;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EUserStatus status = EUserStatus.ACTIVE;


    @Column(name = "is_enabled")
    private Boolean isEnabled = false;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private EUserLanguage language = EUserLanguage.EN;


    public User() {
    }

    public User(String username, String fullname, String email, EUserStatus status, EUserLanguage language, String password) {
        this.username = username;
        this.fullname = fullname;
        this.status = status;
        this.language = language;
        this.email = email;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


    public @NotBlank @Size(max = 20) String getFullname() {
        return fullname;
    }

    public void setFullname(@NotBlank @Size(max = 20) String fullname) {
        this.fullname = fullname;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public EUserLanguage getLanguage() {
        return this.language;
    }

    public void setLanguage(EUserLanguage language) {
        this.language = language;
    }
}

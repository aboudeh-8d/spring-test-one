package com.bezkoder.spring.security.login.payload.response;

import java.util.List;

public class UserInfoResponse {
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
	private LocalizedEnum status;
	private EUserLanguage language;

	public UserInfoResponse(Long id, String username, String email, List<String> roles , LocalizedEnum status, EUserLanguage language) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.status = status;
		this.language = language;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public LocalizedEnum getStatus() {
		return status;
	}

	public void setStatus(LocalizedEnum status) {
		this.status = status;
	}

	public LocalizedEnum getLanguage() {
		return EnumLocalizationUtil.localizeEnum(this.language, "user_language");
	}

	public void setLanguage(EUserLanguage language) {
		this.language = language;
	}
}

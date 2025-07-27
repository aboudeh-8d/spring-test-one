package com.bezkoder.spring.security.login.controllers;

import com.bezkoder.spring.security.login.config.MyLocalResolver;
import com.bezkoder.spring.security.login.extra.services.impl.UserServiceImpl;
import com.bezkoder.spring.security.login.payload.response.UserInfoResponse;
import com.bezkoder.spring.security.login.service.helper.EnumLocalizationUtil;
import com.bezkoder.spring.security.login.service.helper.LocalizedEnum;
import com.bezkoder.spring.security.login.service.helper.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MyLocalResolver myLocalResolver;
    @Autowired
    private TranslationService translationService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/message")
    public String getMessage(HttpServletRequest request) {
        System.out.println("  TestMessage  ............     ");
        return translationService.getMessage("user_status.active");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserInfoResponse>> getAllUsers() {
        List<UserInfoResponse> response = userService.getAllUsers().stream()
                .map(user -> {
                    List<String> roles = user.getRoles().stream()
                            .map(role -> role.getName().name())
                            .toList();
                    return new UserInfoResponse(user.getId(), user.getUsername(), user.getEmail(), roles, user.getStatus(), user.getLanguage());
                }).toList();

        return ResponseEntity.ok(response); // HTTP 200 with body
    }
}

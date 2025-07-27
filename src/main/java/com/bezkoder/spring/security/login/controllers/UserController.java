package com.bezkoder.spring.security.login.controllers;

import com.bezkoder.spring.security.login.config.MyLocalResolver;
import com.bezkoder.spring.security.login.entity.User;
import com.bezkoder.spring.security.login.extra.services.impl.UserServiceImpl;
import com.bezkoder.spring.security.login.payload.response.UserInfoResponse;
import com.bezkoder.spring.security.login.service.helper.EnumLocalizationUtil;
import com.bezkoder.spring.security.login.service.helper.LocalizedEnum;

import com.bezkoder.spring.security.login.service.helper.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MyLocalResolver myLocalResolver;

    @Autowired
    private TranslationService translationService;


    //    @PreAuthorize("hasRole('USER')")
//    @PreAuthorize("true")


    @GetMapping
    public ResponseEntity<List<UserInfoResponse>> getAllUsers() {
        List<UserInfoResponse> response = userService.getAllUsers().stream()
                .map(user -> {
                    List<String> roles = user.getRoles().stream()
                            .map(role -> role.getName().name())
                            .toList();
                    return new UserInfoResponse(user.getId(), user.getUsername(), user.getEmail(), roles, user.getStatus() , user.getLanguage());
                }).toList();

        return ResponseEntity.ok(response); // HTTP 200 with body
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}


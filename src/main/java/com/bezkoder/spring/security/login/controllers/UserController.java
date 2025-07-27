package com.bezkoder.spring.security.login.controllers;

import com.bezkoder.spring.security.login.config.MyLocalResolver;
import com.bezkoder.spring.security.login.dto.user.UserSummary;
import com.bezkoder.spring.security.login.entity.User;
import com.bezkoder.spring.security.login.service.UserServiceImpl;
import com.bezkoder.spring.security.login.dto.user.UserInfoResponse;

import com.bezkoder.spring.security.login.service.helper.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                    return new UserInfoResponse(user.getId(), user.getUsername(), user.getEmail(), roles, user.getStatus(), user.getLanguage());
                }).toList();

        return ResponseEntity.ok(response); // HTTP 200 with body
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<UserInfoResponse>> getUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {

        Page<User> usersPage = userService.getPaginatedUsers(page, size, sort, direction);

        Page<UserInfoResponse> responsePage = usersPage.map(user -> {
            List<String> roles = user.getRoles().stream()
                    .map(role -> role.getName().name())
                    .toList();

            return new UserInfoResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    roles,
                    user.getStatus(),
                    user.getLanguage()
            );
        });

        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/summaries")
    public ResponseEntity<Page<UserSummary>> getUserSummaries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {

        Page<UserSummary> summaries = userService.getUserSummaries(page, size, sort, direction);
        return ResponseEntity.ok(summaries);
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


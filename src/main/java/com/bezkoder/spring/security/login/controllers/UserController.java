package com.bezkoder.spring.security.login.controllers;

import com.bezkoder.spring.security.login.config.MyLocalResolver;
import com.bezkoder.spring.security.login.entity.Role;
import com.bezkoder.spring.security.login.payload.request.UserUpdateRequest;
import com.bezkoder.spring.security.login.payload.projection.UserSummary;
import com.bezkoder.spring.security.login.entity.User;
import com.bezkoder.spring.security.login.service.UserService;
import com.bezkoder.spring.security.login.payload.response.UserInfoResponse;

import com.bezkoder.spring.security.login.service.helper.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

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
        List<UserInfoResponse> response = userService.findAll().stream()
                .map(user -> {
                    List<String> roles = user.getRoles().stream()
                            .map(role -> role.getName().name())
                            .toList();
                    return new UserInfoResponse(user.getId(), user.getUsername(), user.getFullname(),  user.getEmail(), roles, user.getStatus(), user.getLanguage());
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
                    user.getFullname(),
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
    public ResponseEntity<UserInfoResponse> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> {
                    List<String> roles = user.getRoles().stream()
                            .map(role -> role.getName().name())
                            .toList();

                    UserInfoResponse response = new UserInfoResponse(
                            user.getId(),
                            user.getUsername(),
                            user.getFullname(),
                            user.getEmail(),
                            roles,
                            user.getStatus(),
                            user.getLanguage()
                    );
                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.save(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<UserInfoResponse> updateUser(@PathVariable Long id,
                                        @Valid @RequestBody UserUpdateRequest updateRequest) {
        User updatedUser = userService.update(id, updateRequest);
        List<String> roles = updatedUser.getRoles().stream()
                .filter(Objects::nonNull)
                .map(Role::getName)
                .filter(Objects::nonNull)
                .map(Enum::name)
                .toList();

        UserInfoResponse response = new UserInfoResponse(
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getFullname(),
                updatedUser.getEmail(),
                roles,
                updatedUser.getStatus(),
                updatedUser.getLanguage()
        );
            return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.delete(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}


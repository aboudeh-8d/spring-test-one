package com.bezkoder.spring.security.login.service.interfaces;

import com.bezkoder.spring.security.login.dto.user.UserSummary;
import com.bezkoder.spring.security.login.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    Optional<User> updateUser(Long id, User user);
    boolean deleteUser(Long id);
}

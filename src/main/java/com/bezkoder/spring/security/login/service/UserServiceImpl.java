package com.bezkoder.spring.security.login.service;

import com.bezkoder.spring.security.login.dto.projection.UserSummary;
import com.bezkoder.spring.security.login.entity.User;
import com.bezkoder.spring.security.login.repository.UserRepository;
import com.bezkoder.spring.security.login.service.interfaces.UserService;
import com.bezkoder.spring.security.login.utilities.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<UserSummary> getUserSummaries(int page, int size, String sortField, String direction) {
        Pageable pageable = Pagination.getPageable(page, size, sortField, direction);
        return userRepository.findAllBy(pageable);
    }

    public Page<User> getPaginatedUsers(int page, int size, String sortField, String direction) {
        Pageable pageable = Pagination.getPageable(page, size, sortField, direction);
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(Long id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(user.getUsername());
            existingUser.setFullname(user.getFullname());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setStatus(user.getStatus());
            existingUser.setEnabled(user.getEnabled());
            existingUser.setRoles(user.getRoles());
            return userRepository.save(existingUser);
        });
    }

    @Override
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }
}

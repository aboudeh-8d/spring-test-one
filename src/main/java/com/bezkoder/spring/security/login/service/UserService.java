package com.bezkoder.spring.security.login.service;

import com.bezkoder.spring.security.login.dto.projection.UserSummary;
import com.bezkoder.spring.security.login.entity.User;
import com.bezkoder.spring.security.login.repository.UserRepository;
import com.bezkoder.spring.security.login.service.base.BaseService;
import com.bezkoder.spring.security.login.utilities.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
@Service
//public class UserService extends BaseService<User, Long> implements UserService {
public class UserService extends BaseService<User, Long>{

    @Autowired
    private UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    public Page<UserSummary> getUserSummaries(int page, int size, String sortField, String direction) {
        Pageable pageable = Pagination.getPageable(page, size, sortField, direction);
        return userRepository.findAllBy(pageable);
    }

    public Page<User> getPaginatedUsers(int page, int size, String sortField, String direction) {
        Pageable pageable = Pagination.getPageable(page, size, sortField, direction);
        return userRepository.findAll(pageable);
    }

    public User save(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

//    public User update(Long id, UserUpdateRequest dto) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        user.setEmail(dto.getEmail());
//        user.setStatus(dto.getStatus());
//        user.setLanguage(dto.getLanguage());
//
//        return userRepository.save(user);
//    }

    public User update(Long id, User user) {
        // Check if user exists
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

        // Update fields (avoid updating ID or other protected fields unless required)
        existingUser.setUsername(user.getUsername());
        existingUser.setFullname(user.getFullname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword()); // ideally encrypt before saving
        existingUser.setStatus(user.getStatus());
        existingUser.setEnabled(user.getEnabled());
        existingUser.setRoles(user.getRoles());

        return userRepository.save(existingUser);
    }

    public boolean delete(Long id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }


}

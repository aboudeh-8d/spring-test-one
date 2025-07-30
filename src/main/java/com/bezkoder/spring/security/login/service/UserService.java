package com.bezkoder.spring.security.login.service;

import com.bezkoder.spring.security.login.payload.request.UserUpdateRequest;
import com.bezkoder.spring.security.login.payload.projection.UserSummary;
import com.bezkoder.spring.security.login.entity.Role;
import com.bezkoder.spring.security.login.entity.User;
import com.bezkoder.spring.security.login.repository.RoleRepository;
import com.bezkoder.spring.security.login.repository.UserRepository;
import com.bezkoder.spring.security.login.service.base.BaseService;
import com.bezkoder.spring.security.login.utilities.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
//public class UserService extends BaseService<User, Long> implements UserService {
public class UserService extends BaseService<User, Long>{

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


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

    public User update(Long id, UserUpdateRequest dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        updateNonNullFields(dto, user);

        // Handle special cases manually (e.g., password needs encoding, roles need fetching)
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            Set<Role> roles = roleRepository.findAllById(dto.getRoleIds()).stream().collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return userRepository.save(user);
        //        try {
//            userRepository.save(user);
//        } catch (TransactionSystemException ex) {
//            Throwable cause = ex.getRootCause();
//            System.err.println("Transaction failed: " + cause);
//            throw ex; // Or wrap into a ResponseEntity with cause.getMessage()
//        }
    }



    public boolean delete(Long id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }


}

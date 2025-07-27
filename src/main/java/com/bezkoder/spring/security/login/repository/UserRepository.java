package com.bezkoder.spring.security.login.repository;

import java.util.Optional;

import com.bezkoder.spring.security.login.dto.user.UserSummary;
import com.bezkoder.spring.security.login.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.security.login.entity.User;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Page<UserSummary> findAllBy(Pageable pageable);
}

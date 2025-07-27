package com.bezkoder.spring.security.login.repository;

import java.util.Optional;

import com.bezkoder.spring.security.login.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.security.login.enums.ERole;
import com.bezkoder.spring.security.login.entity.Role;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
  Optional<Role> findByName(ERole name);

  Boolean existsByName(ERole name);

}

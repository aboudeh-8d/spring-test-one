package com.bezkoder.spring.security.login.repository.base;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean // Prevent Spring from creating a bean for this interface
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    // Add shared methods here if needed (optional)
    List<T> findAllByIsDeletedFalse();

    // You can add more shared queries

}
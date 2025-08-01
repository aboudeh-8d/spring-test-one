package com.bezkoder.spring.security.login.utilities;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Pagination {

    public static Pageable getPageable(int page, int size, String sortField, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortField);
        return PageRequest.of(page, size, sort);
    }
}


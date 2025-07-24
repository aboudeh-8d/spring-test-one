// UserSeeder.java
package com.bezkoder.spring.security.login.seeders;

import com.bezkoder.spring.security.login.enums.ERole;
import com.bezkoder.spring.security.login.enums.EUserStatus;
import com.bezkoder.spring.security.login.entity.Role;
import com.bezkoder.spring.security.login.entity.User;
import com.bezkoder.spring.security.login.repository.RoleRepository;
import com.bezkoder.spring.security.login.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

public class UserSeeder {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public UserSeeder(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        this.userRepository = userRepo;
        this.roleRepository = roleRepo;
        this.encoder = encoder;
    }

    public void seed() {
        if (userRepository.existsByUsername("admin")) return;

        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow();

        User admin = new User("admin", "Admin", "admin@gmail.com", EUserStatus.ACTIVE, encoder.encode("12345678"));
        admin.setRoles(Set.of(adminRole));
        userRepository.save(admin);

        System.out.println("✅ Admin user seeded");
    }
}

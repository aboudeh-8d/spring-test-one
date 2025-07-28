package com.bezkoder.spring.security.login.seeder;

import com.bezkoder.spring.security.login.enums.ERole;
import com.bezkoder.spring.security.login.enums.EUserLanguage;
import com.bezkoder.spring.security.login.enums.EUserStatus;
import com.bezkoder.spring.security.login.entity.Role;
import com.bezkoder.spring.security.login.entity.User;
import com.bezkoder.spring.security.login.repository.RoleRepository;
import com.bezkoder.spring.security.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.Set;

@Profile({"dev", "local"}) // Only run in dev/local
@Component
public class UserSeederOld implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.existsByUsername("admin")) {
            return; // already seeded
        }

        // Find roles from DB
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));
        Role doctorRole = roleRepository.findByName(ERole.ROLE_DOCTOR)
                .orElseThrow(() -> new RuntimeException("ROLE_DOCTOR not found"));
        Role clientRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                .orElseThrow(() -> new RuntimeException("ROLE_CLIENT not found"));

        // Create users
        User admin = new User("admin", "Admin", "admin@gmail.com", EUserStatus.ACTIVE , EUserLanguage.EN, encoder.encode("12345678"));
        admin.setEnabled(true);
        admin.setRoles(Set.of(adminRole));


        userRepository.save(admin);

        System.out.println("✅ Users seeded successfully.");
    }
}

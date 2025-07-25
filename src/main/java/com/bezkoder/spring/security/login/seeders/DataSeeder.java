// DataSeeder.java
package com.bezkoder.spring.security.login.seeders;

import com.bezkoder.spring.security.login.repository.RoleRepository;
import com.bezkoder.spring.security.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Profile("dev")
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        RoleSeeder roleSeeder = new RoleSeeder(roleRepository);
        UserSeeder userSeeder = new UserSeeder(userRepository, roleRepository, encoder);
        // 🔥 Choose which seeders to run
        roleSeeder.seed();
        userSeeder.seed();
    }
}

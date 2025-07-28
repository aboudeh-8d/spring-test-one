package com.bezkoder.spring.security.login.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"dev", "local"})
@Component
public class DataSeederOld implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

    }
}

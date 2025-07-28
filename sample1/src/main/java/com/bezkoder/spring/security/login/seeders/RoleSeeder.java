// UserSeeder.java
package com.bezkoder.spring.security.login.seeder;

import com.bezkoder.spring.security.login.enums.ERole;
import com.bezkoder.spring.security.login.entity.Role;
import com.bezkoder.spring.security.login.repository.RoleRepository;

public class RoleSeeder {
    private final RoleRepository roleRepository;

    public RoleSeeder( RoleRepository roleRepo) {
        this.roleRepository = roleRepo;
    }

    public void seed() {
        boolean isSeed = false;
        for (ERole role : ERole.values()) {
            if (!roleRepository.existsByName(role)) {
                isSeed = true;
                Role admin = new Role(role);
                roleRepository.save(admin);
            }
        }
        if(isSeed) System.out.println("✅ Roles seeded") ;
    }
}

package com.bezkoder.spring.security.login.security;

import com.bezkoder.spring.security.login.enums.ERole;
import com.bezkoder.spring.security.login.entity.Role;
import com.bezkoder.spring.security.login.repository.RoleRepository;
import com.bezkoder.spring.security.login.security.jwt.AuthEntryPointJwt;
import com.bezkoder.spring.security.login.security.jwt.AuthTokenFilter;
import com.bezkoder.spring.security.login.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        var authorizeRequests = http.authorizeRequests();

//        authorizeRequests
//                .antMatchers("/api/auth/").permitAll()
//                .antMatchers("/api/test/").permitAll();
        Arrays.stream(getPermitAllPathsArray())
                .forEach(path -> authorizeRequests.antMatchers(path).permitAll());


        authorizeRequests
                .antMatchers(h2ConsolePath + "/**").permitAll()
                .anyRequest().authenticated();

        // Enable H2 console in browser
        http.headers().frameOptions().sameOrigin();

        // Register custom JWT filter and auth provider
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public String[] getPermitAllPathsArray() {
        String[] paths = {
                "/api/test/**",
                "/api/public/**",
                "/api/auth/**"
        };
        return paths;
    }

    // Optional: Seed roles into DB (ONLY for development, not production!)
    @Bean
    public void addInitialRoles() {
//        if (roleRepository.count() == 0) {
//            roleRepository.save(new Role(ERole.ROLE_ADMIN));
//            roleRepository.save(new Role(ERole.ROLE_USER));
//            roleRepository.save(new Role(ERole.ROLE_DOCTOR));
//            roleRepository.save(new Role(ERole.ROLE_CLIENT));
//        }
    }
}

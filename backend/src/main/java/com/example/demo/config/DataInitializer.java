package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 테스트 사용자가 없다면 생성
        if (!userRepository.existsByUsername("testuser")) {
            User testUser = User.builder()
                    .username("testuser")
                    .email("test@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .name("테스트 사용자")
                    .role(User.Role.USER)
                    .build();
            
            userRepository.save(testUser);
            log.info("테스트 사용자 생성 완료: testuser / password123");
        }

        // 관리자 사용자가 없다면 생성
        if (!userRepository.existsByUsername("admin")) {
            User adminUser = User.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .name("관리자")
                    .role(User.Role.ADMIN)
                    .build();
            
            userRepository.save(adminUser);
            log.info("관리자 사용자 생성 완료: admin / admin123");
        }
    }
}

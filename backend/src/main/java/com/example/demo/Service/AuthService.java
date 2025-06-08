package com.example.demo.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserDTO.Response register(UserDTO.RegisterRequest request) {
        // 중복 체크
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("이미 존재하는 사용자명입니다.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // 사용자 생성
        User user = request.toEntity();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        User savedUser = userRepository.save(user);
        return UserDTO.Response.fromEntity(savedUser);
    }

    public UserDTO.LoginResponse login(UserDTO.LoginRequest request) {
        // 인증
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        // JWT 토큰 생성
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        // 사용자 정보 조회
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        UserDTO.Response userResponse = UserDTO.Response.fromEntity(user);
        
        return new UserDTO.LoginResponse(token, userResponse);
    }

    @Transactional(readOnly = true)
    public UserDTO.Response getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        
        return UserDTO.Response.fromEntity(user);
    }
}

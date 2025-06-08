package com.example.demo.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 ID로 사용자 조회
     */
    public User findById(Long uid) {
        return userRepository.findById(uid)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. ID: " + uid));
    }

    /**
     * 사용자명으로 사용자 조회
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. Username: " + username));
    }

    /**
     * 사용자 정보를 DTO로 변환하여 반환
     */
    public UserDTO.Response getUserInfo(Long uid) {
        User user = findById(uid);
        return UserDTO.Response.fromEntity(user);
    }

    /**
     * 사용자 등록
     */
    @Transactional
    public UserDTO.Response registerUser(UserDTO.RegisterRequest request) {
        // 사용자명 중복 체크
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다: " + request.getUsername());
        }

        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + request.getEmail());
        }

        // 비밀번호 암호화
        User user = request.toEntity();
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 사용자 저장
        User savedUser = userRepository.save(user);
        return UserDTO.Response.fromEntity(savedUser);
    }

    /**
     * 사용자 정보 수정
     */
    @Transactional
    public UserDTO.Response updateUser(Long uid, UserDTO.RegisterRequest request) {
        User user = findById(uid);

        // 사용자명 중복 체크 (본인 제외)
        if (!user.getUsername().equals(request.getUsername()) && 
            userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다: " + request.getUsername());
        }

        // 이메일 중복 체크 (본인 제외)
        if (!user.getEmail().equals(request.getEmail()) && 
            userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + request.getEmail());
        }

        // 정보 업데이트
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setName(request.getName());

        // 비밀번호가 제공된 경우에만 암호화하여 업데이트
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return UserDTO.Response.fromEntity(updatedUser);
    }

    /**
     * 사용자 삭제
     */
    @Transactional
    public void deleteUser(Long uid) {
        User user = findById(uid);
        userRepository.delete(user);
    }
}

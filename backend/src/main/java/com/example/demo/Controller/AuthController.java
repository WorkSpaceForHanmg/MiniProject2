package com.example.demo.Controller;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 개발용, 운영에서는 구체적인 도메인 지정
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO.RegisterRequest request) {
        try {
            UserDTO.Response response = authService.register(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO.LoginRequest request) {
        try {
            UserDTO.LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 구체적인 오류 타입에 따라 다른 상태 코드 반환
            if (e.getMessage().contains("Bad credentials") || e.getMessage().contains("사용자를 찾을 수 없습니다")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인 실패: 사용자명 또는 비밀번호가 올바르지 않습니다.");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("로그인 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 현재 사용자 정보 조회
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO.Response> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO.Response response = authService.getCurrentUser(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    /**
     * 로그아웃 (클라이언트에서 토큰 삭제로 처리)
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("로그아웃 성공");
    }
}

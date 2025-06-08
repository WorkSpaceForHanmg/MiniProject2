package com.example.demo.Controller;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 현재 로그인한 사용자 정보 조회
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO.Response> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            // 사용자명으로 사용자 조회 후 DTO로 변환
            var user = userService.findByUsername(username);
            UserDTO.Response userResponse = UserDTO.Response.fromEntity(user);
            
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * 사용자 정보 수정
     */
    @PutMapping("/me")
    public ResponseEntity<UserDTO.Response> updateCurrentUser(@Valid @RequestBody UserDTO.RegisterRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            // 현재 사용자의 ID 가져오기
            var currentUser = userService.findByUsername(username);
            
            // 사용자 정보 업데이트
            UserDTO.Response updatedUser = userService.updateUser(currentUser.getUid(), request);
            
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 특정 사용자 정보 조회 (관리자용)
     */
    @GetMapping("/{uid}")
    public ResponseEntity<UserDTO.Response> getUserById(@PathVariable Long uid) {
        try {
            UserDTO.Response user = userService.getUserInfo(uid);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 사용자 등록 (공개 엔드포인트 - 회원가입용)
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO.Response> registerUser(@Valid @RequestBody UserDTO.RegisterRequest request) {
        try {
            UserDTO.Response newUser = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 사용자 삭제 (관리자용 또는 본인)
     */
    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long uid) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            var currentUser = userService.findByUsername(username);
            
            // 본인 또는 관리자만 삭제 가능 (추후 권한 검증 로직 추가 가능)
            if (!currentUser.getUid().equals(uid)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            userService.deleteUser(uid);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

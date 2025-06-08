package com.example.demo.DTO;

import com.example.demo.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public class UserDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RegisterRequest {
        @NotBlank(message = "사용자명은 필수입니다.")
        @Size(min = 3, max = 20, message = "사용자명은 3~20자여야 합니다.")
        private String username;

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 6, max = 20, message = "비밀번호는 6~20자여야 합니다.")
        private String password;

        @NotBlank(message = "이름은 필수입니다.")
        @Size(max = 20, message = "이름은 20자 이하여야 합니다.")
        private String name;

        // DTO → Entity
        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .email(this.email)
                    .password(this.password)  // 컨트롤러에서 암호화 처리
                    .name(this.name)
                    .role(User.Role.USER)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginRequest {
        @NotBlank(message = "사용자명은 필수입니다.")
        private String username;

        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long uid;
        private String username;
        private String email;
        private String name;
        private String role;

        // Entity → DTO
        public static Response fromEntity(User user) {
            return Response.builder()
                    .uid(user.getUid())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .name(user.getName())
                    .role(user.getRole().name())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginResponse {
        private String token;
        private String tokenType = "Bearer";
        private UserDTO.Response user;

        public LoginResponse(String token, UserDTO.Response user) {
            this.token = token;
            this.user = user;
        }
    }
}

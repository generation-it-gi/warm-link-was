package kr.warmlink.application.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class SignInDto {
    @Getter
    @AllArgsConstructor
    @Schema(name = "SignIn.Response", description = "로그인 응답 DTO")
    public static class Response {
        private String message;
        private String accessToken;

        public static Response of(String message, String accessToken) {
            return new Response(message, accessToken);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "SignIn.Register", description = "회원정보 등록 요청 DTO")
    public static class Register {
        private String name;
        private String email;
        private String interestField;
        private LocalDate birth;
        private String region;
        private String job;
    }
}

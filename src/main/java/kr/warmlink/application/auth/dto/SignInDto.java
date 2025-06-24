package kr.warmlink.application.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
}

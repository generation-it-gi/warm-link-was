package kr.warmlink.presentation.controller;

import kr.warmlink.application.auth.dto.SignInDto;
import kr.warmlink.application.auth.service.KakaoOAuthService;
import kr.warmlink.domain.auth.service.UserService;
import kr.warmlink.presentation.api.AuthApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final KakaoOAuthService kakaoOAuthService;
    private final UserService userService;

    @Override
    @GetMapping("/sign-in/kakao")
    public ResponseEntity<?> signIn(String code) {
        return kakaoOAuthService.kakaoSignIn(code);
    }

    @Override
    @PostMapping("/users")
    public ResponseEntity<?> register(SignInDto.Register dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

}

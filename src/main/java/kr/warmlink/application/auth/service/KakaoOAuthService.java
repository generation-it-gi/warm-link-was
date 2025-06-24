package kr.warmlink.application.auth.service;

import kr.warmlink.application.auth.config.KakaoConfig;
import kr.warmlink.application.auth.dto.SignInDto;
import kr.warmlink.common.jwt.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

    private final KakaoConfig kakaoConfig;
    private final JwtProvider jwtProvider;

    public ResponseEntity<SignInDto.Response> kakaoSignIn(String code) {
        String token = getToken(code);
        String email = getEmail(token);

        String message = "로그인에 성공하였습니다";
        String accessToken = jwtProvider.generateAccessToken(email);

        return ResponseEntity.ok(SignInDto.Response.of(message, accessToken));
    }

    private String getToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoConfig.getClientId());
        params.add("client_secret", kakaoConfig.getClientSecret());
        params.add("redirect_uri", kakaoConfig.getRedirectUri());
        params.add("code", code);

        log.info("token uri - {}", kakaoConfig.getTokenUri());
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                kakaoConfig.getTokenUri(),
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        Map<String, Object> tokenInfo = response.getBody();
        return (String) tokenInfo.get("access_token");
    }

    private String getEmail(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                kakaoConfig.getUserInfoUri(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        Map<String, Object> userInfo = response.getBody();
        Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
        String email = (String) kakaoAccount.get("email");

        return email;
    }

}

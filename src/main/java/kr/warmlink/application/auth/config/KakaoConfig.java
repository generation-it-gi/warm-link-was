package kr.warmlink.application.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.kakao")
public class KakaoConfig {
    private String authorizationUri;
    private String tokenUri;
    private String userInfoUri;
    private String unlinkUri;
    private String clientId;
    private String clientSecret;
    private String adminKey;
    private String redirectUri;
    private String scope;
}

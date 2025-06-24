package kr.warmlink.presentation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.warmlink.application.auth.dto.SignInDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "[인증 관련 API]", description = "사용자 인증 관련 API")
public interface AuthApi {
    @Operation(summary = "사용자 카카오 로그인", description = "사용자의 로그인 및 회원가입을 위한 카카오 로그인 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "로그인 실패")
    })
    ResponseEntity<?> signIn(@RequestParam("code") String code);

    @Operation(summary = "사용자 정보 등록", description = "서비스 가입을 위한 사용자 정보 등록을 위한 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 등록 성공"),
            @ApiResponse(responseCode = "400", description = "회원 정보 등록 실패")
    })
    ResponseEntity<?> register(@RequestBody SignInDto.Register dto);
}

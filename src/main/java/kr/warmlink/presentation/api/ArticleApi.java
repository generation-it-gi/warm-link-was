package kr.warmlink.presentation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import kr.warmlink.application.article.dto.ArticleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "[판매글 관련 API]", description = "사용자 판매글 관련 API")
public interface ArticleApi {
    @Operation(summary = "판매글 등록", description = "판매글 등록을 위한 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "판매글 등록 성공"),
            @ApiResponse(responseCode = "400", description = "판매글 등록 실패")
    })
    ResponseEntity<?> create(HttpServletRequest request, @RequestBody ArticleDto.Request dto);

    @Operation(summary = "판매글 목록 조회", description = "특정 사용자가 등록한 모든 판매글을 조회하기 위한 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "판매글 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "판매글 목록 조회 실패")
    })
    ResponseEntity<?> overview(HttpServletRequest request);

    @Operation(summary = "판매글 수정", description = "사용자의 판매 게시글을 수정하기 위한 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "판매글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "판매글 수정 실패")
    })
    ResponseEntity<?> update(HttpServletRequest request, @PathVariable Long id, @RequestBody ArticleDto.Request dto);

    @Operation(summary = "판매글 삭제", description = "사용자의 판매 게시글을 삭제하기 위한 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "판매글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "판매글 수정 실패")
    })
    ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Long id);

    @Operation(summary = "판매글 세부 조회", description = "사용자의 판매 게시글을 내용까지 조회하기 위한 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "판매글 세부 조회 성공"),
            @ApiResponse(responseCode = "400", description = "판매글 세부 조회 실패")
    })
    ResponseEntity<?> detail(HttpServletRequest request, @PathVariable Long id);

}

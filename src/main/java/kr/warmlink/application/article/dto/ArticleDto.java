package kr.warmlink.application.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.warmlink.domain.article.entity.Article;
import kr.warmlink.domain.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "ArticleDto.Request", description = "판매글 등록을 위한 DTO")
    public static class Request {
        @Schema(description = "게시글 제목", example = "초상화 그려드립니다.")
        private String title;
        @Schema(description = "게시글 내용", example = "1장에 8천원 이상, 택배비 별도 부담하셔야 합니다.")
        private String content;
    }

    @Getter
    @AllArgsConstructor
    @Schema(name = "ArticleDto.Response", description = "판매글 등록에 대한 응답 DTO")
    public static class Response {
        @Schema(description = "응답 메시지", example = "게시글 등록에 성공하였습니다.")
        private String message;

        public static Response of(String message) {
            return new Response(message);
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "ArticleDto.Overview", description = "게시글 목록 조회 응답 DTO")
    public static class Overview {
        @Schema(description = "게시글 id", example = "1")
        private Long id;

        @Schema(description = "게시글 제목", example = "초상화 그려드립니다.")
        private String tile;

        @Schema(description = "작성자 이름", example = "홍길동")
        private String name;

        public static List<Overview> from(User user) {
            return user.getArticles().stream()
                    .filter(article -> article.isDeleted() == false)
                    .map(article -> Overview.builder()
                            .id(article.getId())
                            .tile(article.getTitle())
                            .name(user.getName())
                            .build())
                    .collect(Collectors.toUnmodifiableList());
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @Schema(name = "ArticleDto.DetailResponse", description = "게시글 세부 조회 응답 DTO")
    public static class DetailResponse {
        @Schema(description = "게시글 제목", example = "초상화 그려드립니다.")
        private String title;

        @Schema(description = "게시글 내용", example = "1장에 8천원 이상, 택배비 별도 부담하셔야 합니다.")
        private String content;

        @Schema(description = "작성자", example = "홍길동")
        private String name;

        @Schema(description = "작성일", example = "2025-06-26")
        private LocalDateTime createdAt;

        public static DetailResponse from(Article article, User user) {
            return DetailResponse.builder()
                    .title(article.getTitle())
                    .content(article.getContent())
                    .name(user.getName())
                    .createdAt(article.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @Schema(name = "ArticleDto.SearchResponse", description = "검색 결과 반환을 위한 응답 DTO")
    public static class SearchResponse {
        @Schema(description = "게시글 id", example = "1")
        private Long id;

        @Schema(description = "게시글 제목", example = "초상화 그려드립니다.")
        private String title;

        @Schema(description = "작성자", example = "홍길동")
        private String name;

        @Schema(description = "작성일", example = "2025-06-26")
        private LocalDateTime createdAt;

        public static List<SearchResponse> from(List<Article> articles) {
            return articles.stream()
                    .filter(article -> article.isDeleted() == false)
                    .map(article -> SearchResponse.builder()
                            .id(article.getId())
                            .name(article.getUser().getName())
                            .title(article.getTitle())
                            .createdAt(article.getCreatedAt())
                            .build())
                    .collect(Collectors.toUnmodifiableList());
        }
    }
}

package kr.warmlink.domain.article.service;

import jakarta.servlet.http.HttpServletRequest;
import kr.warmlink.application.article.dto.ArticleDto;
import kr.warmlink.common.jwt.util.JwtProvider;
import kr.warmlink.domain.article.entity.Article;
import kr.warmlink.domain.article.repository.ArticleRepository;
import kr.warmlink.domain.auth.entity.User;
import kr.warmlink.domain.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleDto.Response create(HttpServletRequest request, ArticleDto.Request dto) {
        String accessToken = jwtProvider.resolveToken(request);
        String email = jwtProvider.getEmail(accessToken);

        User user = userService.read(email);
        Article article = Article.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();

        articleRepository.save(article);

        String message = "게시글 등록이 완료되었습니다.";
        return ArticleDto.Response.of(message);
    }

    @Transactional
    public List<ArticleDto.Overview> read(HttpServletRequest request) {
        String accessToken = jwtProvider.resolveToken(request);
        String email = jwtProvider.getEmail(accessToken);

        User user = userService.read(email);
        return ArticleDto.Overview.from(user);
    }

    @Transactional
    public Article read(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Transactional
    public ArticleDto.Response update(HttpServletRequest request, Long id, ArticleDto.Request dto) {
        String accessToken = jwtProvider.resolveToken(request);
        String email = jwtProvider.getEmail(accessToken);
        Article article = read(id);

        article.changeTitle(dto.getTitle());
        article.changeContent(dto.getContent());

        articleRepository.save(article);

        String message = "게시글 수정을 완료했습니다.";
        return ArticleDto.Response.of(message);
    }

    @Transactional
    public ArticleDto.Response delete(HttpServletRequest request, Long id) {
        String accessToken = jwtProvider.resolveToken(request);
        String email = jwtProvider.getEmail(accessToken);

        Article article = read(id);
        article.delete();

        articleRepository.save(article);

        String message = "게시글이 정상적으로 삭제되었습니다.";
        return ArticleDto.Response.of(message);
    }

    @Transactional
    public ArticleDto.DetailResponse detail(HttpServletRequest request, Long id) {
        String accessToken = jwtProvider.resolveToken(request);
        String email = jwtProvider.getEmail(accessToken);

        Article article = read(id);

        return ArticleDto.DetailResponse.from(article, article.getUser());
    }

    @Transactional
    public List<ArticleDto.SearchResponse> search(HttpServletRequest request, String keyword) {
        String accessToken = jwtProvider.resolveToken(request);
        String email = jwtProvider.getEmail(accessToken);

        List<Article> result = articleRepository.findByKeyword(keyword);

        return ArticleDto.SearchResponse.from(result);
    }
}

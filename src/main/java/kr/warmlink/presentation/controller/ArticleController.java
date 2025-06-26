package kr.warmlink.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.warmlink.application.article.dto.ArticleDto;
import kr.warmlink.domain.article.service.ArticleService;
import kr.warmlink.presentation.api.ArticleApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController implements ArticleApi {

    private final ArticleService articleService;

    @Override
    @PostMapping("")
    public ResponseEntity<?> create(HttpServletRequest request, ArticleDto.Request dto) {
        return ResponseEntity.ok(articleService.create(request, dto));
    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> overview(HttpServletRequest request) {
        return ResponseEntity.ok(articleService.read(request));
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(HttpServletRequest request, Long id, ArticleDto.Request dto) {
        return ResponseEntity.ok(articleService.update(request, id, dto));
    }

    @Override
    @PatchMapping("/{id}/delete")
    public ResponseEntity<?> delete(HttpServletRequest request, Long id) {
        return ResponseEntity.ok(articleService.delete(request, id));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(HttpServletRequest request, Long id) {
        return ResponseEntity.ok(articleService.detail(request, id));
    }

}

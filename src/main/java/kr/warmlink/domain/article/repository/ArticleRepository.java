package kr.warmlink.domain.article.repository;

import kr.warmlink.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM article a WHERE a.title LIKE %:keyword%")
    List<Article> findByKeyword(@Param("keyword") String keyword);

}

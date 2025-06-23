package kr.warmlink.domain.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import kr.warmlink.common.entity.BaseEntity;
import kr.warmlink.domain.article.entity.Article;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Column(name = "email", nullable = false, updatable = false, length = 50)
    private String email;

    @Column(name = "name", nullable = false, length = 8)
    private String name;

    @Column(name = "interest_field", nullable = false, length = 10)
    private String interestField;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "region", nullable = false, length = 10)
    private String region;

    @Column(name = "job", nullable = false, length = 10)
    private String job;

    @OneToMany(mappedBy = "user")
    private List<Article> articles;

    @Builder
    public User(String email, String name, String interestField, LocalDate birth, String region, String job) {
        this.email = email;
        this.name = name;
        this.interestField = interestField;
        this.birth = birth;
        this.region = region;
        this.job = job;
    }
}

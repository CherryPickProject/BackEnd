package charrypick.charrypickapp.crawling.domain;

import charrypick.charrypickapp.basetest.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ArticlePhoto extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articlePhoto_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="article_id")
    private Article article;

    private String articleImgUrl;

    @Builder
    public ArticlePhoto(Article article, String articleImgUrl) {
        this.article = article;
        this.articleImgUrl = articleImgUrl;
        article.getArticlePhoto().add(this);
    }
}
package charrypick.charrypickapp.crawling.domain;

import charrypick.charrypickapp.basetest.domain.BaseEntity;
import java.util.ArrayList;
import java.util.List;
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

    private String imgDesc;

    @Builder
    public ArticlePhoto(Article article, String articleImgUrl, String imgDesc) {
        this.article = article;
        this.articleImgUrl = articleImgUrl;
        this.imgDesc = imgDesc;

        article.getArticlePhoto().add(this);
    }
}
package charrypick.charrypickapp.crawling.repository;

import charrypick.charrypickapp.crawling.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}

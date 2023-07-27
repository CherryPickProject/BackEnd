package charrypick.charrypickapp.crawling.repository;

import charrypick.charrypickapp.crawling.domain.ArticlePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlePhotoRepository extends JpaRepository<ArticlePhoto, Long> {
}

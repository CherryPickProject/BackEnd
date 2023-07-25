package charrypick.charrypickapp.crawling.repository;

import charrypick.charrypickapp.crawling.domain.PressIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PressIndexRepository extends JpaRepository<PressIndex, Long> {
    Optional<PressIndex> findByPress(String pressName);
}

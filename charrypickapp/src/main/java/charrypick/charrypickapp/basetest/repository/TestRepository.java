package charrypick.charrypickapp.basetest.repository;

import charrypick.charrypickapp.basetest.domain.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {

}

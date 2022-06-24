package info.akaki.usage.repository;

import info.akaki.usage.entity.Usage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsageRepository extends JpaRepository<Usage, Long> {
}

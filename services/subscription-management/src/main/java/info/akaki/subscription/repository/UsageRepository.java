package info.akaki.subscription.repository;

import info.akaki.subscription.entity.Usage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsageRepository extends JpaRepository<Usage, Long> {
}

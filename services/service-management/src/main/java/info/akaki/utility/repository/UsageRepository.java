package info.akaki.utility.repository;

import info.akaki.utility.entity.Usage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsageRepository extends JpaRepository<Usage, Long> {
}

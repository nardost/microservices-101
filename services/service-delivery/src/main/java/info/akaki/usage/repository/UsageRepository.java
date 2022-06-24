package info.akaki.usage.repository;

import info.akaki.usage.entity.Usage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UsageRepository extends JpaRepository<Usage, Long> {
    @Query("select u from Usage u where u.deviceId = :deviceId and u.captureTimestamp between :from and :to")
    List<Usage> getUsageData(
            @Param("deviceId") UUID deviceId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}

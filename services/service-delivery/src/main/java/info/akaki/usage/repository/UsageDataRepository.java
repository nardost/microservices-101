package info.akaki.usage.repository;

import info.akaki.usage.entity.UsageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UsageDataRepository extends JpaRepository<UsageData, Long> {
    @Query("select u from UsageData u where u.deviceId = :deviceId and u.captureTimestamp between :from and :to")
    List<UsageData> getUsageData(
            @Param("deviceId") UUID deviceId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}

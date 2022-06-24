package info.akaki.usage.service;

import info.akaki.usage.entity.Usage;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public interface UsageService {
    Collection<Usage> getUsageDataByDeviceId(UUID deviceId, LocalDateTime from, LocalDateTime to);
    Usage saveUsageData(Usage usage);
}

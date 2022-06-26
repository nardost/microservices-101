package info.akaki.usage.service;

import info.akaki.usage.entity.UsageData;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public interface UsageDataService {
    Collection<UsageData> getUsageDataByDeviceId(UUID deviceId, LocalDateTime from, LocalDateTime to);
    UsageData saveUsageData(UsageData usage);
}

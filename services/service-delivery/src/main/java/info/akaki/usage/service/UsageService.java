package info.akaki.usage.service;

import info.akaki.usage.entity.Usage;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public interface UsageService {
    Collection<Usage> getAllTimeUsagesByAllSubscriptions();
    Collection<Usage> getAllTimeUsagesBySubscriptionId(UUID subscriptionId);
    Collection<Usage> getUsagesBySubscriptionId(UUID subscriptionId, LocalDateTime from, LocalDateTime to);
    Usage saveUsage(Usage usage);
}

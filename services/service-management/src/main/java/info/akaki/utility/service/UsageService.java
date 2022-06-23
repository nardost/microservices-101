package info.akaki.utility.service;

import info.akaki.utility.entity.Usage;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public interface UsageService {
    Collection<Usage> getAllTimeUsagesByAllSubscriptions();
    Collection<Usage> getAllTimeUsagesBySubscriptionId(UUID subscriptionId);
    Collection<Usage> getUsagesBySubscriptionId(UUID subscriptionId, LocalDateTime from, LocalDateTime to);
    Usage saveUsage(Usage usage);
}

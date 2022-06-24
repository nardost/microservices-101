package info.akaki.subscription.service;

import info.akaki.subscription.dto.SubscriptionDTO;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public interface SubscriptionService {
    SubscriptionDTO getSubscriptionById(UUID subscriptionId);
    Collection<SubscriptionDTO> getAllSubscriptions();
    SubscriptionDTO saveSubscription(SubscriptionDTO subscriptionDTO);
    void scheduleServiceActivation(UUID subscriptionId);
}

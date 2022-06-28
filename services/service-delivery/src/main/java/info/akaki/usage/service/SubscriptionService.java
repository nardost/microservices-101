package info.akaki.usage.service;

import info.akaki.usage.dto.SubscriptionDTO;

import java.util.Collection;

public interface SubscriptionService {
    Collection<SubscriptionDTO> getSubscriptions();
    SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);
    SubscriptionDTO updateSubscription(SubscriptionDTO dto);
}

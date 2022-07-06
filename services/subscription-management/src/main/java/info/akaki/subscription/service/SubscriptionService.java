package info.akaki.subscription.service;

import info.akaki.subscription.dto.SubscriptionDTO;

import java.util.Collection;
import java.util.UUID;

public interface SubscriptionService {
    SubscriptionDTO getSubscriptionById(UUID subscriptionId);
    Collection<SubscriptionDTO> getAllSubscriptions();
    SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO);
    void changeSubscriptionStatus(SubscriptionDTO subscriptionDTO);
}

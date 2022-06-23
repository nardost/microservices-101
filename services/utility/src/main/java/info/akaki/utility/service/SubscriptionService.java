package info.akaki.utility.service;

import info.akaki.utility.dto.SubscriptionDTO;

import java.util.Collection;
import java.util.UUID;

public interface SubscriptionService {
    SubscriptionDTO getSubscriptionById(UUID subscriptionId);
    Collection<SubscriptionDTO> getAllSubscriptions();
    SubscriptionDTO saveSubscription(SubscriptionDTO subscriptionDTO);
}

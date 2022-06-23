package info.akaki.utility.service;

import info.akaki.utility.dto.SubscriptionDTO;
import info.akaki.utility.entity.SubscriptionStatus;
import info.akaki.utility.exception.AkakiUtilityException;
import info.akaki.utility.repository.PlanRepository;
import info.akaki.utility.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;

@Service(value = "subscriptionServiceAlpha")
@Transactional
public class SubscriptionServiceAlpha implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    @Autowired
    public SubscriptionServiceAlpha(
            SubscriptionRepository subscriptionRepository,
            PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    @Override
    public SubscriptionDTO getSubscriptionById(UUID subscriptionId) {
        return new SubscriptionDTO(this.subscriptionRepository
                .findById(subscriptionId)
                .orElseThrow(() -> new AkakiUtilityException("service.subscription.not-found")));
    }

    @Override
    public Collection<SubscriptionDTO> getAllSubscriptions() {
        return this.subscriptionRepository.findAll()
                .stream()
                .map(SubscriptionDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionDTO saveSubscription(SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO.validate(subscriptionDTO);
        if(FALSE.equals(planExists(subscriptionDTO.getPlanId()))) {
            throw new AkakiUtilityException("plan.not-found");
        }
        if(FALSE.equals(subscriberExists(subscriptionDTO.getSubscriberId()))) {
            throw new AkakiUtilityException("subscriber.not-found");
        }
        subscriptionDTO.setSubscriptionTimestamp(LocalDateTime.now());
        subscriptionDTO.setStatus(SubscriptionStatus.PENDING);
        return new SubscriptionDTO(this.subscriptionRepository.saveAndFlush(subscriptionDTO.toSubscription()));
    }

    /**
     * Check if plan exists
     * @param planId the plan id
     * @return true if plan exists; false otherwise
     */
    private Boolean planExists(UUID planId) {
        return this.planRepository.existsById(planId);
    }

    // TODO: Check if subscriber exists
    private Boolean subscriberExists(UUID subscriberId) {
        return true;
    }
}

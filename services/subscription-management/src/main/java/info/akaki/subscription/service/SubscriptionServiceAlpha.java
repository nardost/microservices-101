package info.akaki.subscription.service;

import info.akaki.subscription.dto.ActionDTO;
import info.akaki.subscription.dto.SubscriptionDTO;
import info.akaki.subscription.entity.SubscriptionStatus;
import info.akaki.subscription.exception.SubscriptionManagementException;
import info.akaki.subscription.repository.CustomerRepository;
import info.akaki.subscription.repository.PlanRepository;
import info.akaki.subscription.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SubscriptionServiceAlpha implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public SubscriptionServiceAlpha(
            SubscriptionRepository subscriptionRepository,
            PlanRepository planRepository,
            CustomerRepository customerRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public SubscriptionDTO getSubscriptionById(UUID subscriptionId) {
        return new SubscriptionDTO(this.subscriptionRepository
                .findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionManagementException("service.subscription.not-found")));
    }

    @Override
    public Collection<SubscriptionDTO> getAllSubscriptions() {
        return this.subscriptionRepository.findAll()
                .stream()
                .map(SubscriptionDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO.validate(subscriptionDTO);
        if(FALSE.equals(planExists(subscriptionDTO.getSubscriptionType()))) {
            throw new SubscriptionManagementException("plan.not-found");
        }
        if(FALSE.equals(subscriberExists(subscriptionDTO.getSubscriberId()))) {
            throw new SubscriptionManagementException("subscriber.not-found");
        }
        subscriptionDTO.setSubscriptionTimestamp(LocalDateTime.now());
        subscriptionDTO.setSubscriptionStatus(SubscriptionStatus.PENDING);
        requestSubscription(subscriptionDTO);
        return new SubscriptionDTO(this.subscriptionRepository.saveAndFlush(subscriptionDTO.toSubscription()));
    }

    @Override
    public void changeSubscriptionStatus(ActionDTO actionDTO) {
        requestServiceStatusChange(actionDTO);
        log.info("{}", actionDTO);
    }

    /**
     * TODO:
     *  - send subscription request to service-delivery microservice
     *  - should return device info if subscriber is leasing device.
     */
    private void requestSubscription(SubscriptionDTO subscriptionDTO) {
        log.info("Subscription request sent to service-delivery microservice");
    }

    /**
     * TODO:
     *  - send activation request to service-delivery microservice
     *
     * @param actionDTO subscription id
     */
    private void requestServiceStatusChange(ActionDTO actionDTO) {
        log.info("Service activation will be requested for subscription id");
    }

    /**
     * Check if plan exists
     * @param subscriptionType the plan id
     * @return true if plan exists; false otherwise
     */
    private Boolean planExists(String subscriptionType) {
        return this.planRepository.existsByServiceType(subscriptionType);
    }

    /**
     * Check is customer exists
     * @param subscriberId customer id
     * @return true if customer exists
     */
    private Boolean subscriberExists(UUID subscriberId) {
        return this.customerRepository.existsById(subscriberId);
    }
}

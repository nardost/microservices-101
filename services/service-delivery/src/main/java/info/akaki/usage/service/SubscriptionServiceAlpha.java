package info.akaki.usage.service;

import info.akaki.usage.dto.SubscriptionDTO;
import info.akaki.usage.entity.Device;
import info.akaki.usage.entity.DeviceSource;
import info.akaki.usage.entity.DeviceState;
import info.akaki.usage.entity.ServiceStatus;
import info.akaki.usage.entity.ServiceType;
import info.akaki.usage.entity.Subscription;
import info.akaki.usage.exception.ServiceDeliveryException;
import info.akaki.usage.repository.DeviceRepository;
import info.akaki.usage.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static info.akaki.usage.entity.ServiceStatus.*;

@Service("subscriptionService")
@Transactional
@Slf4j
public class SubscriptionServiceAlpha implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public SubscriptionServiceAlpha(
            SubscriptionRepository subscriptionRepository,
            DeviceRepository deviceRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Collection<SubscriptionDTO> getSubscriptions() {
        return this.subscriptionRepository.findAll()
                .stream()
                .map(SubscriptionDTO::new)
                .collect(Collectors.toSet());
    }

    @Override
    public SubscriptionDTO createSubscription(SubscriptionDTO dto) {
        SubscriptionDTO.validateForCreationRequest(dto);

        final Subscription subscription = dto.toSubscription();
        // Assume device will be leased
        Device device = this.deviceRepository
                .findFirstByServiceTypesContainingAndDeviceStateEquals(
                        dto.getServiceType(),
                        DeviceState.IDLE
                );
        // if subscriber wants to use own device, query device metadata in manufacturer db
        if(DeviceSource.OWN.equals(subscription.getDevice().getDeviceSource())) {
            device = queryManufacturerDB(subscription.getDevice().getDeviceId())
                    .orElseThrow(() -> new ServiceDeliveryException("service.device-not-recognized"));
            device.setDeviceSource(DeviceSource.OWN);
        }
        device.setDeviceState(DeviceState.ACTIVE);
        subscription.setDevice(device);
        subscription.setServiceStatus(CREATED);
        subscription.setSubscriptionTimestamp(LocalDateTime.now());
        return new SubscriptionDTO(this.subscriptionRepository.saveAndFlush(subscription));
    }

    @Override
    public SubscriptionDTO updateSubscription(SubscriptionDTO dto) {
        SubscriptionDTO.validateForUpdateRequest(dto);
        final Subscription subscription = this.subscriptionRepository
                .findById(dto.getSubscriptionId())
                .orElseThrow(() -> new ServiceDeliveryException("subscription.not-found"));
        /*
         *  Identify the type of update requested
         *  (1) activate / suspend / resume / terminate
         * TODO:
         *  (2) replace device
         *      - OWN to LEASE
         *      - LEASE to OWN
         *      - LEASE to LEASE
         *  (3) change service plan
         *   (4) ?
         */

        // Get a rollback clone in case something fails.
        final Subscription copy = new Subscription();
        copy.setServiceType(subscription.getServiceType());
        copy.setDevice(subscription.getDevice());

        // (1) activate / suspend / resume / terminate
        if(subscription.getServiceStatus() != dto.getSubscriptionStatus()) {
            if(!doSubscriptionStateTransition(subscription, dto)) {
                throw new ServiceDeliveryException("service.illegal-service-state-transition");
            }
        }

        // (2) replace device
        if(Objects.nonNull(dto.getDeviceSource())) {
            if(!doDeviceReplacement(subscription, dto)) {
                // rollback x 1
                subscription.setServiceStatus(copy.getServiceStatus());
                log.error("Service status change for subscription {} rolled back", subscription.getSubscriptionId());
                throw new ServiceDeliveryException("service.device-replacement-error");
            }
        }

        // (3) change service plan
        if(subscription.getServiceType() != dto.getServiceType()) {
            if(!doServiceTypeChange(subscription, dto)) {
                // rollback x 2
                subscription.setServiceStatus(copy.getServiceStatus());
                log.error("Service status change for subscription {} rolled back", subscription.getSubscriptionId());
                subscription.setDevice(copy.getDevice());
                log.error("Device change for subscription {} rolled back", subscription.getSubscriptionId());
                throw new ServiceDeliveryException("service.service-plan-change-error");
            }
        }
        return new SubscriptionDTO(subscription);
    }

    private boolean doServiceTypeChange(Subscription subscription, SubscriptionDTO dto) {
        return false;
    }

    private boolean doDeviceReplacement(Subscription subscription, SubscriptionDTO dto) {
        return false;
    }

    private boolean doSubscriptionStateTransition(Subscription subscription, SubscriptionDTO dto) {
        final ServiceStatus currentState = subscription.getServiceStatus();
        final ServiceStatus desiredNextState = dto.getSubscriptionStatus();

        if(TERMINATED.equals(currentState)) {
            log.info("Status of a terminated subscription cannot be changed.");
            return false;
        }

        if(TERMINATED.equals(desiredNextState)) {
            subscription.setServiceStatus(TERMINATED);
            log.info("Subscription {} terminated", subscription.getSubscriptionId());
            return true;
        }

        if(CREATED.equals(currentState)) {
            if(ACTIVATED.equals(desiredNextState)) {
                subscription.setServiceStatus(ACTIVATED);
                log.info("Subscription {} activated", subscription.getSubscriptionId());
                return true;
            }
        }

        if(ACTIVATED.equals(currentState)) {
            if(SUSPENDED.equals(desiredNextState)) {
                subscription.setServiceStatus(SUSPENDED);
                log.info("Subscription {} suspended", subscription.getSubscriptionId());
                return true;
            }
        }

        if(SUSPENDED.equals(currentState)) {
            if(ACTIVATED.equals(desiredNextState)) {
                subscription.setServiceStatus(ACTIVATED);
                log.info("Subscription {} resumed", subscription.getSubscriptionId());
                return true;
            }
        }
        return false;
    }

    /**
     * Simulate manufacturer device metadata query
     * @param deviceId device id
     * @return Device meta data
     */
    private Optional<Device> queryManufacturerDB(UUID deviceId) {
        if(Objects.isNull(deviceId)) {
            return Optional.empty();
        }
        final Device device = new Device();
        device.setDeviceId(deviceId);
        device.setEndOfLife(LocalDateTime.now().plusYears(5));
        device.setServiceTypes(Arrays.stream(ServiceType.values()).collect(Collectors.toSet()));
        return Optional.of(device);
    }
}

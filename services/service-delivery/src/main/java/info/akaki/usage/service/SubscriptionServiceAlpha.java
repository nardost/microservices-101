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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public SubscriptionDTO createSubscription(SubscriptionDTO dto) {
        SubscriptionDTO.validate(dto);

        final Subscription subscription = dto.toSubscription();
        // Assume device will be leased
        Device device = this.deviceRepository
                .findFirstByServiceTypesContainingAndDeviceStateEquals(
                        dto.getServiceType(),
                        DeviceState.IDLE
                );
        // if subscriber wants to own device, query device metadata in manufacturer db
        if(subscription.getDevice().getDeviceSource() == DeviceSource.OWN) {
            device = queryManufacturerDB(subscription.getDevice().getDeviceId())
                    .orElseThrow(() -> new ServiceDeliveryException("service.device-not-recognized"));
            device.setDeviceSource(DeviceSource.OWN);
        }
        device.setDeviceState(DeviceState.ACTIVE);
        subscription.setDevice(device);
        subscription.setServiceStatus(ServiceStatus.CREATED);
        subscription.setSubscriptionTimestamp(LocalDateTime.now());
        return new SubscriptionDTO(this.subscriptionRepository.saveAndFlush(subscription));
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
        Device device = new Device();
        device.setDeviceId(deviceId);
        device.setEndOfLife(LocalDateTime.now().plusYears(5));
        device.setServiceTypes(Arrays.stream(ServiceType.values()).collect(Collectors.toSet()));
        return Optional.of(device);
    }
}

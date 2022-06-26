package info.akaki.usage.service;

import info.akaki.usage.dto.DeviceDTO;
import info.akaki.usage.dto.SubscriptionDTO;
import info.akaki.usage.entity.DeviceSource;
import info.akaki.usage.entity.DeviceState;
import info.akaki.usage.entity.ServiceStatus;
import info.akaki.usage.entity.ServiceType;
import info.akaki.usage.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("subscriptionService")
@Transactional
@Slf4j
public class SubscriptionServiceAlpha implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final DeviceService deviceService;

    @Autowired
    public SubscriptionServiceAlpha(
            SubscriptionRepository subscriptionRepository,
            DeviceService deviceService) {
        this.subscriptionRepository = subscriptionRepository;
        this.deviceService = deviceService;
    }

    @Override
    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO) {
        if(subscriptionDTO.getDevice().getDeviceSource() == DeviceSource.OWN) {
            // if device is BYOD, create a new Device object (simulate querying manufacturer db to get metadata)
            subscriptionDTO.setDevice(queryManufacturerDB(subscriptionDTO.getDevice().getDeviceId()));
        } else {
            // if device is leased, get a device from db
            subscriptionDTO.setDevice(this.deviceService.getDeviceForServiceType(subscriptionDTO.getServiceType()));
        }
        subscriptionDTO.getDevice().setDeviceState(DeviceState.ACTIVE);
        subscriptionDTO.setServiceStatus(ServiceStatus.CREATED);
        subscriptionDTO.setSubscriptionTimestamp(LocalDateTime.now());
        return new SubscriptionDTO(this.subscriptionRepository.saveAndFlush(subscriptionDTO.toSubscription()));
    }

    /**
     * Simulate manufacturer device metadata query
     * @param deviceId device id
     * @return Device meta data
     */
    private DeviceDTO queryManufacturerDB(UUID deviceId) {
        DeviceDTO dto = new DeviceDTO();
        dto.setDeviceId(deviceId);
        dto.setEndOfLife(LocalDateTime.now().plusYears(5));
        dto.setServiceTypes(Arrays.stream(ServiceType.values()).collect(Collectors.toSet()));
        dto.setDeviceSource(DeviceSource.OWN);
        return dto;
    }
}

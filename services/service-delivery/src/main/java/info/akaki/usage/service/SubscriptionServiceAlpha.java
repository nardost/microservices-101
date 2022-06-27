package info.akaki.usage.service;

import info.akaki.usage.dto.DeviceDTO;
import info.akaki.usage.dto.SubscriptionDTO;
import info.akaki.usage.entity.DeviceSource;
import info.akaki.usage.entity.DeviceState;
import info.akaki.usage.entity.ServiceStatus;
import info.akaki.usage.entity.ServiceType;
import info.akaki.usage.exception.ServiceDeliveryException;
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
    public SubscriptionDTO createSubscription(SubscriptionDTO dto) {
        SubscriptionDTO.validate(dto);
        if(this.subscriptionRepository.existsById(dto.getSubscriptionId())) {
            throw new ServiceDeliveryException("service.subscription-exists");
        }
        if(dto.getDevice().getDeviceSource() == DeviceSource.OWN) {
            // if device is BYOD, create a new Device object (simulate querying manufacturer db to get metadata)
            final DeviceDTO ownDevice = queryManufacturerDB(dto.getDevice().getDeviceId());
            ownDevice.setDeviceSource(DeviceSource.OWN);
            dto.setDevice(ownDevice);

        } else {
            // if device is leased, get a device from db
            final DeviceDTO leasedDevice = this.deviceService.getDeviceForServiceType(dto.getServiceType());
            dto.setDevice(leasedDevice);
        }
        dto.getDevice().setDeviceState(DeviceState.ACTIVE);
        dto.setServiceStatus(ServiceStatus.CREATED);
        dto.setSubscriptionTimestamp(LocalDateTime.now());
        return new SubscriptionDTO(this.subscriptionRepository.saveAndFlush(dto.toSubscription()));
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
        return dto;
    }
}

package info.akaki.usage.service;

import info.akaki.usage.dto.DeviceDTO;
import info.akaki.usage.entity.Device;
import info.akaki.usage.entity.DeviceState;
import info.akaki.usage.entity.ServiceType;
import info.akaki.usage.exception.ServiceDeliveryException;
import info.akaki.usage.repository.DeviceRepository;
import info.akaki.usage.utilities.DevicesFileProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service(value = "deviceServiceAlpha")
@Transactional
@Slf4j
public class DeviceServiceAlpha implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceServiceAlpha(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Collection<DeviceDTO> getDevices() {
        return this.deviceRepository.findAll()
                .stream()
                .map(DeviceDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public DeviceDTO getDeviceByDeviceId(UUID deviceId) {
        return new DeviceDTO(this.deviceRepository
                .findById(deviceId)
                .orElseThrow(() -> new ServiceDeliveryException("device.not-found")));
    }

    @Override
    public DeviceDTO getDeviceForServiceType(ServiceType serviceType) {
        return new DeviceDTO(this.deviceRepository
                .findFirstByServiceTypesContainingAndDeviceStateEquals(serviceType, DeviceState.IDLE));
    }

    @Override
    public DeviceDTO updateDevice(DeviceDTO deviceDTO) {
        DeviceDTO.validate(deviceDTO);
        throw new ServiceDeliveryException("service.method-not-implemented");
        // todo:
        //  - if device exists, do update
        //  - 404 if device does not exist
    }

    @Override
    @Async
    public void bulkSaveDevices(MultipartFile devicesFile) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(devicesFile.getInputStream()))) {
            final Collection<Device> devices = reader
                    .lines()
                    .filter(DevicesFileProcessor::isValidDeviceDataLine)
                    .map(DevicesFileProcessor::toDeviceDTO)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(DeviceDTO::toDevice)
                    .collect(Collectors.toSet());
            this.deviceRepository.saveAll(devices);
        } catch (IOException ioe) {
            throw new ServiceDeliveryException("service.io-exception");
        }
    }

    @Override
    public void changeDeviceState(UUID deviceId, DeviceState deviceState) {
        final Device device = this.deviceRepository
                .findById(deviceId)
                .orElseThrow(() -> new ServiceDeliveryException("service.device-not-found"));
        device.setDeviceState(deviceState);
    }
}

package info.akaki.usage.service;

import info.akaki.usage.dto.DeviceDTO;
import info.akaki.usage.exception.AkakiServiceDeliveryException;
import info.akaki.usage.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service(value = "deviceServiceAlpha")
@Transactional
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
                .orElseThrow(() -> new AkakiServiceDeliveryException("device.not-found")));
    }

    @Override
    public DeviceDTO saveDevice(DeviceDTO deviceDTO) {
        DeviceDTO.validate(deviceDTO);
        return new DeviceDTO(this.deviceRepository.saveAndFlush(deviceDTO.toDevice()));
    }
}

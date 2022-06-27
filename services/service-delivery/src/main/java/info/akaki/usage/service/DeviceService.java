package info.akaki.usage.service;

import info.akaki.usage.dto.DeviceDTO;
import info.akaki.usage.entity.DeviceState;
import info.akaki.usage.entity.ServiceType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.UUID;

public interface DeviceService {
    Collection<DeviceDTO> getDevices();
    DeviceDTO getDeviceByDeviceId(UUID deviceId);
    DeviceDTO getDeviceForServiceType(ServiceType serviceType);
    DeviceDTO updateDevice(DeviceDTO deviceDTO);

    void bulkSaveDevices(MultipartFile devicesFile);

    void changeDeviceState(UUID deviceId, DeviceState active);
}

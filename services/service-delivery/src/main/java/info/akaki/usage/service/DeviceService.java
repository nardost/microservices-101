package info.akaki.usage.service;

import info.akaki.usage.dto.DeviceDTO;

import java.util.Collection;
import java.util.UUID;

public interface DeviceService {
    Collection<DeviceDTO> getDevices();
    DeviceDTO getDeviceByDeviceId(UUID deviceId);
    DeviceDTO saveDevice(DeviceDTO deviceDTO);
}

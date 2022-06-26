package info.akaki.usage.service;

import info.akaki.usage.dto.DeviceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.UUID;

public interface DeviceService {
    Collection<DeviceDTO> getDevices();
    DeviceDTO getDeviceByDeviceId(UUID deviceId);
    DeviceDTO updateDevice(DeviceDTO deviceDTO);

    void bulkSaveDevices(MultipartFile devicesFile);
}

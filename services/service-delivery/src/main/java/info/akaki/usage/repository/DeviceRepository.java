package info.akaki.usage.repository;

import info.akaki.usage.entity.Device;
import info.akaki.usage.entity.DeviceState;
import info.akaki.usage.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    // Get the first available device that can handle serviceType
    Device findFirstByServiceTypesContainingAndDeviceStateEquals(ServiceType serviceType, DeviceState deviceState);
}

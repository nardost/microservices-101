package info.akaki.usage.repository;

import info.akaki.usage.entity.Device;
import info.akaki.usage.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    Device findFirstByServiceTypesContaining(ServiceType serviceType);
}

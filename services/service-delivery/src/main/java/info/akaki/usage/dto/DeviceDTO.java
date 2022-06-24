package info.akaki.usage.dto;

import info.akaki.usage.entity.Device;
import info.akaki.usage.entity.DeviceState;
import info.akaki.usage.entity.ServiceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "deviceId" })
@ToString
public class DeviceDTO {
    private UUID deviceId;
    private DeviceState deviceState;
    private LocalDateTime endOfLife;
    private Collection<ServiceType> serviceTypes;

    public DeviceDTO(Device device) {
        this.deviceId = device.getDeviceId();
        this.deviceState = device.getDeviceState();
        this.endOfLife = device.getEndOfLife();
        this.serviceTypes = device.getServiceTypes();
    }

    public Device toDevice() {
        Device d = new Device();
        d.setDeviceId(this.deviceId);
        d.setDeviceState(this.deviceState);
        d.setEndOfLife(this.endOfLife);
        d.setServiceTypes(new HashSet<>(this.serviceTypes));
        return d;
    }

    public static void validate(DeviceDTO deviceDTO) {
    }
}

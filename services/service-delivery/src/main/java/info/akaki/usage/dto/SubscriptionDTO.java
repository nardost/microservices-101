package info.akaki.usage.dto;

import info.akaki.usage.entity.Device;
import info.akaki.usage.entity.DeviceSource;
import info.akaki.usage.entity.ServiceStatus;
import info.akaki.usage.entity.ServiceType;
import info.akaki.usage.entity.Subscription;
import info.akaki.usage.exception.ServiceDeliveryException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "subscriptionId" })
public class SubscriptionDTO {
    private UUID subscriptionId;
    private UUID deviceId;
    @NotNull(message = "{subscription.device-source.absent}")
    private DeviceSource deviceSource;
    @NotNull(message = "{subscription.service-type.absent}")
    private ServiceType serviceType;
    private LocalDateTime subscriptionTimestamp;
    private ServiceStatus subscriptionStatus;

    public SubscriptionDTO(Subscription subscription) {
        this.subscriptionId = subscription.getSubscriptionId();
        this.subscriptionStatus = subscription.getServiceStatus();
        this.serviceType = subscription.getServiceType();
        this.subscriptionTimestamp = subscription.getSubscriptionTimestamp();
        this.deviceId = subscription.getDevice().getDeviceId();
        this.deviceSource = subscription.getDevice().getDeviceSource();
    }

    public Subscription toSubscription() {
        Subscription s = new Subscription();
        s.setSubscriptionId(this.subscriptionId);
        s.setServiceStatus(this.subscriptionStatus);
        s.setServiceType(this.serviceType);
        s.setSubscriptionTimestamp(this.subscriptionTimestamp);
        /* If device already exists in DB (managed entity),
         * Subscription::setDevice has to be invoked with
         * the managed Device entity as parameter after the
         * Subscription object is created with this method.
         * Otherwise, an exception will be raised that says
         * another entity with the same id exists in the session...
         */
        Device d = new Device();
        d.setDeviceId(this.deviceId);
        d.setDeviceSource(this.deviceSource);
        s.setDevice(d);
        return s;
    }

    public static void validate(SubscriptionDTO dto) {
        if(Objects.isNull(dto)) {
            throw new ServiceDeliveryException("subscription.absent");
        }
        if(Objects.isNull(dto.getDeviceSource())) {
            throw new ServiceDeliveryException("subscription.device-source.absent");
        }
        if(Objects.isNull(dto.getServiceType())) {
            throw new ServiceDeliveryException("subscription.subscription-type.absent");
        }
    }
}

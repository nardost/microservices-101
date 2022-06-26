package info.akaki.usage.dto;

import info.akaki.usage.entity.DeviceSource;
import info.akaki.usage.entity.ServiceStatus;
import info.akaki.usage.entity.ServiceType;
import info.akaki.usage.entity.Subscription;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "subscriptionId" })
public class SubscriptionDTO {
    @NotNull(message = "{subscription-id.absent}")
    private UUID subscriptionId;
    @NotNull(message = "{device-info.absent}")
    private DeviceDTO device;
    @NotNull(message = "{service-type.absent}")
    private ServiceType serviceType;
    private LocalDateTime subscriptionTimestamp;
    private ServiceStatus serviceStatus;

    public SubscriptionDTO(Subscription subscription) {
        this.subscriptionId = subscription.getSubscriptionId();
        this.serviceStatus = subscription.getServiceStatus();
        this.serviceType = subscription.getServiceType();
        this.subscriptionTimestamp = subscription.getSubscriptionTimestamp();
        this.device = new DeviceDTO(subscription.getDevice());
    }

    public Subscription toSubscription() {
        Subscription s = new Subscription();
        s.setSubscriptionId(this.subscriptionId);
        s.setServiceStatus(this.serviceStatus);
        s.setServiceType(this.serviceType);
        s.setSubscriptionTimestamp(this.subscriptionTimestamp);
        s.setDevice(this.device.toDevice());
        return s;
    }
}

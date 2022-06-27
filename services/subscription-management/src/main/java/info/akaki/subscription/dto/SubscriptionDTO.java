package info.akaki.subscription.dto;

import info.akaki.subscription.entity.DeviceSource;
import info.akaki.subscription.entity.Subscription;
import info.akaki.subscription.entity.SubscriptionStatus;
import info.akaki.subscription.exception.SubscriptionManagementException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "{ id }")
@ToString
public class SubscriptionDTO {
    private UUID subscriptionId;
    @NotNull(message = "{subscription.subscriber-id.absent}")
    private UUID subscriberId;
    @NotNull(message = "{subscription.subscription-type.absent}")
    private String serviceType;
    private UUID deviceId;
    @NotNull(message = "{subscription.device-source.absent}")
    private DeviceSource deviceSource;
    private LocalDateTime subscriptionTimestamp;
    private SubscriptionStatus subscriptionStatus;

    public SubscriptionDTO(Subscription subscription) {
        this.subscriptionId = subscription.getId();
        this.subscriberId = subscription.getSubscriberId();
        this.serviceType = subscription.getSubscriptionType();
        this.deviceId = subscription.getDeviceId();
        this.deviceSource = subscription.getDeviceSource();
        this.subscriptionTimestamp = subscription.getSubscriptionTimestamp();
        this.subscriptionStatus = subscription.getSubscriptionStatus();
    }

    public Subscription toSubscription() {
        Subscription s = new Subscription();
        s.setId(this.subscriptionId);
        s.setSubscriberId(this.subscriberId);
        s.setSubscriptionType(this.serviceType);
        s.setDeviceId(this.deviceId);
        s.setDeviceSource(this.deviceSource);
        s.setSubscriptionTimestamp(this.subscriptionTimestamp);
        s.setSubscriptionStatus(this.subscriptionStatus);
        return s;
    }

    public static void validate(SubscriptionDTO dto) {
        if(Objects.isNull(dto)) {
            throw new SubscriptionManagementException("subscription.absent");
        }
        if(dto.getDeviceSource() == DeviceSource.OWN && Objects.isNull(dto.getDeviceId())) {
            throw new SubscriptionManagementException(("subscription.device-source.ambiguous"));
        }
        if(dto.getDeviceSource() == DeviceSource.LEASE && Objects.nonNull(dto.getDeviceId())) {
            throw new SubscriptionManagementException(("subscription.device-source.ambiguous"));
        }
    }
}

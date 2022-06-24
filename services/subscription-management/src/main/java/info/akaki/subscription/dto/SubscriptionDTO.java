package info.akaki.subscription.dto;

import info.akaki.subscription.entity.Subscription;
import info.akaki.subscription.entity.SubscriptionStatus;
import info.akaki.subscription.exception.AkakiUtilityException;
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
@EqualsAndHashCode(of = "{ id }")
public class SubscriptionDTO {
    private UUID id;
    @NotNull(message = "{subscriber-id.absent}")
    private UUID subscriberId;
    @NotNull(message = "{plan-id.absent}")
    private UUID planId;
    @NotNull(message = "{device-id.absent}")
    private UUID deviceId;
    private LocalDateTime subscriptionTimestamp;
    private SubscriptionStatus status;

    public SubscriptionDTO(Subscription subscription) {
        this.id = subscription.getId();
        this.subscriberId = subscription.getSubscriberId();
        this.planId = subscription.getPlanId();
        this.deviceId = subscription.getDeviceId();
        this.subscriptionTimestamp = subscription.getSubscriptionTimestamp();
        this.status = subscription.getStatus();
    }

    public Subscription toSubscription() {
        Subscription s = new Subscription();
        s.setId(this.id);
        s.setSubscriberId(this.subscriberId);
        s.setPlanId(this.planId);
        s.setDeviceId(this.deviceId);
        s.setSubscriptionTimestamp(this.subscriptionTimestamp);
        s.setStatus(this.status);
        return s;
    }

    public static void validate(SubscriptionDTO dto) {
        if(Objects.isNull(dto)) {
            throw new AkakiUtilityException("subscription.absent");
        }
    }
}

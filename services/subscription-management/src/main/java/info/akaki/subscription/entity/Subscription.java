package info.akaki.subscription.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Subscription {
    @Id
    private UUID id;
    private UUID subscriberId;
    private String subscriptionType;
    private UUID deviceId;
    @Enumerated(EnumType.STRING)
    private DeviceSource deviceSource;
    private LocalDateTime subscriptionTimestamp;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;
}

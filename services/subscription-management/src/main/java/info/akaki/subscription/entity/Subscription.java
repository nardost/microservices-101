package info.akaki.subscription.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
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
    @GeneratedValue(generator = "subscription_uuid_generator")
    @GenericGenerator(name = "subscription_uuid_generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private UUID subscriberId;
    private UUID planId;
    private UUID deviceId;
    private LocalDateTime subscriptionTimestamp;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;
}

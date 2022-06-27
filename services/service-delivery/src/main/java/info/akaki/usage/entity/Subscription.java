package info.akaki.usage.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "subscriptionId" })
public class Subscription {
    @Id
    @GeneratedValue(generator = "subscription_uuid_generator")
    @GenericGenerator(name = "subscription_uuid_generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID subscriptionId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id")
    private Device device;
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;
    private LocalDateTime subscriptionTimestamp;
    @Enumerated(EnumType.STRING)
    private ServiceStatus serviceStatus;
}

package info.akaki.usage.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "deviceId" })
public class Device {
    @Id
    private UUID deviceId;
    @Enumerated(EnumType.STRING)
    private DeviceState deviceState;
    @Enumerated(EnumType.STRING)
    private DeviceSource deviceSource;
    private LocalDateTime endOfLife;
    @ElementCollection(targetClass = ServiceType.class)
    @Column(name = "service_type", nullable = false)
    @CollectionTable(name = "device_service_types", joinColumns = @JoinColumn(name = "device_id"))
    @Enumerated(EnumType.STRING)
    private Collection<ServiceType> serviceTypes;
}

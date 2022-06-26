package info.akaki.subscription.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "plans")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Plan {
    @Id
    @GeneratedValue(generator = "utility_uuid_generator")
    @GenericGenerator(name = "utility_uuid_generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String serviceType;
    private double baseUnits;
    private double maxUnits;
    private BigDecimal ratePerUnit;
}

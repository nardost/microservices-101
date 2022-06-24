package info.akaki.subscription.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usages")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Usage {
    @Id
    @SequenceGenerator(name = "usage_id_generator", sequenceName = "usage_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usage_id_generator")
    private long id;
    private UUID subscriptionId;
    private LocalDateTime timestamp;
    private long usageAmount;
}

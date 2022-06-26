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
import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Address {
    @Id
    @GeneratedValue(generator = "uuid_gen")
    @GenericGenerator(name = "uuid_gen", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String street;
    private String city;
    private String zipCode;
    @Enumerated(EnumType.STRING)
    private USState USState;
}

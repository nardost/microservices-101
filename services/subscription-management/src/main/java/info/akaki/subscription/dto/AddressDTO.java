package info.akaki.subscription.dto;

import info.akaki.subscription.entity.Address;
import info.akaki.subscription.entity.USState;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

import static info.akaki.subscription.utilities.Constants.MAX_LENGTH_CITY;
import static info.akaki.subscription.utilities.Constants.MAX_LENGTH_STREET_ADDRESS;
import static info.akaki.subscription.utilities.Constants.REGEX_UUID;
import static info.akaki.subscription.utilities.Constants.REGEX_ZIP_CODE;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class AddressDTO {
    @Pattern(regexp = REGEX_UUID, message = "validation.uuid.invalid")
    private String id;
    @Size(max = MAX_LENGTH_STREET_ADDRESS, message = "validation.street-address.length")
    private String street;
    @Size(max = MAX_LENGTH_CITY, message = "validation.city.length")
    private String city;
    @Pattern(regexp = REGEX_ZIP_CODE, message = "validation.zip-code.invalid")
    private String zipCode;
    private USState USState;

    public AddressDTO(Address address) {
        this.id = address.getId().toString();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.zipCode = address.getZipCode();
        this.USState = address.getUSState();
    }

    public Address toAddress() {
        Address a = new Address();
        a.setId(Objects.nonNull(this.id) ? UUID.fromString(this.id) : null);
        a.setStreet(this.street);
        a.setCity(this.city);
        a.setZipCode(this.zipCode);
        a.setUSState(this.USState);
        return a;
    }
}

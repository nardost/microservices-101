package info.akaki.subscription.dto;

import info.akaki.subscription.entity.Address;
import info.akaki.subscription.entity.USState;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

import static info.akaki.subscription.utilities.Constants.MAX_LENGTH_CITY;
import static info.akaki.subscription.utilities.Constants.MAX_LENGTH_STREET_ADDRESS;
import static info.akaki.subscription.utilities.Constants.REGEX_ZIP_CODE;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class AddressDTO {
    private UUID id;
    @Size(max = MAX_LENGTH_STREET_ADDRESS, message = "address.street-address.too-long")
    private String street;
    @Size(max = MAX_LENGTH_CITY, message = "address.city.too-long")
    private String city;
    @Pattern(regexp = REGEX_ZIP_CODE, message = "address.zip-code.invalid")
    private String zipCode;
    private USState state;

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.zipCode = address.getZipCode();
        this.state = address.getState();
    }

    public Address toAddress() {
        Address a = new Address();
        a.setId(this.id);
        a.setStreet(this.street);
        a.setCity(this.city);
        a.setZipCode(this.zipCode);
        a.setState(this.state);
        return a;
    }
}

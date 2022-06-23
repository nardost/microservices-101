package info.akaki.customers.dto;

import info.akaki.customers.entity.Customer;
import info.akaki.customers.exception.AkakiValidationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

import static info.akaki.customers.utilities.Constants.MAX_LENGTH_EMAIL;
import static info.akaki.customers.utilities.Constants.MAX_LENGTH_NAME;
import static info.akaki.customers.utilities.Constants.MAX_LENGTH_PHONE_NUMBER;
import static info.akaki.customers.utilities.Constants.MIN_LENGTH_NAME;
import static info.akaki.customers.utilities.Constants.REGEX_PHONE;
import static info.akaki.customers.utilities.Constants.REGEX_UUID;
import static info.akaki.customers.utilities.Validators.validateCity;
import static info.akaki.customers.utilities.Validators.validateEmail;
import static info.akaki.customers.utilities.Validators.validateName;
import static info.akaki.customers.utilities.Validators.validatePhoneNumber;
import static info.akaki.customers.utilities.Validators.validateStreetAddress;
import static info.akaki.customers.utilities.Validators.validateUUID;
import static info.akaki.customers.utilities.Validators.validateZipCode;
import static java.lang.Boolean.FALSE;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class CustomerDTO {
    @Pattern(regexp = REGEX_UUID, message = "{uuid.invalid}")
    private String id;
    @Size(min = MIN_LENGTH_NAME, max = MAX_LENGTH_NAME, message = "{name.length.invalid}")
    private String firstName;
    @Size(min = MIN_LENGTH_NAME, max = MAX_LENGTH_NAME, message = "{name.length.invalid}")
    private String lastName;
    @Email(message = "{email.invalid}")
    @Size(max = MAX_LENGTH_EMAIL, message = "{email.length.invalid}")
    private String emailId;
    @Pattern(regexp = REGEX_PHONE, message = "{phone.format.invalid}")
    @Size(max = MAX_LENGTH_PHONE_NUMBER, message = "{phone.length.invalid}")
    private String phoneNumber;
    private AddressDTO address;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId().toString();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.emailId = customer.getEmailId();
        this.phoneNumber = customer.getPhoneNumber();
        this.address = Objects.nonNull(customer.getAddress()) ? new AddressDTO(customer.getAddress()) : null;
    }

    public Customer toCustomer() {
        Customer c = new Customer();
        c.setId(Objects.nonNull(this.id) ? UUID.fromString(this.id) : null);
        c.setFirstName(this.firstName);
        c.setLastName(this.lastName);
        c.setEmailId(this.emailId);
        c.setPhoneNumber(this.phoneNumber);
        c.setAddress(Objects.nonNull(this.address) ? this.address.toAddress() : null);
        return c;
    }

    public static void validate(CustomerDTO dto) {
        if(Objects.nonNull(dto.getId()) && FALSE.equals(validateUUID(dto.getId()))) {
            throw new AkakiValidationException("validation.uuid.invalid");
        }
        if(Objects.nonNull(dto.getEmailId()) && FALSE.equals(validateEmail(dto.getEmailId()))) {
            throw new AkakiValidationException("validation.email.invalid");
        }
        if(Objects.nonNull(dto.getPhoneNumber()) && FALSE.equals(validatePhoneNumber(dto.getPhoneNumber()))) {
            throw new AkakiValidationException("validation.phone.invalid");
        }
        if(Objects.nonNull(dto.getFirstName()) && FALSE.equals(validateName(dto.getFirstName()))) {
            throw new AkakiValidationException("validation.name.invalid");
        }
        if(Objects.nonNull(dto.getLastName()) && FALSE.equals(validateName(dto.getLastName()))) {
            throw new AkakiValidationException("validation.name.invalid");
        }
        if(Objects.nonNull(dto.getAddress())) {
            if(FALSE.equals(validateStreetAddress(dto.getAddress().getStreet()))) {
                throw new AkakiValidationException("validation.street-address.invalid");
            }
            if(FALSE.equals(validateCity(dto.getAddress().getCity()))) {
                throw new AkakiValidationException("validation.city.invalid");
            }
            if(FALSE.equals(validateZipCode(dto.getAddress().getZipCode()))) {
                throw new AkakiValidationException("validation.zip-code.invalid");
            }
        }
    }
}

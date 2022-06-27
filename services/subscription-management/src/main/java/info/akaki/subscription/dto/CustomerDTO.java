package info.akaki.subscription.dto;

import info.akaki.subscription.entity.Customer;
import info.akaki.subscription.exception.SubscriptionManagementException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

import static info.akaki.subscription.utilities.Constants.MAX_LENGTH_EMAIL;
import static info.akaki.subscription.utilities.Constants.MAX_LENGTH_NAME;
import static info.akaki.subscription.utilities.Constants.MAX_LENGTH_PHONE_NUMBER;
import static info.akaki.subscription.utilities.Constants.MIN_LENGTH_NAME;
import static info.akaki.subscription.utilities.Constants.REGEX_PHONE;
import static info.akaki.subscription.utilities.Validators.validateCity;
import static info.akaki.subscription.utilities.Validators.validateEmail;
import static info.akaki.subscription.utilities.Validators.validateName;
import static info.akaki.subscription.utilities.Validators.validatePhoneNumber;
import static info.akaki.subscription.utilities.Validators.validateStreetAddress;
import static info.akaki.subscription.utilities.Validators.validateZipCode;
import static java.lang.Boolean.FALSE;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class CustomerDTO {
    private UUID id;
    @Size(min = MIN_LENGTH_NAME, max = MAX_LENGTH_NAME, message = "{customer.name.invalid}")
    private String firstName;
    @Size(min = MIN_LENGTH_NAME, max = MAX_LENGTH_NAME, message = "{customer.name.invalid}")
    private String lastName;
    @Email(message = "{email.invalid}")
    @Size(max = MAX_LENGTH_EMAIL, message = "{customer.email.invalid}")
    private String emailId;
    @Pattern(regexp = REGEX_PHONE, message = "{customer.phone.invalid}")
    @Size(max = MAX_LENGTH_PHONE_NUMBER, message = "{customer.phone.invalid}")
    private String phoneNumber;
    private AddressDTO address;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.emailId = customer.getEmailId();
        this.phoneNumber = customer.getPhoneNumber();
        this.address = Objects.nonNull(customer.getAddress()) ? new AddressDTO(customer.getAddress()) : null;
    }

    public Customer toCustomer() {
        Customer c = new Customer();
        c.setId(this.id);
        c.setFirstName(this.firstName);
        c.setLastName(this.lastName);
        c.setEmailId(this.emailId);
        c.setPhoneNumber(this.phoneNumber);
        c.setAddress(Objects.nonNull(this.address) ? this.address.toAddress() : null);
        return c;
    }

    public static void validate(CustomerDTO dto) {
        if(Objects.isNull(dto)) {
            throw new SubscriptionManagementException("customer.absent");
        }
        if(Objects.nonNull(dto.getEmailId()) && FALSE.equals(validateEmail(dto.getEmailId()))) {
            throw new SubscriptionManagementException("customer.email.invalid");
        }
        if(Objects.nonNull(dto.getPhoneNumber()) && FALSE.equals(validatePhoneNumber(dto.getPhoneNumber()))) {
            throw new SubscriptionManagementException("customer.phone.invalid");
        }
        if(Objects.nonNull(dto.getFirstName()) && FALSE.equals(validateName(dto.getFirstName()))) {
            throw new SubscriptionManagementException("customer.name.invalid");
        }
        if(Objects.nonNull(dto.getLastName()) && FALSE.equals(validateName(dto.getLastName()))) {
            throw new SubscriptionManagementException("customer.name.invalid");
        }
        if(Objects.nonNull(dto.getAddress())) {
            if(FALSE.equals(validateStreetAddress(dto.getAddress().getStreet()))) {
                throw new SubscriptionManagementException("customer.address.street-address.invalid");
            }
            if(FALSE.equals(validateCity(dto.getAddress().getCity()))) {
                throw new SubscriptionManagementException("customer.address.city.invalid");
            }
            if(FALSE.equals(validateZipCode(dto.getAddress().getZipCode()))) {
                throw new SubscriptionManagementException("customer.address.zip-code.invalid");
            }
        }
    }
}

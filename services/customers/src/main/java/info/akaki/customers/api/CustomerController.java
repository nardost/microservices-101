package info.akaki.customers.api;

import info.akaki.customers.dto.CustomerDTO;
import info.akaki.customers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.UUID;

import static info.akaki.customers.utilities.Constants.REGEX_UUID;

@RestController
@RequestMapping(path = CustomerController.CUSTOMER_API_URL)
@Validated
public class CustomerController {
    public static final String CUSTOMER_API_URL = "api/v1/customers";

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Collection<CustomerDTO>> getCustomers() {
        return new ResponseEntity<>(this.customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(
            @PathVariable("customerId")
            @NotNull(message = "{uuid.absent}")
            UUID customerId) {
        return new ResponseEntity<>(this.customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(this.customerService.saveCustomer(customerDTO), HttpStatus.CREATED);
    }
}

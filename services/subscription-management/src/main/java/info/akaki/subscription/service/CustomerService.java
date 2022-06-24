package info.akaki.subscription.service;

import info.akaki.subscription.dto.CustomerDTO;

import java.util.Collection;
import java.util.UUID;

public interface CustomerService {
    Collection<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(UUID customerId);
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
}

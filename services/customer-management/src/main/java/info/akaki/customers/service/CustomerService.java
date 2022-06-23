package info.akaki.customers.service;

import info.akaki.customers.dto.CustomerDTO;

import java.util.Collection;
import java.util.UUID;

public interface CustomerService {
    Collection<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(UUID customerId);
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
}

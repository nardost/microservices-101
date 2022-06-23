package info.akaki.customers.service;

import info.akaki.customers.dto.CustomerDTO;

import java.util.Collection;

public interface CustomerService {
    Collection<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(String customerId);
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
}

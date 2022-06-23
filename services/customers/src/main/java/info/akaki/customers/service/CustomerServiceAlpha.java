package info.akaki.customers.service;

import info.akaki.customers.dto.CustomerDTO;
import info.akaki.customers.exception.AkakiServiceException;
import info.akaki.customers.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service(value = "customerServiceAlpha")
@Transactional
public class CustomerServiceAlpha implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceAlpha(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Collection<CustomerDTO> getAllCustomers() {
        return this.customerRepository.findAll()
                .stream()
                .map(CustomerDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(String customerId) {
        return new CustomerDTO(this.customerRepository
                .findById(UUID.fromString(customerId))
                .orElseThrow(() -> new AkakiServiceException("service.resource-not-found")));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO dto) {
        CustomerDTO.validate(dto);
        return new CustomerDTO(this.customerRepository.save(dto.toCustomer()));
    }
}

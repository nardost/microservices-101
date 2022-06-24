package info.akaki.subscription.service;

import info.akaki.subscription.dto.CustomerDTO;
import info.akaki.subscription.exception.AkakiCustomersServiceException;
import info.akaki.subscription.repository.CustomerRepository;
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
    public CustomerDTO getCustomerById(UUID customerId) {
        return new CustomerDTO(this.customerRepository
                .findById(customerId)
                .orElseThrow(() -> new AkakiCustomersServiceException("service.customer.not-found")));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO dto) {
        CustomerDTO.validate(dto);
        return new CustomerDTO(this.customerRepository.saveAndFlush(dto.toCustomer()));
    }
}

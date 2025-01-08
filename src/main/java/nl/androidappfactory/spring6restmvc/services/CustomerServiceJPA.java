package nl.androidappfactory.spring6restmvc.services;

import lombok.AllArgsConstructor;
import nl.androidappfactory.spring6restmvc.entities.Customer;
import nl.androidappfactory.spring6restmvc.mappers.CustomerMapper;
import nl.androidappfactory.spring6restmvc.model.CustomerDTO;
import nl.androidappfactory.spring6restmvc.repositories.CustomerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class  CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDTO(customerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer savedCustomer = customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO));
        return  customerMapper.customerToCustomerDTO(savedCustomer);
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO customerDTO) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(id).ifPresentOrElse( existingCustomer  -> {
            existingCustomer.setName(customerDTO.getName());
            existingCustomer.setModifiedAt(LocalDateTime.now());
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDTO(customerRepository.save(existingCustomer))));
        }, () -> atomicReference.set(Optional.empty()));
        return atomicReference.get();
    }

    @Override
    public boolean delete(UUID id) {
        if (!customerRepository.existsById(id)) {
            return false;
        }
        customerRepository.deleteAll();
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomer(UUID id, CustomerDTO updateCustomer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer == null) {
            return Optional.empty();
        }
        existingCustomer.setName(updateCustomer.getName());
        return Optional.of(customerMapper.customerToCustomerDTO(customerRepository.save(existingCustomer)));
    }
}

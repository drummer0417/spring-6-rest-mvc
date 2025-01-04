package nl.androidappfactory.spring6restmvc.services;

import lombok.AllArgsConstructor;
import nl.androidappfactory.spring6restmvc.mappers.BeerMapper;
import nl.androidappfactory.spring6restmvc.mappers.CustomerMapper;
import nl.androidappfactory.spring6restmvc.model.CustomerDTO;
import nl.androidappfactory.spring6restmvc.repositories.CustomerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class  CustomerServiceJPA implements CustomerService {
    private final BeerMapper beerMapper;
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
        return null;
    }

    @Override
    public void updateCustomer(UUID id, CustomerDTO customerDTO) {

    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public void patchCustomer(UUID id, CustomerDTO customerDTO) {

    }
}

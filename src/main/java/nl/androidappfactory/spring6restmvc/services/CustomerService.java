package nl.androidappfactory.spring6restmvc.services;

import nl.androidappfactory.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> getAll();

    Optional<CustomerDTO> getById(UUID id);

    CustomerDTO addCustomer(CustomerDTO customerDTO);

    void updateCustomer(UUID id, CustomerDTO customerDTO);

    void delete(UUID id);

    void patchCustomer(UUID id, CustomerDTO customerDTO);
}

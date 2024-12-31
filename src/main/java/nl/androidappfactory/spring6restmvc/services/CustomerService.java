package nl.androidappfactory.spring6restmvc.services;

import nl.androidappfactory.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getAll();

    Customer getById(UUID id);

    Customer addCustomer(Customer customer);
}

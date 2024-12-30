package nl.androidappfactory.spring6restmvc.services;

import nl.androidappfactory.spring6restmvc.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();

    Customer getById(int id);

}

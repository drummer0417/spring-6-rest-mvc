package nl.androidappfactory.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.Customer;
import nl.androidappfactory.spring6restmvc.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {
    private CustomerService customerService;

    @RequestMapping(value = "{customer-id}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable("customer-id") int id) {
        return customerService.getById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        return customerService.getAll();
    }
}

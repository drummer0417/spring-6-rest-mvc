package nl.androidappfactory.spring6restmvc.controllers;

import nl.androidappfactory.spring6restmvc.model.CustomerDTO;
import nl.androidappfactory.spring6restmvc.repositories.CustomerRepository;
import nl.androidappfactory.spring6restmvc.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerRepository customerRepository;
    @Qualifier("customerServiceJPA")
    @Autowired
    private CustomerService customerService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getAllCustomers() {
        List<CustomerDTO> customers = customerController.getAllCustomers();
        customers.forEach(System.out::println);
        assertThat(customers.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void getAllCutomersFromEmptyTable() {
        customerRepository.deleteAll();
        List<CustomerDTO> customers = customerController.getAllCustomers();
        assertThat(customers.size()).isEqualTo(0);
    }

    @Test
    void findCustomerById() {
        CustomerDTO customer = customerController.getAllCustomers().getFirst();
        CustomerDTO actualCustomer = customerController.getCustomerById(customer.getId());
        assertThat(actualCustomer).isEqualTo(customer);
    }

    @Test
    void findCustomerByUnknownId() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }
}
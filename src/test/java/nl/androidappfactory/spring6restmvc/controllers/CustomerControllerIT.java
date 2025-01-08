package nl.androidappfactory.spring6restmvc.controllers;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.entities.Customer;
import nl.androidappfactory.spring6restmvc.mappers.CustomerMapper;
import nl.androidappfactory.spring6restmvc.model.CustomerDTO;
import nl.androidappfactory.spring6restmvc.repositories.CustomerRepository;
import nl.androidappfactory.spring6restmvc.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class CustomerControllerIT {

    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerRepository customerRepository;
    @Qualifier("customerServiceJPA")
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;

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
        assertThrows(NotFoundException.class, () ->
                customerController.getCustomerById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void saveNewCustomer() {
        CustomerDTO newCustomer = CustomerDTO.builder()
                .name("New Customer")
                .build();

        ResponseEntity<CustomerDTO> responseEntity = customerController.addCustomer(newCustomer);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        log.debug(responseEntity.getHeaders().getLocation().toString() + ".................");
        String[] locationHeader = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUuid = UUID.fromString(locationHeader[4]);

        Customer customer = customerRepository.findById(savedUuid).get();

        assertThat(savedUuid).isEqualTo(customer.getId());
    }

    @Rollback
    @Transactional
    @Test
    void udateCustomer() {
        CustomerDTO customer = customerController.getAllCustomers().getFirst();

        customer.setName("UPDATED Customer");
        customerService.updateCustomer(customer.getId(), customer);

        ResponseEntity<CustomerDTO> responseEntity = customerController.updateCustomer(customer.getId(), customer);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        CustomerDTO updatedCustomer = customerMapper.customerToCustomerDTO(customerRepository.findById(customer.getId()).get());
        assertThat("UPDATED Customer").isEqualTo(updatedCustomer.getName());
    }

    @Rollback
    @Transactional
    @Test
    void updateCustomerDoesNotExist() {
        assertThrows(NotFoundException.class, () ->
                customerController.updateCustomer(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Rollback
    @Transactional
    @Test
    void deleteCustomerById() {
        CustomerDTO customer = customerController.getAllCustomers().getFirst();

        ResponseEntity<CustomerDTO> responseEntity = customerController.deleteCustomer(customer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

    }

    @Test
    void deleteCustomerDoesNotExist() {

        assertThrows(NotFoundException.class, () ->
                customerController.deleteCustomer(UUID.randomUUID()));
    }

    @Test
    void patchCustomer() {
        CustomerDTO existingCustomer = customerController.getAllCustomers().getFirst();
        CustomerDTO patchCustomer = CustomerDTO.builder()
                .name("Patched Customer")
                .build();
        ResponseEntity<CustomerDTO> responseEntity = customerController.patchCustomer(existingCustomer.getId(), patchCustomer);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }

    @Test
    void patchCustomerDoesNotExist() {
        CustomerDTO patchCustomer = CustomerDTO.builder().build();

        assertThrows(NotFoundException.class, () ->
                customerController.patchCustomer(UUID.randomUUID(), patchCustomer)
        );
    }
}


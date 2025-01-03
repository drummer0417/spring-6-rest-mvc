package nl.androidappfactory.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.Customer;
import nl.androidappfactory.spring6restmvc.services.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
//@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {
    private CustomerService customerService;

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customer-id}";

    @GetMapping(CUSTOMER_PATH_ID)
    public Customer getCustomer(@PathVariable("customer-id") UUID id) {
        return customerService.getById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(CUSTOMER_PATH)
    public List<Customer> getAllCustomers() {
        return customerService.getAll();
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.addCustomer(customer);
        log.info("New customer created: {}", savedCustomer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customer-id") UUID id, @RequestBody Customer customer) {
        customerService.updateCustomer(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> patchCustomer(@PathVariable("customer-id") UUID id, @RequestBody Customer customer) {
        customerService.patchCustomer(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customer-id") UUID id) {
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // moved mathod below to the ExceptionController so it works global (i.e. also for BeerController)
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity handleNotFoundException () {
//        log.debug("in handleNotFoundException.................. ");
//        return ResponseEntity.notFound().build();
//    }
}

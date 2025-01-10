package nl.androidappfactory.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.CustomerDTO;
import nl.androidappfactory.spring6restmvc.services.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
public class CustomerController {
    private CustomerService customerService;

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customer-id}";

    @GetMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("customer-id") UUID id) {
        return customerService.getById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAll();
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<CustomerDTO> addCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomerDTO = customerService.addCustomer(customerDTO);
        log.info("New customer created: {}", savedCustomerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomerDTO.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("customer-id") UUID id, @RequestBody CustomerDTO customerDTO) {
        if (customerService.updateCustomer(id, customerDTO).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable("customer-id") UUID id, @Validated @RequestBody CustomerDTO customerDTO) {
        if (customerService.patchCustomer(id, customerDTO).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable("customer-id") UUID id) {

        if (!customerService.delete(id)) {
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

// moved mathod below to the ExceptionController so it works global (i.e. also for BeerController)
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity handleNotFoundException () {
//        log.debug("in handleNotFoundException.................. ");
//        return ResponseEntity.notFound().build();
//    }
}

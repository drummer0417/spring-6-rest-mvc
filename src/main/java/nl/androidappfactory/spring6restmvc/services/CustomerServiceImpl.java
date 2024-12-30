package nl.androidappfactory.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final Map<Integer, Customer> customers;

    public CustomerServiceImpl() {
        Customer cutomer1 = Customer.builder()
                .id(1)
                .name("Hans")
                .version("1.0")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        Customer cutomer2 = Customer.builder()
                .id(2)
                .name("Jacky")
                .version("1.0")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        Customer cutomer3 = Customer.builder()
                .id(3)
                .name("Kees")
                .version("1.0")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        this.customers = new HashMap<>();
        this.customers.put(cutomer1.getId(), cutomer1);
        this.customers.put(cutomer2.getId(), cutomer2);
        this.customers.put(cutomer3.getId(), cutomer3);
        log.debug(String.format("CustomerService created %s customers", customers.size()));
    }

    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer getById(int id) {
        return customers.get(id);
    }
}

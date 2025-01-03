package nl.androidappfactory.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, Customer> customers;

    public CustomerServiceImpl() {
        Customer cutomer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Hans")
                .version("1.0")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        Customer cutomer2 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Jacky")
                .version("1.0")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        Customer cutomer3 = Customer.builder()
                .id(UUID.randomUUID())
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
    public Optional<Customer> getById(UUID id) {
        return Optional.of(customers.get(id));
    }

    @Override
    public Customer addCustomer(Customer customer) {
        Customer newCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .version("1.0")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .name(customer.getName())
                .build();
        customers.put(newCustomer.getId(), newCustomer);
        return newCustomer;
    }

    @Override
    public void updateCustomer(UUID id, Customer customer) {
        Customer existingCustomer = customers.get(id);
        existingCustomer.setName(customer.getName());
        existingCustomer.setModifiedAt(LocalDateTime.now());
        existingCustomer.setVersion("2.0");
        customers.put(id, existingCustomer);
    }

    @Override
    public void delete(UUID id) {
        customers.remove(id);
        log.debug(String.format("CustomerService deleted %s", id));
        log.debug(String.format("CustomerService deleted. # customers %s", customers.size()));
    }

    @Override
    public void patchCustomer(UUID id, Customer customer) {
        Customer currentCustomer = customers.get(id);
        if (StringUtils.hasText(customer.getName())) {
            currentCustomer.setName(customer.getName());
        }
        if (StringUtils.hasText(customer.getVersion())) {
            currentCustomer.setVersion(customer.getVersion());
        }
        currentCustomer.setModifiedAt(LocalDateTime.now());
    }
}

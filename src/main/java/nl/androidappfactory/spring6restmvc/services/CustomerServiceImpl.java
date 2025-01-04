package nl.androidappfactory.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, CustomerDTO> customers;

    public CustomerServiceImpl() {
        CustomerDTO cutomer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Hans")
                .version("1.0")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        CustomerDTO cutomer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Jacky")
                .version("1.0")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        CustomerDTO cutomer3 = CustomerDTO.builder()
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
    public List<CustomerDTO> getAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Optional<CustomerDTO> getById(UUID id) {
        return Optional.of(customers.get(id));
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        CustomerDTO newCustomerDTO = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version("1.0")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .name(customerDTO.getName())
                .build();
        customers.put(newCustomerDTO.getId(), newCustomerDTO);
        return newCustomerDTO;
    }

    @Override
    public void updateCustomer(UUID id, CustomerDTO customerDTO) {
        CustomerDTO existingCustomerDTO = customers.get(id);
        existingCustomerDTO.setName(customerDTO.getName());
        existingCustomerDTO.setModifiedAt(LocalDateTime.now());
        existingCustomerDTO.setVersion("2.0");
        customers.put(id, existingCustomerDTO);
    }

    @Override
    public void delete(UUID id) {
        customers.remove(id);
        log.debug(String.format("CustomerService deleted %s", id));
        log.debug(String.format("CustomerService deleted. # customers %s", customers.size()));
    }

    @Override
    public void patchCustomer(UUID id, CustomerDTO customerDTO) {
        CustomerDTO currentCustomerDTO = customers.get(id);
        if (StringUtils.hasText(customerDTO.getName())) {
            currentCustomerDTO.setName(customerDTO.getName());
        }
        if (StringUtils.hasText(customerDTO.getVersion())) {
            currentCustomerDTO.setVersion(customerDTO.getVersion());
        }
        currentCustomerDTO.setModifiedAt(LocalDateTime.now());
    }
}

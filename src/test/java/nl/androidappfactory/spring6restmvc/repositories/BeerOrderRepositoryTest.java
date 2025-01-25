package nl.androidappfactory.spring6restmvc.repositories;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.entities.Beer;
import nl.androidappfactory.spring6restmvc.entities.BeerOrder;
import nl.androidappfactory.spring6restmvc.entities.BeerOrderShipment;
import nl.androidappfactory.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;
    @Autowired
    BeerRepository beerRepository;
    @Autowired
    CustomerRepository customerRepository;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        testBeer = beerRepository.findAll().getFirst();
        testCustomer = customerRepository.findAll().getFirst();
    }
    @Transactional
    @Test
    void testOrders() {
        BeerOrder beerOrder = BeerOrder.builder()
                .customerRef("Hans' first beer order")
                .customer(testCustomer)
                .beerOrderShipment(BeerOrderShipment.builder()
                        .trackingNumber("LKJLKJ345345")
                        .build())
                .build();
        BeerOrder savedOrder = beerOrderRepository.save(beerOrder);

        assertThat(savedOrder.getCustomer().getId()).isEqualTo(testCustomer.getId());
    }
}

package nl.androidappfactory.spring6restmvc.bootstrap;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.entities.Beer;
import nl.androidappfactory.spring6restmvc.entities.Customer;
import nl.androidappfactory.spring6restmvc.model.BeerStyle;
import nl.androidappfactory.spring6restmvc.repositories.BeerRepository;
import nl.androidappfactory.spring6restmvc.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Slf4j
@AllArgsConstructor
public class BootstrapData implements CommandLineRunner {

    BeerRepository beerRepository;
    CustomerRepository customerRepository;

    @Override
    public void run(String... args) {

        initBeerData();
        initCustomerData();

        log.debug("{} beers created", beerRepository.count());
        log.debug("{} customers created", customerRepository.count());
    }

    public void initBeerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Hertog Jan")
                    .version(1L)
                    .beerStyle(BeerStyle.LAGER)
                    .upc("12356")
                    .price(new BigDecimal("13.90"))
                    .quantityOnHand(24)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Bavaria")
                    .version(1L)
                    .beerStyle(BeerStyle.LAGER)
                    .upc("12356222")
                    .price(new BigDecimal("7.99"))
                    .quantityOnHand(12)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Corona")
                    .version(1L)
                    .beerStyle(BeerStyle.PILSNER)
                    .upc("12356")
                    .price(new BigDecimal("9.99"))
                    .quantityOnHand(6)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3));
            log.debug(beerRepository.findAll().getFirst().getBeerName());

        }
    }

    public void initCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .name("Hans")
                    .version(1L)
                    .createdAt(LocalDateTime.now())
                    .modifiedAt(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .name("Jacky")
                    .version(1L)
                    .createdAt(LocalDateTime.now())
                    .modifiedAt(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .name("Kees")
                    .version(1L)
                    .createdAt(LocalDateTime.now())
                    .modifiedAt(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }
}

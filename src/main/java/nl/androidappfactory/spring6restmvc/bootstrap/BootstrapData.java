package nl.androidappfactory.spring6restmvc.bootstrap;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.entities.Beer;
import nl.androidappfactory.spring6restmvc.entities.Customer;
import nl.androidappfactory.spring6restmvc.model.BeerCsvRecord;
import nl.androidappfactory.spring6restmvc.model.BeerStyle;
import nl.androidappfactory.spring6restmvc.repositories.BeerRepository;
import nl.androidappfactory.spring6restmvc.repositories.CustomerRepository;
import nl.androidappfactory.spring6restmvc.services.BeerCsvService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class BootstrapData implements CommandLineRunner {

//    private final BeerService beerService;
    private final BeerCsvService beerCsvService;
    BeerRepository beerRepository;
    CustomerRepository customerRepository;

    @Transactional
    @Override
    public void run(String... args) throws FileNotFoundException {
        beerRepository.deleteAll();
        initBeerData();
        initCustomerData();
        initFromBeerCsvFile();

        log.debug("{} beers created", beerRepository.count());
        log.debug("{} customers created", customerRepository.count());
    }

    private void initFromBeerCsvFile() throws FileNotFoundException {

        if (beerRepository.count() < 10) {
            File beerCsvFile = ResourceUtils.getFile("classpath:csvdata/beers.csv");
            List<BeerCsvRecord> beerCsvRecords = beerCsvService.convert(beerCsvFile);

            beerCsvRecords.forEach(beerCsvRecord -> {
                BeerStyle beerStyle = switch (beerCsvRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                Beer beer = Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCsvRecord.getBeer(), 50))
                        .beerStyle(beerStyle)
                        .price(new BigDecimal("9.95"))
                        .upc(beerCsvRecord.getRow().toString())
                        .quantityOnHand(beerCsvRecord.getCount())
                        .build();

                beerRepository.save(beer);
            });
        }
        log.debug("{} beers created", beerRepository.count());
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

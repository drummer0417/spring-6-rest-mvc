package nl.androidappfactory.spring6restmvc.bootstrap;

import nl.androidappfactory.spring6restmvc.repositories.BeerRepository;
import nl.androidappfactory.spring6restmvc.repositories.CustomerRepository;
import nl.androidappfactory.spring6restmvc.services.BeerCsvService;
import nl.androidappfactory.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootstrapDataTest {

    BootstrapData bootstrapData;

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private BeerCsvService beerCsvService;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerCsvService, beerRepository, customerRepository);
    }

    @Test
    void run() throws FileNotFoundException {
        bootstrapData.run(null);
        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}
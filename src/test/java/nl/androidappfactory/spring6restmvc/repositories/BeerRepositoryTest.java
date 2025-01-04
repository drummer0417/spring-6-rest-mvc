package nl.androidappfactory.spring6restmvc.repositories;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder().beerName("Hertog Jan").build());

        assertThat(beerRepository.count()).isEqualTo(1);
        assertThat(savedBeer.getId()).isNotNull();
    }
}
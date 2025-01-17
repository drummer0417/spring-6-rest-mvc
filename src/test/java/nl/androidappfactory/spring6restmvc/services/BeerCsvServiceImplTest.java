package nl.androidappfactory.spring6restmvc.services;

import nl.androidappfactory.spring6restmvc.model.BeerCsvRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerCsvServiceImplTest {

    @Autowired
    BeerCsvService beerCsvService;

    @Test
    void convertCvsToPojo() throws FileNotFoundException {

        File csvFile = ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCsvRecord> beerCsvRecords = beerCsvService.convert(csvFile);

        assertNotNull(beerCsvRecords);
        assertThat(beerCsvRecords.size()).isEqualTo(2410);
        assertThat(beerCsvRecords.getFirst().getLabel()).isEqualTo("Pub Beer (10 Barrel Brewing Company)");
    }
}
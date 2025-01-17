package nl.androidappfactory.spring6restmvc.services;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.BeerCsvRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
@Slf4j
public class BeerCsvServiceImpl implements BeerCsvService {
    @Override
    public List<BeerCsvRecord> convert(File beerCsvFile) throws FileNotFoundException {

        List<BeerCsvRecord> beerCsvRecords = new CsvToBeanBuilder<BeerCsvRecord>(new FileReader(beerCsvFile))
                .withType(BeerCsvRecord.class)
                .build()
                .parse();
        log.debug(beerCsvRecords.getFirst().toString());
        return beerCsvRecords;
    }
}

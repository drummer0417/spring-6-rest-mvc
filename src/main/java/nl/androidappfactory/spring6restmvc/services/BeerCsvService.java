package nl.androidappfactory.spring6restmvc.services;

import nl.androidappfactory.spring6restmvc.model.BeerCsvRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface BeerCsvService {
    List<BeerCsvRecord> convert(File beerCsvFile) throws FileNotFoundException;
}
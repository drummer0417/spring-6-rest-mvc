package nl.androidappfactory.spring6restmvc.services;

import nl.androidappfactory.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> getAllBeers();

    BeerDTO addBeer(BeerDTO beerDTO);
}

package nl.androidappfactory.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.Beer;
import nl.androidappfactory.spring6restmvc.model.BeerStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {
    private final Map<UUID, Beer> beers = new HashMap<>();

    public BeerServiceImpl() {
        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Hertog Jan")
                .beerStyle(BeerStyle.LAGER)
                .upc("12356")
                .price(new BigDecimal("13.90"))
                .quantityOnHand(24)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Bavaria")
                .beerStyle(BeerStyle.LAGER)
                .upc("12356222")
                .price(new BigDecimal("7.99"))
                .quantityOnHand(12)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Corona")
                .beerStyle(BeerStyle.PILSNER)
                .upc("12356")
                .price(new BigDecimal("9.99"))
                .quantityOnHand(6)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beers.put(beer1.getId(), beer1);
        beers.put(beer2.getId(), beer2);
        beers.put(beer3.getId(), beer3);

        log.debug(String.format("Created %s Beers", beers.size()));
    }

    @Override
    public Beer getBeerById(UUID id) {
        return beers.get(id);
    }

    @Override
    public List<Beer> getAllBeers() {
        return new ArrayList<>(beers.values());
    }

    @Override
    public Beer addBeer(Beer beer) {
        Beer newBeer = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .build();
        beers.put(newBeer.getId(), newBeer);
        return newBeer;
    }
}

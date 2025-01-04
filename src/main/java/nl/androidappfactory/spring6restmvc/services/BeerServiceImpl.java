package nl.androidappfactory.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.BeerDTO;
import nl.androidappfactory.spring6restmvc.model.BeerStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {
    private final Map<UUID, BeerDTO> beers = new HashMap<>();

    public BeerServiceImpl() {
        BeerDTO beerDTO1 = BeerDTO.builder()
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

        BeerDTO beerDTO2 = BeerDTO.builder()
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

        BeerDTO beerDTO3 = BeerDTO.builder()
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

        beers.put(beerDTO1.getId(), beerDTO1);
        beers.put(beerDTO2.getId(), beerDTO2);
        beers.put(beerDTO3.getId(), beerDTO3);

        log.debug(String.format("Created %s Beers", beers.size()));
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.of(beers.get(id));
    }

    @Override
    public List<BeerDTO> getAllBeers() {
        return new ArrayList<>(beers.values());
    }

    @Override
    public BeerDTO addBeer(BeerDTO beerDTO) {
        BeerDTO newBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beerDTO.getBeerName())
                .beerStyle(beerDTO.getBeerStyle())
                .upc(beerDTO.getUpc())
                .price(beerDTO.getPrice())
                .quantityOnHand(beerDTO.getQuantityOnHand())
                .build();
        beers.put(newBeerDTO.getId(), newBeerDTO);
        return newBeerDTO;
    }
}

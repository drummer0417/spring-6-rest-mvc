package nl.androidappfactory.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.Beer;
import nl.androidappfactory.spring6restmvc.services.BeerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {
    private final BeerService beerService;

    @RequestMapping("/api/v1/beer")
    public List<Beer> getAllBeers() {

        List<Beer> beers = beerService.getAllBeers();
        log.debug("BeerController.getAllBeers(): {}", beers);
        return beers;
    }

    public Beer getBeerById(UUID id) {

        log.debug("Get Beer by Id - in controller");

        return beerService.getBeerById(id);
    }
}

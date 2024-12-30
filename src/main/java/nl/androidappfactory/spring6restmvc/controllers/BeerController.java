package nl.androidappfactory.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.Beer;
import nl.androidappfactory.spring6restmvc.services.BeerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> getAllBeers() {
        List<Beer> beers = beerService.getAllBeers();
        log.debug("BeerController.getAllBeers(): {}", beers);
        return beers;
    }

    @RequestMapping(value = "{beer-id}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beer-id") int id) {

        log.debug("Get Beer by Id - in controller");

        return beerService.getBeerById(id);
    }
}

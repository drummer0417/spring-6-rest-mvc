package nl.androidappfactory.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.Beer;
import nl.androidappfactory.spring6restmvc.services.BeerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


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

    @GetMapping
    @RequestMapping(value = "{beer-id}")
//    @RequestMapping(value = "{beer-id}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beer-id") UUID id) {

        log.debug("Get Beer by Id - in controller");

        return beerService.getBeerById(id);
    }

    @PostMapping
    public ResponseEntity addBeer(@RequestBody Beer beer) {
        Beer savedBeer = beerService.addBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/api/v1/beer/" + savedBeer.getId());
        log.debug("BeerController.addBeer(), name: {}", savedBeer);
        return new ResponseEntity<Beer>(headers, HttpStatus.CREATED);
    }
}

package nl.androidappfactory.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.entities.Beer;
import nl.androidappfactory.spring6restmvc.model.BeerDTO;
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
    public List<BeerDTO> getAllBeers() {
        List<BeerDTO> beerDTOS = beerService.getAllBeers();
        log.debug("BeerController.getAllBeers(): {}", beerDTOS);
        return beerDTOS;
    }

    @GetMapping
    @RequestMapping(value = "{beer-id}")
    public BeerDTO getBeerById(@PathVariable("beer-id") UUID id) {

        log.debug("Get Beer by Id - in controller");

        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public ResponseEntity<BeerDTO> addBeer(@RequestBody BeerDTO beerDTO) {
        BeerDTO savedBeerDTO = beerService.addBeer(beerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/api/v1/beer/" + savedBeerDTO.getId());
        log.debug("BeerController.addBeer(), name: {}", savedBeerDTO);
        return new ResponseEntity<BeerDTO>(headers, HttpStatus.CREATED);
    }
}

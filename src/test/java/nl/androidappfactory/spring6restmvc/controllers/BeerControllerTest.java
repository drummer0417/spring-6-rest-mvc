package nl.androidappfactory.spring6restmvc.controllers;

import nl.androidappfactory.spring6restmvc.model.Beer;
import nl.androidappfactory.spring6restmvc.model.BeerStyle;
import nl.androidappfactory.spring6restmvc.services.BeerService;
import nl.androidappfactory.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BeerController.class)
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    public void testGetBeer() throws Exception {
        Beer testBeer = createBeer();

        // when
        when(beerService.getBeerById(testBeer.getId())).thenReturn(testBeer);
        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())))
                .andExpect(jsonPath("$.price", is(9.99)))
        ;
    }

    @Test
    public void testListBeer() throws Exception {

        List<Beer> beers = beerServiceImpl.getAllBeers();

        when(beerService.getAllBeers()).thenReturn(beers);
        mockMvc.perform(get("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    private Beer createBeer() {
        return Beer.builder()
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
    }
}
package nl.androidappfactory.spring6restmvc.repositories;

import nl.androidappfactory.spring6restmvc.entities.Beer;
import nl.androidappfactory.spring6restmvc.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CategoryRepositoryTest {
    
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BeerRepository beerRepository;

    Beer testBeer;

    @BeforeEach
    void setUp() {
        testBeer = beerRepository.findAll().getFirst();
    }

    @Transactional
    @Test
    void addCategory() {
        Category savedCategory = categoryRepository.save( Category.builder()
                .description("0.0")
                .build());

        testBeer.addCategory(savedCategory);
        Beer savedBeer = beerRepository.save(testBeer);

        System.out.println(savedBeer);
    }
}

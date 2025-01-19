package nl.androidappfactory.spring6restmvc.repositories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.androidappfactory.spring6restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

    List<Beer> findByBeerNameIsLikeIgnoreCase(@NotNull @NotBlank @Size(max = 50) String beerName);
}

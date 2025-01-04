package nl.androidappfactory.spring6restmvc.repositories;

import nl.androidappfactory.spring6restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}

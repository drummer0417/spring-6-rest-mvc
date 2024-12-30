package nl.androidappfactory.spring6restmvc.services;

import nl.androidappfactory.spring6restmvc.model.Beer;

import java.util.List;

public interface BeerService {
    Beer getBeerById(int id);

    List<Beer> getAllBeers();
}

package com.armedia.atddaccelerator.template.monolith.service;

import com.armedia.atddaccelerator.template.monolith.entity.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    City save(City city);

    Optional<City> findById(Long id);

    List<City> findCitiesByName(String name);

    List<City> findAllCities();
}

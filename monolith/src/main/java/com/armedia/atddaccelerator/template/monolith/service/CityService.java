package com.armedia.atddaccelerator.template.monolith.service;

import com.armedia.atddaccelerator.template.monolith.controllers.api.dto.CityDTO;
import com.armedia.atddaccelerator.template.monolith.entity.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    City save(CityDTO cityDto);

    City save(City city);

    void delete(Long id);

    Optional<City> findById(Long id);

    List<City> findCitiesByName(String name);

    List<City> findAllCities();

}

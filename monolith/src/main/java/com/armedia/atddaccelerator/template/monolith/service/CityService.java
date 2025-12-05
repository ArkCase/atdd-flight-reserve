package com.armedia.atddaccelerator.template.monolith.service;

import com.armedia.atddaccelerator.template.monolith.controllers.api.dto.CityDTO;
import com.armedia.atddaccelerator.template.monolith.controllers.api.dto.CreateCityDTO;
import com.armedia.atddaccelerator.template.monolith.entity.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    City save(CreateCityDTO cityDto);

    City save(City city);

    void delete(Long id);

    Optional<City> findById(Long id);

    List<City> findCitiesByName(String name);

    List<City> findAllCities();

    List<CityDTO> searchByCountryAndName(Long countryId, String query, int limit);
}

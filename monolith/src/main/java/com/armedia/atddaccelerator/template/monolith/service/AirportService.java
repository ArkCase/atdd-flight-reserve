package com.armedia.atddaccelerator.template.monolith.service;

import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import com.armedia.atddaccelerator.template.monolith.entity.City;
import com.armedia.atddaccelerator.template.monolith.entity.Country;

import java.util.List;
import java.util.Optional;

public interface AirportService {
    Airport save(List<String> rawData, City city, Country country);

    Optional<Airport> findById(Long id);
}

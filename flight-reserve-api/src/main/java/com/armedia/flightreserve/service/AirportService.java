package com.armedia.flightreserve.service;

import com.armedia.flightreserve.model.Airport;
import com.armedia.flightreserve.model.City;
import com.armedia.flightreserve.model.Country;

import java.util.List;
import java.util.Optional;

public interface AirportService {
    Airport save(List<String> rawData, City city, Country country);

    Optional<Airport> findById(Long id);

    List<Airport> findByCityIdAndCountryId(Integer cityId, Integer countryId);
}

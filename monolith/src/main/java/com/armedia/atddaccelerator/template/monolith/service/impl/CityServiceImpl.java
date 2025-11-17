package com.armedia.atddaccelerator.template.monolith.service.impl;

import com.armedia.atddaccelerator.template.monolith.controllers.api.dto.CityDTO;
import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import com.armedia.atddaccelerator.template.monolith.entity.City;
import com.armedia.atddaccelerator.template.monolith.entity.Country;
import com.armedia.atddaccelerator.template.monolith.repository.AirportRepository;
import com.armedia.atddaccelerator.template.monolith.repository.CityRepository;
import com.armedia.atddaccelerator.template.monolith.repository.CountryRepository;
import com.armedia.atddaccelerator.template.monolith.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final AirportRepository airportRepository;

    @Override
    public City save(CityDTO cityDto) {
        City city = new City();
        city.setName(cityDto.name());
        if (cityDto.country() != null) {
            Country country = new Country();
            country.setName(cityDto.country().name());
            countryRepository.save(country);
            city.setCountry(country);
        }
        if (cityDto.airports() != null) {
            List<Airport> airports = new ArrayList<>();
            cityDto.airports().forEach(arp -> {
                Airport airport = Airport.builder()
                        .name(arp)
                        .build();
                airportRepository.save(airport);
                airports.add(airport);
            });
            city.setAirports(airports);
        }
        return cityRepository.save(city);
    }

    public City save(City city) {
        return cityRepository.save(city);
    }

    @Override
    public void delete(Long id) {
        findById(id).ifPresent(cityRepository::delete);
    }

    @Override
    public Optional<City> findById(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public List<City> findCitiesByName(String name) {
        return cityRepository.findCitiesByName(name);
    }

    @Override
    public List<City> findAllCities() {
        return cityRepository.findAll();
    }
}

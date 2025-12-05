package com.armedia.atddaccelerator.template.monolith.service.impl;

import com.armedia.atddaccelerator.template.monolith.controllers.api.dto.CityDTO;
import com.armedia.atddaccelerator.template.monolith.controllers.api.dto.CreateCityDTO;
import com.armedia.atddaccelerator.template.monolith.entity.City;
import com.armedia.atddaccelerator.template.monolith.entity.Country;
import com.armedia.atddaccelerator.template.monolith.repository.AirportRepository;
import com.armedia.atddaccelerator.template.monolith.repository.CityRepository;
import com.armedia.atddaccelerator.template.monolith.repository.CountryRepository;
import com.armedia.atddaccelerator.template.monolith.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final AirportRepository airportRepository;

    @Override
    public City save(CreateCityDTO cityDto) {
        City city = new City();
        city.setName(cityDto.name());
        Country country = countryRepository.findById(cityDto.countryId()).orElseThrow();
        city.setCountry(country);
        city.setDescription(cityDto.description());
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
        return cityRepository.findAll(Pageable.ofSize(100)).getContent();
    }

    public List<CityDTO> searchByCountryAndName(Long countryId, String query, int limit) {
        List<City> cities = cityRepository.searchByCountryAndName(countryId, query);

        return cities.stream()
                .limit(limit)
                .map(CityDTO::from)
                .collect(Collectors.toList());
    }
}

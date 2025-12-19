package com.armedia.flightreserve.service.impl;

import com.armedia.flightreserve.controllers.api.dto.CityDTO;
import com.armedia.flightreserve.controllers.api.dto.CreateCityDTO;
import com.armedia.flightreserve.model.City;
import com.armedia.flightreserve.model.Country;
import com.armedia.flightreserve.repository.AirportRepository;
import com.armedia.flightreserve.repository.CityRepository;
import com.armedia.flightreserve.repository.CountryRepository;
import com.armedia.flightreserve.service.CityService;
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

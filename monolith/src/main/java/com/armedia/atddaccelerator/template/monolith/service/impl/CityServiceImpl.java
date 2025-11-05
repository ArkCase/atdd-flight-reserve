package com.armedia.atddaccelerator.template.monolith.service.impl;

import com.armedia.atddaccelerator.template.monolith.entity.City;
import com.armedia.atddaccelerator.template.monolith.repository.CityRepository;
import com.armedia.atddaccelerator.template.monolith.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public City save(City city) {
        return cityRepository.save(city);
    }

    public Optional<City> findById(Long id) {
        return cityRepository.findById(id);
    }

    public List<City> findCitiesByName(String name) {
        return cityRepository.findCitiesByName(name);
    }

    @Override
    public List<City> findAllCities() {
        return cityRepository.findAll();
    }
}

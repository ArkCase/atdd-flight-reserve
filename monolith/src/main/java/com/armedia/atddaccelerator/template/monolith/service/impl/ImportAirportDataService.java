package com.armedia.atddaccelerator.template.monolith.service.impl;

import com.armedia.atddaccelerator.template.monolith.entity.City;
import com.armedia.atddaccelerator.template.monolith.entity.Country;
import com.armedia.atddaccelerator.template.monolith.repository.AirportRepository;
import com.armedia.atddaccelerator.template.monolith.service.AirportService;
import com.armedia.atddaccelerator.template.monolith.service.CityService;
import com.armedia.atddaccelerator.template.monolith.service.CountryService;
import com.armedia.atddaccelerator.template.monolith.service.ImportDataService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImportAirportDataService implements ImportDataService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final CountryService countryService;

    private final CityService cityService;

    private final AirportService airportService;

    private final AirportRepository airportRepository;

    @Override
    public String getType() {
        return "airports";
    }

    @Transactional
    @Override
    public List<String> importData(MultipartFile file) {

        if (airportRepository.count() > 0) {
            LOGGER.info("Airports have already been imported");

            return null;
        }
        List<List<String>> lines = parse(file);
        var total = lines.size();
        var count = 0;
        List<String> successfullyInserted = new ArrayList<>(total);
        Map<String, Country> insertedCountries = new HashMap<>();
        Map<String, City> insertedCities = new HashMap<>();

        for (List<String> data : lines) {
            try {
                insertAirport(data, insertedCountries, insertedCities);
                successfullyInserted.add(data.toString());
                count++;
                if (count % 100 == 0) {
                    LOGGER.info("Progress : {}/{}", count, total);
                }
            } catch (Exception e) {
                LOGGER.error("SKIPPING, unable to insert line : [{}]", data.toString());
            }
        }

        return successfullyInserted;
    }

    private void insertAirport(List<String> data,
                               Map<String, Country> existingCountries,
                               Map<String, City> existingCities) {
        var countryName = data.get(3).trim();
        Country country = existingCountries.get(countryName);
        if (country == null) {
            country = insertCountry(countryName);
            existingCountries.put(countryName, country);
        }

        var cityName = data.get(2);
        String key = String.format("%s-%s", country.getName().trim(), cityName.trim()).toLowerCase();
        City city = existingCities.get(key);
        if (city == null) {
            city = insertCity(cityName, country);
            existingCities.put(key, city);
        }
        airportService.save(data, city, country);
    }

    private City insertCity(String cityName, Country country) {
        City city = new City();
        city.setName(cityName);
        city.setCountry(country);
        city.setDescription("Created by import functionality");
        return cityService.save(city);
    }

    private Country insertCountry(String countryName) {
        Country country = new Country();
        country.setName(countryName);
        return countryService.save(country);
    }
}

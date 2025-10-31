package com.armedia.atddaccelerator.template.monolith.service.impl;

import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import com.armedia.atddaccelerator.template.monolith.entity.City;
import com.armedia.atddaccelerator.template.monolith.entity.Country;
import com.armedia.atddaccelerator.template.monolith.repository.AirportRepository;
import com.armedia.atddaccelerator.template.monolith.repository.CountryRepository;
import com.armedia.atddaccelerator.template.monolith.service.AirportService;
import com.armedia.atddaccelerator.template.monolith.service.CityService;
import com.armedia.atddaccelerator.template.monolith.service.CountryService;
import com.armedia.atddaccelerator.template.monolith.service.ImportDataService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportAirportDataService implements ImportDataService
{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final CountryRepository countryRepository;

    private final CountryService countryService;

    private final CityService cityService;

    private final AirportService airportService;

    private final AirportRepository airportRepository;

    @Override
    public String getType() {
        return "airports";
    }

    @Override
    public List<String> importData(MultipartFile file) {

        if (airportRepository.count() > 0)
        {
            LOGGER.info("Airports have already been imported");

            return null;
        }
        List<String> successfullyInserted = new ArrayList<>();

        List<List<String>> lines = parse(file);

        for (List<String> data : lines) {
            try {
                Airport result = insertAirport(data);

                successfullyInserted.add(data.toString());
                LOGGER.info("INSERTED airport, id=[{}],name=[{}],iata=[{}],icao=[{}],city=[{}],country=[{}]", result.getId(), result.getName(), result.getIata(), result.getIcao(), result.getHome_city().getName(), result.getHome_country().getName());

            } catch (Exception e) {
                LOGGER.error("SKIPPING, unable to insert line : [{}]", data.toString());
            }
        }

        return successfullyInserted;
    }

    private Airport insertAirport(List<String> data) {
        Country country = countryRepository.findByName(data.get(3));

        if (country == null) {

            country = insertCountry(data.get(3));
        }

        City city = null;
        if (country.getCities() != null) {
            city = country.getCities().stream().filter(c -> c.getName().equals(data.get(2))).findFirst().orElse(insetCity(data.get(2), country));
        } else {
            city = insetCity(data.get(2), country);
        }

        return airportService.save(data, city, country);
    }

    private City insetCity(String cityName, Country country) {
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

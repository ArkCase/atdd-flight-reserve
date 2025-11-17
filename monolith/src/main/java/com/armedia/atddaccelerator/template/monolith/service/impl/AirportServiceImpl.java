package com.armedia.atddaccelerator.template.monolith.service.impl;

import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import com.armedia.atddaccelerator.template.monolith.entity.City;
import com.armedia.atddaccelerator.template.monolith.entity.Country;
import com.armedia.atddaccelerator.template.monolith.repository.AirportRepository;
import com.armedia.atddaccelerator.template.monolith.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService
{
    private final AirportRepository airportRepository;

    @Override
    public Airport save(List<String> rawData, City city, Country country) {
        Airport airport = Airport.builder()
                .name(rawData.get(1))
                .homeCity(city)
                .homeCountry(country)
                .iata(rawData.get(4))
                .icao(rawData.get(5))
                .latitude(Double.parseDouble(rawData.get(6)))
                .longitude(Double.parseDouble(rawData.get(7)))
                .altitude(Integer.parseInt(rawData.get(8)))
                .timezone(rawData.get(9))
                .dst(rawData.get(10))
                .tz(rawData.get(11))
                .type(rawData.get(12))
                .source(rawData.get(13)).build();
        return airportRepository.save(airport);
    }

    @Override
    public Optional<Airport> findById(Long id) {
        return airportRepository.findById(id);
    }

    @Override
    public List<Airport> findByCityIdAndCountryId(Integer cityId, Integer countryId)
    {
        return airportRepository.findAirportsByHomeCity_IdAndHomeCountry_Id(Long.valueOf(cityId), Long.valueOf(countryId));
    }
}

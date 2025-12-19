package com.armedia.flightreserve.controllers.api.dto;

import com.armedia.flightreserve.model.Airport;
import com.armedia.flightreserve.model.City;

import java.util.List;

public record CityDTO(
// City fields
        Long id,
        String name,
        List<String> airports,
        CountryDTO country
) {

    public CityDTO {
        airports = airports != null ? List.copyOf(airports) : List.of();
    }

    public static CityDTO from(City city) {
        return new CityDTO(
                city.getId(),
                city.getName(),
                city.getAirports() != null ?
                        city.getAirports()
                                .stream()
                                .map(Airport::getName)
                                .toList() : List.of(),
                city.getCountry() != null ?
                        CountryDTO.toDTO(city.getCountry()) : null);
    }

}

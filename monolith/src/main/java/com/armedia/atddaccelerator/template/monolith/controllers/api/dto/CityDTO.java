package com.armedia.atddaccelerator.template.monolith.controllers.api.dto;

import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import com.armedia.atddaccelerator.template.monolith.entity.City;

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

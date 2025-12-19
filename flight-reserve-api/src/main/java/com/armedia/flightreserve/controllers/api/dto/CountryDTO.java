package com.armedia.flightreserve.controllers.api.dto;

import com.armedia.flightreserve.model.Country;

public record CountryDTO(
        String name,
        Long id
) {

    public static CountryDTO toDTO(Country country) {
        return new CountryDTO(country.getName(), country.getId());
    }
}
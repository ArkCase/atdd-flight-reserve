package com.armedia.atddaccelerator.template.monolith.controllers.api.dto;

import com.armedia.atddaccelerator.template.monolith.entity.Country;

public record CountryDTO(
        String name,
        Long id
) {

    public static CountryDTO toDTO(Country country) {
        return new CountryDTO(country.getName(), country.getId());
    }
}
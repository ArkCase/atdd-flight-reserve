package com.armedia.atddaccelerator.template.monolith.controllers.api.dto;

public record CreateCityDTO(
        String name,
        Long countryId,
        String description
) {
}

package com.armedia.flightreserve.controllers.api.dto;

public record CreateCityDTO(
        String name,
        Long countryId,
        String description
) {
}

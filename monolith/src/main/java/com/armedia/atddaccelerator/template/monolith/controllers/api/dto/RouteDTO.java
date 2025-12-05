package com.armedia.atddaccelerator.template.monolith.controllers.api.dto;

import com.armedia.atddaccelerator.template.monolith.entity.Route;

public record RouteDTO(
    Long id,
    String airline,
    String srcAirport,
    String dstAirport,
    String codeshare,
    Integer stops,
    String equipment,
    Double price
) {
    public static RouteDTO from(Route route) {
        return new RouteDTO(
                route.getId(),
                route.getAirline() != null ? route.getAirline().getCode() : null,
                route.getSrc_airport() != null ? route.getSrc_airport().getIata() : null,
                route.getDst_airport() != null ? route.getDst_airport().getIata() : null,
                route.getCodeshare(),
                route.getStops(),
                route.getEquipment(),
                route.getPrice()
        );
    }
}
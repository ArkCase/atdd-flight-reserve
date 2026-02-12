package com.armedia.flightreserve.controllers.api.dto;

import com.armedia.flightreserve.model.Route;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record RouteDTO(
    Long id,
    String airline,
    String srcAirport,
    String dstAirport,
    String codeshare,
    Integer stops,
    String equipment,
    PriceDTO price
) {

    public static RouteDTO from(
            Route route,
            double exchangeRate,
            String localCurrency
    ) {

        double usdPrice = route.getPrice();

        double localPrice = BigDecimal
                .valueOf(usdPrice * exchangeRate)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return new RouteDTO(
                route.getId(),
                route.getAirline() != null ? route.getAirline().getCode() : null,
                route.getSrc_airport() != null ? route.getSrc_airport().getIata() : null,
                route.getDst_airport() != null ? route.getDst_airport().getIata() : null,
                route.getCodeshare(),
                route.getStops(),
                route.getEquipment(),
                new PriceDTO(
                        usdPrice,
                        localPrice,
                        localCurrency
                )
        );
    }
}

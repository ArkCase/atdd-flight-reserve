package com.armedia.atddaccelerator.template.monolith.repository;

import com.armedia.atddaccelerator.template.monolith.entity.Airline;
import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import com.armedia.atddaccelerator.template.monolith.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    default Route buildRoute(List<String> rawData, Airline airline, Airport srcAirport, Airport dstAirport) {
        return Route.builder()
                .airline(airline)
                .src_airport(srcAirport)
                .dst_airport(dstAirport)
                .codeshare(rawData.get(6))
                .stops(Integer.valueOf(rawData.get(7)))
                .equipment(rawData.get(8))
                .price(Double.valueOf(rawData.get(9))).build();
    }
}

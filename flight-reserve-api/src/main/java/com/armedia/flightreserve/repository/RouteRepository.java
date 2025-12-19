package com.armedia.flightreserve.repository;

import com.armedia.flightreserve.model.Airline;
import com.armedia.flightreserve.model.Airport;
import com.armedia.flightreserve.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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


    @Query(
            value = "SELECT * FROM route WHERE src_airport_id = :srcId AND dst_airport_id = :dstId",
            nativeQuery = true
    )
    List<Route> findBySrcAndDstAirportIds(@Param("srcId") Integer srcId, @Param("dstId") Integer dstId);


}

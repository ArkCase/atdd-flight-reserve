package com.armedia.flightreserve.service;

import java.util.List;

import com.armedia.flightreserve.controllers.api.dto.RouteDTO;
import com.armedia.flightreserve.model.Route;
import com.armedia.flightreserve.model.Airline;
import com.armedia.flightreserve.model.Airport;

public interface RouteService {
    Route save(List<String> rawData, Airline airline, Airport srcAirport, Airport dstAirport);

    List<RouteDTO> findBySrcAirportIdAndDstAirportId(Integer srcAirportId, Integer dstAirportId);
}

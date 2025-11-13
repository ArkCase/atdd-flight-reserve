package com.armedia.atddaccelerator.template.monolith.service;

import java.util.List;
import com.armedia.atddaccelerator.template.monolith.entity.Route;
import com.armedia.atddaccelerator.template.monolith.entity.Airline;
import com.armedia.atddaccelerator.template.monolith.entity.Airport;

public interface RouteService {
    Route save(List<String> rawData, Airline airline, Airport srcAirport, Airport dstAirport);

    List<Route> findBySrcAirportIdAndDstAirportId(Integer srcAirportId, Integer dstAirportId);
}

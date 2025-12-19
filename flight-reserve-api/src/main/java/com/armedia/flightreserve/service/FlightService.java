package com.armedia.flightreserve.service;

import com.armedia.flightreserve.model.dto.CheapestFlightRoute;
import com.armedia.flightreserve.exception.CityNotFoundException;
import com.armedia.flightreserve.exception.RouteNotFoundException;

public interface FlightService {
    CheapestFlightRoute findShortestCostPath(Long sourceCityId, Long destinationCityId) throws CityNotFoundException, RouteNotFoundException;
}

package com.armedia.atddaccelerator.template.monolith.service;

import com.armedia.atddaccelerator.template.monolith.entity.dto.CheapestFlightRoute;
import com.armedia.atddaccelerator.template.monolith.exception.CityNotFoundException;
import com.armedia.atddaccelerator.template.monolith.exception.RouteNotFoundException;

public interface FlightService {
    CheapestFlightRoute findShortestPath(Long sourceCityId, Long destinationCityId) throws CityNotFoundException, RouteNotFoundException;
}

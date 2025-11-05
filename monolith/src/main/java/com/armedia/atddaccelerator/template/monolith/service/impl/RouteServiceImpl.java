package com.armedia.atddaccelerator.template.monolith.service.impl;

import com.armedia.atddaccelerator.template.monolith.entity.Airline;
import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import com.armedia.atddaccelerator.template.monolith.entity.Route;
import com.armedia.atddaccelerator.template.monolith.repository.RouteRepository;
import com.armedia.atddaccelerator.template.monolith.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;

    @Override
    public Route save(List<String> rawData, Airline airline, Airport srcAirport, Airport dstAirport) {
        Route route = routeRepository.buildRoute(rawData, airline, srcAirport, dstAirport);
        return routeRepository.save(route);
    }
}

package com.armedia.flightreserve.service.impl;

import com.armedia.flightreserve.controllers.api.dto.RouteDTO;
import com.armedia.flightreserve.model.Airline;
import com.armedia.flightreserve.model.Airport;
import com.armedia.flightreserve.model.Route;
import com.armedia.flightreserve.repository.RouteRepository;
import com.armedia.flightreserve.service.RouteService;
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

    @Override
    public List<RouteDTO> findBySrcAirportIdAndDstAirportId(Integer srcAirportId, Integer dstAirportId) {
        List<Route> routes = routeRepository.findBySrcAndDstAirportIds(srcAirportId, dstAirportId);
        return routes.stream().map(RouteDTO::from).toList();
    }
}

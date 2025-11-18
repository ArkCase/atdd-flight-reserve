package com.armedia.atddaccelerator.template.monolith.service.impl;

import com.armedia.atddaccelerator.template.monolith.entity.Airline;
import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import com.armedia.atddaccelerator.template.monolith.entity.Route;
import com.armedia.atddaccelerator.template.monolith.repository.AirlineRepository;
import com.armedia.atddaccelerator.template.monolith.repository.AirportRepository;
import com.armedia.atddaccelerator.template.monolith.repository.RouteRepository;
import com.armedia.atddaccelerator.template.monolith.service.ImportDataService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImportRoutesDataService implements ImportDataService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final AirlineRepository airlineRepository;

    private final AirportRepository airportRepository;

    private final RouteRepository routeRepository;

    @Override
    public String getType() {
        return "routes";
    }

    @Transactional
    @Override
    public List<String> importData(MultipartFile file) {

        if (routeRepository.count() > 0) {
            LOGGER.info("Routes have already been imported");
            return List.of();
        }

        List<List<String>> lines = parse(file);

        Map<String, Airline> airlineByCode = airlineRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Airline::getCode, a -> a));

        Map<String, Airport> airportByIata = airportRepository.findAll()
                .stream()
                .filter(airport -> airport.getIata() != null && !airport.getIata().equals("\\N"))
                .collect(Collectors.toMap(Airport::getIata, a -> a));

        List<Route> batch = new ArrayList<>(1000);
        List<String> inserted = new ArrayList<>(lines.size());
        int count = 0;

        for (List<String> data : lines) {

            Airline airline = airlineByCode.get(data.get(0));
            if (airline == null) continue;

            Airport srcAirport = airportByIata.get(data.get(2));
            if (srcAirport == null) continue;

            Airport dstAirport = airportByIata.get(data.get(4));
            if (dstAirport == null) continue;

            Route route = routeRepository.buildRoute(data, airline, srcAirport, dstAirport);
            batch.add(route);
            inserted.add(data.toString());

            if (batch.size() == 1000) {
                routeRepository.saveAll(batch);
                batch.clear();
            }

            count++;
            if (count % 5000 == 0) {
                LOGGER.info("Progress: {}/{}", count, lines.size());
            }
        }

        routeRepository.saveAll(batch);
        LOGGER.info("Routes import finished: {} inserted", inserted.size());

        return inserted;
    }
}

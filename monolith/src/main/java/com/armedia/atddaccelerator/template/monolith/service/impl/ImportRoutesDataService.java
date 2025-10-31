package com.armedia.atddaccelerator.template.monolith.service.impl;

import com.armedia.atddaccelerator.template.monolith.entity.Airline;
import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import com.armedia.atddaccelerator.template.monolith.entity.Route;
import com.armedia.atddaccelerator.template.monolith.repository.AirportRepository;
import com.armedia.atddaccelerator.template.monolith.repository.AirlineRepository;
import com.armedia.atddaccelerator.template.monolith.repository.RouteRepository;
import com.armedia.atddaccelerator.template.monolith.service.ImportDataService;
import com.armedia.atddaccelerator.template.monolith.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportRoutesDataService implements ImportDataService
{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final AirlineRepository airlineRepository;

    private final AirportRepository airportRepository;

    private final RouteService routeService;

    private final RouteRepository routeRepository;

    @Override
    public String getType() {
        return "routes";
    }

    @Override
    public List<String> importData(MultipartFile file) {

        if (routeRepository.count() > 0)
        {
            LOGGER.info("Routes have already been imported");
            return null;
        }
        List<String> successfullyInserted = new ArrayList<>();

        List<List<String>> lines = parse(file);

        for (List<String> data : lines) {
            try {
                Airline airline = airlineRepository.findByCode(data.get(0));
                if (airline == null) continue;
                Airport srcAirport = airportRepository.findByIata(data.get(2));
                if (srcAirport == null) continue;
                Airport dstAirport = airportRepository.findByIata(data.get(4));
                if (dstAirport == null) continue;
                System.out.println("found related data");

                Route result = routeService.save(data, airline, srcAirport, dstAirport);
                successfullyInserted.add(data.toString());
                LOGGER.info("INSERTED route, id=[{}],airline=[{}],src_airport=[{}],dst_airport=[{}],price=[{}]", result.getId(),
                        result.getAirline().getCode(), result.getSrc_airport().getName(), result.getDst_airport().getName(), result.getPrice());
            } catch (Exception e) {
                LOGGER.error("SKIPPING, unable to insert line : [{}]", data.toString());
            }
        }

        return successfullyInserted;
    }

}

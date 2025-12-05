package com.armedia.atddaccelerator.template.monolith.controllers.api;

import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import com.armedia.atddaccelerator.template.monolith.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController
{

    private final AirportService airportService;

    @GetMapping("/by/{cityId}/{countryId}")
    public List<Airport> getAirportsByCityAndCountry(
            @PathVariable("cityId") Integer cityId,
            @PathVariable("countryId") Integer countryId)
    {
        return airportService.findByCityIdAndCountryId(cityId, countryId);
    }
}

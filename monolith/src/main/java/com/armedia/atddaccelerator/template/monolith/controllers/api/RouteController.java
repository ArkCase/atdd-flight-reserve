package com.armedia.atddaccelerator.template.monolith.controllers.api;

import com.armedia.atddaccelerator.template.monolith.entity.Route;
import com.armedia.atddaccelerator.template.monolith.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController
{
    @Autowired
    RouteService routeService;

    @GetMapping("/by/{srcAirportId}/{dstAirportId}")
    public List<Route> getRoutesBySrcAndDst(
            @PathVariable Integer srcAirportId,
            @PathVariable Integer dstAirportId) {

        return routeService.findBySrcAirportIdAndDstAirportId(srcAirportId, dstAirportId);
    }
}

package com.armedia.atddaccelerator.template.monolith.controllers.api;

import com.armedia.atddaccelerator.template.monolith.controllers.api.dto.RouteDTO;
import com.armedia.atddaccelerator.template.monolith.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/by/{srcAirportId}/{dstAirportId}")
    public List<RouteDTO> getRoutesBySrcAndDst(
            @PathVariable Integer srcAirportId,
            @PathVariable Integer dstAirportId) {

        return routeService.findBySrcAirportIdAndDstAirportId(srcAirportId, dstAirportId);
    }
}

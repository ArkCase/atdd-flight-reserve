package com.armedia.flightreserve.controllers.api;

import com.armedia.flightreserve.model.dto.CheapestFlightRoute;
import com.armedia.flightreserve.exception.CityNotFoundException;
import com.armedia.flightreserve.exception.RouteNotFoundException;
import com.armedia.flightreserve.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/{source}/{destination}")
   // @RolesAllowed(Roles.REGULAR_USER)
    public ResponseEntity<?> findCheapestFlight(@PathVariable Long source, @PathVariable Long destination) {

        CheapestFlightRoute result = null;
        try {
            result = flightService.findShortestCostPath(source, destination);
        } catch (CityNotFoundException | RouteNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}

package com.armedia.flightreserve.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CheapestFlightRoute {
    private List<String> cities;
    private Double price;
}

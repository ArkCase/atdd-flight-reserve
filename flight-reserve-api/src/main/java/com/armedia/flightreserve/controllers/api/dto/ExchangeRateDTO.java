package com.armedia.flightreserve.controllers.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDTO
{
    private String base;
    private String target;
    private double mid;
    private int unit;
    private String timestamp;
}

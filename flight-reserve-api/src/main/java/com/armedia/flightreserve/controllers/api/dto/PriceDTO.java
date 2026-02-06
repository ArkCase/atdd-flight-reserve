package com.armedia.flightreserve.controllers.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDTO {

    private double usd;
    private Double local;
    private String localCurrency;
}

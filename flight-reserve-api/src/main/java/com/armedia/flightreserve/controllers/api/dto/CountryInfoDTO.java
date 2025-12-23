package com.armedia.flightreserve.controllers.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryInfoDTO
{
    private String officialName;
    private Map<String, CurrencyDTO> currencies;
    private List<String> timezones;
}

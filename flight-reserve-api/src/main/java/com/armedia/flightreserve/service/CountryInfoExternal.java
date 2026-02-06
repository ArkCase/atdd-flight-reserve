package com.armedia.flightreserve.service;

import com.armedia.flightreserve.controllers.api.dto.CountryInfoDTO;

import java.util.List;

public interface CountryInfoExternal
{
    List<CountryInfoDTO> getCountryCurrencyAndTimezone(String countryName);
}

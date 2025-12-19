package com.armedia.flightreserve.service;

import com.armedia.flightreserve.controllers.api.dto.CountryDTO;
import com.armedia.flightreserve.model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    Optional<Country> findById(Long id);

    Country save(Country country);

    List<CountryDTO> findAll();
}

package com.armedia.atddaccelerator.template.monolith.service;

import com.armedia.atddaccelerator.template.monolith.controllers.api.dto.CountryDTO;
import com.armedia.atddaccelerator.template.monolith.entity.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    Optional<Country> findById(Long id);

    Country save(Country country);

    List<CountryDTO> findAll();
}

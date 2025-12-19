package com.armedia.flightreserve.service.impl;

import com.armedia.flightreserve.controllers.api.dto.CountryDTO;
import com.armedia.flightreserve.model.Country;
import com.armedia.flightreserve.repository.CountryRepository;
import com.armedia.flightreserve.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public List<CountryDTO> findAll() {
        return countryRepository.findAll()
                .stream()
                .map(CountryDTO::toDTO)
                .toList();
    }
}

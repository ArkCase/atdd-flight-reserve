package com.armedia.atddaccelerator.template.monolith.controllers.api;

import com.armedia.atddaccelerator.template.monolith.controllers.api.dto.CountryDTO;
import com.armedia.atddaccelerator.template.monolith.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public List<CountryDTO> findAll() {
        return countryService.findAll();
    }
}

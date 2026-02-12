package com.armedia.flightreserve.controllers.api;

import com.armedia.flightreserve.controllers.api.dto.CountryInfoDTO;
import com.armedia.flightreserve.service.CountryInfoExternal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.util.List;


@Slf4j
@RequestMapping("/api/country/info")
@RestController
@RequiredArgsConstructor
public class CountryInfoController
{

    private final CountryInfoExternal countryInfoExternal;

    @GetMapping("/{countryName}")
    public ResponseEntity<List<CountryInfoDTO>> countryCurrentAndTimeZoneInfo(@PathVariable String countryName)
    {
        List<CountryInfoDTO> response =
                countryInfoExternal.getCountryCurrencyAndTimezone(countryName);

        if (response.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);

    }
}

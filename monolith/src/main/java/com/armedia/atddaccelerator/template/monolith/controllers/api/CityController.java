package com.armedia.atddaccelerator.template.monolith.controllers.api;

import com.armedia.atddaccelerator.template.monolith.controllers.api.dto.CityDTO;
import com.armedia.atddaccelerator.template.monolith.controllers.api.dto.CreateCityDTO;
import com.armedia.atddaccelerator.template.monolith.entity.City;
import com.armedia.atddaccelerator.template.monolith.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/search")
    public List<CityDTO> searchCities(
            @RequestParam Long countryId,
            @RequestParam String query,
            @RequestParam(defaultValue = "100") int limit) {
        return cityService.searchByCountryAndName(countryId, query, limit);
    }

    @GetMapping("/by-name/{name}")
    public List<CityDTO> findByName(@PathVariable String name) {
        return cityService.findCitiesByName(name)
                .stream()
                .map(CityDTO::from)
                .toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public CityDTO save(@RequestBody CreateCityDTO cityDTO) {
        City city = cityService.save(cityDTO);
        return CityDTO.from(city);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cityService.delete(id);
    }
}

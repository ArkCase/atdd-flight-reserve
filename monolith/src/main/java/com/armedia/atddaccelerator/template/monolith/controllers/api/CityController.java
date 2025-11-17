package com.armedia.atddaccelerator.template.monolith.controllers.api;

import com.armedia.atddaccelerator.template.monolith.entity.City;
import com.armedia.atddaccelerator.template.monolith.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController
{
    private final CityService cityService;

    @GetMapping("by/{name}")
    public List<City> findByName(@PathVariable String name)
    {
        return cityService.findCitiesByName(name);
    }

}

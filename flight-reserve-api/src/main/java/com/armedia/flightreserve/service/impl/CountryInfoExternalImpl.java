package com.armedia.flightreserve.service.impl;

import com.armedia.flightreserve.controllers.api.dto.CountryInfoDTO;
import com.armedia.flightreserve.controllers.api.dto.CurrencyDTO;
import com.armedia.flightreserve.service.CountryInfoExternal;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CountryInfoExternalImpl implements CountryInfoExternal
{
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${frs.host}")
    private String api;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<CountryInfoDTO> getCountryCurrencyAndTimezone(String countryName)
    {
        try {
            String result = restTemplate.getForObject(api + "/name/" + countryName + "?fields=name,currencies,timezones", String.class);

            JsonNode root = objectMapper.readTree(result);

            ArrayNode responseList = objectMapper.createArrayNode();

            if (!root.isArray() || root.isEmpty())
            {
                return  Collections.emptyList();
            }
            List<CountryInfoDTO> response = new ArrayList<>();

            for (JsonNode country : root)
            {

                String officialName =
                        country.path("name").path("common").asText();

                Map<String, CurrencyDTO> currencies =
                        objectMapper.convertValue(
                                country.path("currencies"),
                                objectMapper.getTypeFactory()
                                        .constructMapType(Map.class, String.class, CurrencyDTO.class)
                        );

                List<String> timezones =
                        objectMapper.convertValue(
                                country.path("timezones"),
                                objectMapper.getTypeFactory()
                                        .constructCollectionType(List.class, String.class)
                        );

                response.add(new CountryInfoDTO(
                        officialName,
                        currencies,
                        timezones
                ));
            }

            return response;
        } catch (Exception ex) {
            log.warn("Exception: ", ex);
            return  Collections.emptyList();
        }
    }
}

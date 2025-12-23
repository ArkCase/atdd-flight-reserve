package com.armedia.flightreserve.controllers.api;

import com.armedia.flightreserve.controllers.api.dto.CountryInfoDTO;
import com.armedia.flightreserve.controllers.api.dto.CurrencyDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api/country/info")
@RestController
public class CountryInfoController
{

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${frs.host}")
    private String api;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/{countryName}")
    public ResponseEntity<List<CountryInfoDTO>> countryCurrentAndTimeZoneInfo(@PathVariable String countryName) {
        try {
            String result = restTemplate.getForObject(api + "/name/" + countryName + "?fields=name,currencies,timezones", String.class);
            //return  ResponseEntity.ok(objectMapper.readTree(result).get(0).toString());
            JsonNode root = objectMapper.readTree(result);

            ArrayNode responseList = objectMapper.createArrayNode();

            if (!root.isArray() || root.isEmpty())
            {
                return ResponseEntity.notFound().build();
            }
            List<CountryInfoDTO> response = new ArrayList<>();

            for (JsonNode country : root)
            {

                String officialName =
                        country.path("name").path("official").asText();

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

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.warn("Exception: ", ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}

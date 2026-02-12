package com.armedia.flightreserve.controllers.api;

import com.armedia.flightreserve.controllers.api.dto.ExchangeRateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequestMapping("/api/currencyExchange/")
@RestController
public class CurrencyExchangeInfoController
{
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/usd/{countryCode}")
    public ResponseEntity<ExchangeRateDTO> getUsdToCountryRate(
            @PathVariable String countryCode) throws JsonProcessingException
    {

        String baseCurrency = "USD";
        String url = String.format(
                "https://hexarate.paikama.co/api/rates/%s/%s/latest",
                baseCurrency,
                countryCode.toUpperCase()
        );

        ResponseEntity<JsonNode> response =
                restTemplate.getForEntity(url, JsonNode.class);

        ExchangeRateDTO dto =
                new ObjectMapper().treeToValue(
                        response.getBody().get("data"),
                        ExchangeRateDTO.class
                );


        return ResponseEntity.ok(dto);
    }

}

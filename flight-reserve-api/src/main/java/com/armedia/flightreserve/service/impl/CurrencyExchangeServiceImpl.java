package com.armedia.flightreserve.service.impl;

import com.armedia.flightreserve.controllers.api.dto.ExchangeRateDTO;
import com.armedia.flightreserve.service.CurrencyExchangeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService
{
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ExchangeRateDTO getUsdToCurrencyRate(String currencyCode)
    {

        String url = String.format(
                "https://hexarate.paikama.co/api/rates/USD/%s/latest",
                currencyCode.toUpperCase()
        );

        ResponseEntity<JsonNode> response =
                restTemplate.getForEntity(url, JsonNode.class);

        try
        {
            return objectMapper.treeToValue(
                    response.getBody().get("data"),
                    ExchangeRateDTO.class
            );
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException("Failed to parse exchange rate", e);
        }
    }
}

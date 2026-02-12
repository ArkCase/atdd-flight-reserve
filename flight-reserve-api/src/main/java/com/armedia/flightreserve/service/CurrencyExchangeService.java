package com.armedia.flightreserve.service;

import com.armedia.flightreserve.controllers.api.dto.ExchangeRateDTO;

public interface CurrencyExchangeService
{
    ExchangeRateDTO getUsdToCurrencyRate(String currencyCode);
}

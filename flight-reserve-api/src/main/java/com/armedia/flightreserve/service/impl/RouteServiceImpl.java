package com.armedia.flightreserve.service.impl;

import com.armedia.flightreserve.controllers.api.dto.CountryInfoDTO;
import com.armedia.flightreserve.controllers.api.dto.ExchangeRateDTO;
import com.armedia.flightreserve.controllers.api.dto.RouteDTO;
import com.armedia.flightreserve.model.Airline;
import com.armedia.flightreserve.model.Airport;
import com.armedia.flightreserve.model.Route;
import com.armedia.flightreserve.repository.RouteRepository;
import com.armedia.flightreserve.service.CountryInfoExternal;
import com.armedia.flightreserve.service.CurrencyExchangeService;
import com.armedia.flightreserve.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final CurrencyExchangeService currencyExchangeService;
    private final CountryInfoExternal countryInfoExternal;

    @Override
    public Route save(List<String> rawData, Airline airline, Airport srcAirport, Airport dstAirport)
    {
        Route route = routeRepository.buildRoute(rawData, airline, srcAirport, dstAirport);
        return routeRepository.save(route);
    }

    @Override
    public List<RouteDTO> findBySrcAirportIdAndDstAirportId(Integer srcAirportId, Integer dstAirportId)
    {
        List<Route> routes = routeRepository.findBySrcAndDstAirportIds(srcAirportId, dstAirportId);

            if (routes.isEmpty()) {
                return List.of();
            }

            //  Get source country currency (from first route – same source)
            Route firstRoute = routes.get(0);

            String sourceCountryName =
                    firstRoute.getSrc_airport().getHomeCountry().getName();

        Double exchangeRate = null;
        String localCurrency = null;

        try {
            List<CountryInfoDTO> countryInfoExt =
                    countryInfoExternal.getCountryCurrencyAndTimezone(sourceCountryName);

            log.info("source country name {}", sourceCountryName);
            log.info("countryInfoExtr {}", countryInfoExt);
            Optional<CountryInfoDTO> matchedCountry =
                    countryInfoExt.stream()
                            .filter(c ->
                                    c.getOfficialName().contains(sourceCountryName))
                            .findFirst();


            if (matchedCountry.isPresent())
            {
                localCurrency =
                        matchedCountry.get()
                                .getCurrencies()
                                .keySet()
                                .iterator()
                                .next();

                ExchangeRateDTO exchangeRateDTO =
                        currencyExchangeService.getUsdToCurrencyRate(localCurrency);

                exchangeRate = exchangeRateDTO.getMid();
            }
            else
            {
                log.info("no matching country found");
            }

        } catch (Exception ex) {
            log.warn("Currency conversion skipped for country: {}", sourceCountryName);
        }

        Double finalExchangeRate = exchangeRate;
        String finalLocalCurrency = localCurrency;

        return routes.stream()
                .map(route -> {

                    if (finalExchangeRate == null) {
                        return RouteDTO.from(route, 0.0, null); // USD only
                    }


                    RouteDTO result =  RouteDTO.from(route, finalExchangeRate, finalLocalCurrency);
                    log.info("result: {}", result );
                    return result;
                })
                .toList();
    }
}


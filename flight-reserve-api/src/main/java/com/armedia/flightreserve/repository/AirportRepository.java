package com.armedia.flightreserve.repository;

import com.armedia.flightreserve.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long>
{

    Airport findByIata(String iata);

    /* @Query("SELECT a FROM Airport a WHERE a.homeCity.id = :cityId AND a.homeCountry.id = :countryId")
     List<Airport> findByCityAndCountry(@Param("cityId") Integer cityId,
             @Param("countryId") Integer countryId);
 */
    List<Airport> findAirportsByHomeCity_IdAndHomeCountry_Id(Long homeCityId, Long homeCountryId);
}

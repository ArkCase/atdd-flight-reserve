package com.armedia.atddaccelerator.template.monolith.repository;

import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Airport findByIata(String iata);

    //List<Airport> findByCityIdAndCountryId(Integer cityId, Integer countryId);

    @Query("SELECT a FROM Airport a WHERE a.home_city.id = :cityId AND a.home_country.id = :countryId")
    List<Airport> findByCityAndCountry(@Param("cityId") Integer cityId,
            @Param("countryId") Integer countryId);
}

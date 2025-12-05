package com.armedia.atddaccelerator.template.monolith.repository;

import com.armedia.atddaccelerator.template.monolith.entity.City;
import com.armedia.atddaccelerator.template.monolith.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findCitiesByName(String name);

    Optional<City> findByNameAndCountry(String name, Country country);

    @Query("SELECT c FROM City c " +
            "WHERE c.country.id = :countryId " +
            "AND LOWER(c.name) LIKE LOWER(CONCAT(:query, '%')) " +
            "ORDER BY c.name")
    List<City> searchByCountryAndName(
            @Param("countryId") Long countryId,
            @Param("query") String query
    );
}

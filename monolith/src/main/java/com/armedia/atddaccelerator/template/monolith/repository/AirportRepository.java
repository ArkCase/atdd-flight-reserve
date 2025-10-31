package com.armedia.atddaccelerator.template.monolith.repository;

import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Airport findByIata(String iata);
}

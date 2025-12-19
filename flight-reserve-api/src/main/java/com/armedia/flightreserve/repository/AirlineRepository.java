package com.armedia.flightreserve.repository;

import com.armedia.flightreserve.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long>
{
    Airline findByCode(String code);
}


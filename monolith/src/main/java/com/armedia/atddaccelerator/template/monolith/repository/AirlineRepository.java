package com.armedia.atddaccelerator.template.monolith.repository;

import com.armedia.atddaccelerator.template.monolith.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long>
{
    Airline findByCode(String code);
}


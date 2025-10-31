package com.armedia.atddaccelerator.template.monolith.repository;

import com.armedia.atddaccelerator.template.monolith.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByName(String name);
}

package com.armedia.atddaccelerator.template.monolith.repository;

import com.armedia.atddaccelerator.template.monolith.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
}

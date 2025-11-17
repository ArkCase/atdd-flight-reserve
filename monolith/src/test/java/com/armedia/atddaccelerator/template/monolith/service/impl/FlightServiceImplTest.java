package com.armedia.atddaccelerator.template.monolith.service.impl;

import com.armedia.atddaccelerator.template.monolith.entity.dto.Graph;
import com.armedia.atddaccelerator.template.monolith.entity.dto.Node;
import com.armedia.atddaccelerator.template.monolith.repository.AirportRepository;
import com.armedia.atddaccelerator.template.monolith.repository.CityRepository;
import com.armedia.atddaccelerator.template.monolith.repository.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightServiceImplTest {
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CountryRepository countryRepository;
    private FlightServiceImpl flightService;

    @BeforeEach
    void init() {
        this.flightService = new FlightServiceImpl(new AirportServiceImpl(airportRepository),
                new CityServiceImpl(cityRepository, countryRepository, airportRepository));
    }

    @Test
    public void whenSPPSolved_thenCorrect() {

        Node skopje = new Node(1L);
        Node belgrade = new Node(2L);
        Node berlin = new Node(3L);
        Node montreal = new Node(4L);
        Node ankara = new Node(5L);
        Node sofia = new Node(6L);

        skopje.addDestination(belgrade, 10.0);
        skopje.addDestination(sofia, 15.0);
        skopje.addDestination(ankara, 20.0);

        belgrade.addDestination(montreal, 12.0);
        belgrade.addDestination(berlin, 2.0);
        belgrade.addDestination(ankara, 10.0);

        berlin.addDestination(montreal, 5.0);

        ankara.addDestination(berlin, 15.0);
        ankara.addDestination(montreal, 25.0);

        montreal.addDestination(sofia, 5.0);

        Graph graph = new Graph();

        graph.addNode(skopje);
        graph.addNode(belgrade);
        graph.addNode(berlin);
        graph.addNode(montreal);
        graph.addNode(ankara);
        graph.addNode(sofia);

        graph = flightService.calculateShortestPathFromSource(graph, skopje);

        List<Node> shortestPathForMontreal = Arrays.asList(skopje, belgrade, berlin);

        Node calculated = graph.getNodes()
                .stream()
                .filter(x -> x.getName().equals(montreal.getName()))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(calculated);
        assertEquals(calculated
                .getShortestPath(), shortestPathForMontreal);
        assertEquals(17.0, calculated.getCost());
    }
}

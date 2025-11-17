package com.armedia.atddaccelerator.template.monolith.service.impl;

import com.armedia.atddaccelerator.template.monolith.entity.Airport;
import com.armedia.atddaccelerator.template.monolith.entity.City;
import com.armedia.atddaccelerator.template.monolith.entity.Route;
import com.armedia.atddaccelerator.template.monolith.entity.dto.CheapestFlightRoute;
import com.armedia.atddaccelerator.template.monolith.entity.dto.Graph;
import com.armedia.atddaccelerator.template.monolith.entity.dto.Node;
import com.armedia.atddaccelerator.template.monolith.exception.CityNotFoundException;
import com.armedia.atddaccelerator.template.monolith.exception.RouteNotFoundException;
import com.armedia.atddaccelerator.template.monolith.service.AirportService;
import com.armedia.atddaccelerator.template.monolith.service.CityService;
import com.armedia.atddaccelerator.template.monolith.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final AirportService airportService;

    private final CityService cityService;

    private Node sourceNode;

    public CheapestFlightRoute findShortestCostPath(Long sourceCityId, Long destinationCityId) throws CityNotFoundException,
            RouteNotFoundException {
        Graph init = initRoutes(sourceCityId);

        Graph calculatedGraph = calculateShortestPathFromSource(init, sourceNode);

        // get airportId's by destinationCityId
        List<Long> dstAirportIds = getDstAirportIds(destinationCityId);

        //get all destination nodes from the calculated graph
        List<Node> destNodes = calculatedGraph.getNodes()
                .stream()
                .filter(node -> dstAirportIds.contains(node.getName()))
                .toList();

        if (!destNodes.isEmpty()) {
            // find the node with the lowest cost.
            Node minCostNode = destNodes
                    .stream()
                    .min(Comparator.comparing(Node::getCost))
                    .orElseThrow(NoSuchElementException::new);

            return createResponse(minCostNode);
        }

        throw new RouteNotFoundException(String.format("Sorry, we don't find any route between %s and %s ", sourceCityId, destinationCityId));
    }

    private Graph initRoutes(Long sourceCityId) throws CityNotFoundException {

        Optional<City> city = cityService.findById(sourceCityId);
        if (city.isEmpty()) {
            throw new CityNotFoundException("Chosen source city not exists!");
        }

        Graph graph = new Graph();

        // TODO: Works only for the first airport in the city.
        // Should add : foreach airports in city, apply the same calculation
        Airport airport = city.get().getAirports().get(0);

        //init starting node
        sourceNode = new Node(airport.getId());
        graph.addNode(sourceNode);
        createAdjNodes(graph, sourceNode, airport.getInbound_routes());

        return graph;
    }

    private void createAdjNodes(Graph graph, Node node, List<Route> inbound_routes) {
        for (Route route : inbound_routes) {
            if (graph.getNodes().stream().anyMatch(x -> x.getName().equals(route.getDst_airport().getId()))) {
                continue;
            }
            Node adjNode = new Node(route.getDst_airport().getId());
            node.addDestination(adjNode, route.getPrice());
            graph.addNode(adjNode);
            if (route.getDst_airport().getInbound_routes() != null
                    && !route.getDst_airport().getInbound_routes().isEmpty()) {
                createAdjNodes(graph, adjNode, route.getDst_airport().getInbound_routes());
            }
        }
    }

    public Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setCost(0.0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Double> adjacencyPair :
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Double edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        Double lowestDistance = Double.MAX_VALUE;
        for (Node node : unsettledNodes) {
            Double nodeDistance = node.getCost();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(Node evaluationNode,
                                          Double edgeWeigh, Node sourceNode) {
        Double sourceDistance = sourceNode.getCost();
        if (sourceDistance + edgeWeigh < evaluationNode.getCost()) {
            evaluationNode.setCost(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private CheapestFlightRoute createResponse(Node minCostNode) {
        List<String> citiesOnRoute = minCostNode.getShortestPath().stream()
                .map(node -> airportService.findById(node.getName()).get().getHomeCity().getName()).collect(Collectors.toList());
        citiesOnRoute.add(airportService.findById(minCostNode.getName()).get().getHomeCity().getName());
        return new CheapestFlightRoute(citiesOnRoute, minCostNode.getCost());
    }

    private List<Long> getDstAirportIds(Long destinationCityId) throws CityNotFoundException {
        Optional<City> city = cityService.findById(destinationCityId);
        if (city.isEmpty()) {
            throw new CityNotFoundException("Chosen destination city not exists!");
        }

        List<Airport> airports = city.get().getAirports();

        return airports.stream()
                .map(Airport::getId)
                .collect(Collectors.toList());
    }

}

package com.armedia.atddaccelerator.template.monolith.entity.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Node {

    private Long name;

    private List<Node> shortestPath = new LinkedList<>();

    private Double cost = Double.MAX_VALUE;

    // immediate neighbours
    Map<Node, Double> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, Double distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(Long name) {
        this.name = name;
    }
}

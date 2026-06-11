package com.project.model;

public class Region {
    public String name;
    public int waterRequired; // E.g., in thousands of liters or discrete trucks
    public double priorityScore; // Based on crop vulnerability
    public int waterAllocated = 0; // The output variable

    // For Graph Routing (Dijkstra)
    public int distanceFromReservoir = Integer.MAX_VALUE;

    public Region(String name, int waterRequired, double priorityScore) {
        this.name = name;
        this.waterRequired = waterRequired;
        this.priorityScore = priorityScore;
    }

    // Ratio used specifically for the Greedy algorithm
    public double getPriorityRatio() {
        return priorityScore / waterRequired;
    }
}
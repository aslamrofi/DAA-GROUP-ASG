package com.project.model;

public class Node {
    public String regionName;
    public int waterRequired; // The amount of water trucks needed
    public double priorityScore; // The urgency of the drought
    public int waterAllocated = 0; // The output: what the algorithm decides

    // Kept for when you build the Graph algorithm later
    public int distanceFromReservoir = Integer.MAX_VALUE;

    public Node(String regionName, int waterRequired, double priorityScore) {
        this.regionName = regionName;
        this.waterRequired = waterRequired;
        this.priorityScore = priorityScore;
    }
}
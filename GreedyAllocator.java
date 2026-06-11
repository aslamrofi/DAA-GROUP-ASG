package com.project.algorithm;

import com.project.model.Node;
import java.util.Comparator;
import java.util.List;

public class GreedyAllocator implements WaterDistributionAlgorithm {
    private long executionTime = 0;

    @Override
    public List<Node> distributeWater(List<Node> nodes, int totalWaterAvailable) {
        long startTime = System.nanoTime();

        int waterLeft = totalWaterAvailable;

        nodes.sort(Comparator.comparingDouble((Node node) -> {
            if (node.waterRequired == 0) {
                return Double.MAX_VALUE;
            }
            return node.priorityScore / node.waterRequired;
        }).reversed());

        for (Node node : nodes) {
            node.waterAllocated = 0;
        }

        for (Node node : nodes) {
            if (waterLeft <= 0) {
                break;
            }

            if (node.waterRequired <= waterLeft) {
                node.waterAllocated = node.waterRequired;
                waterLeft -= node.waterRequired;
            } else {
                node.waterAllocated = waterLeft;
                waterLeft = 0;
            }
        }

        executionTime = System.nanoTime() - startTime;
        return nodes;
    }

    @Override
    public long getExecutionTimeNano() {
        return executionTime;
    }

    @Override
    public String getAlgorithmName() {
        return "Greedy (Fractional Knapsack)";
    }
}
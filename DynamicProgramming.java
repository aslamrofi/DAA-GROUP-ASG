package com.project.algorithm;

import com.project.model.Node;
import java.util.List;

public class DynamicProgramming implements WaterDistributionAlgorithm {
    private long executionTime = 0;

    @Override
    public List<Node> distributeWater(List<Node> nodes, int totalWaterAvailable) {
        long startTime = System.nanoTime();

        int n = nodes.size();
        double[][] dp = new double[n + 1][totalWaterAvailable + 1];

        for (Node node : nodes) {
            node.waterAllocated = 0;
        }

        for (int i = 1; i <= n; i++) {
            Node current = nodes.get(i - 1);

            for (int w = 0; w <= totalWaterAvailable; w++) {
                if (current.waterRequired <= w) {
                    dp[i][w] = Math.max(
                            dp[i - 1][w],
                            dp[i - 1][w - current.waterRequired] + current.priorityScore
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        int remainingWater = totalWaterAvailable;

        for (int i = n; i > 0 && remainingWater > 0; i--) {
            if (dp[i][remainingWater] != dp[i - 1][remainingWater]) {
                Node allocatedNode = nodes.get(i - 1);
                allocatedNode.waterAllocated = allocatedNode.waterRequired;
                remainingWater -= allocatedNode.waterRequired;
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
        return "Dynamic Programming (0/1 Knapsack)";
    }
}
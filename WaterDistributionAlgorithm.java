package com.project.algorithm;

import com.project.model.Node;
import java.util.List;

public interface WaterDistributionAlgorithm {
    // Process the distribution and return the modified list
    List<Node> distributeWater(List<Node> nodes, int totalWaterAvailable);

    // Required for your asymptotic analysis
    long getExecutionTimeNano();

    String getAlgorithmName();
}
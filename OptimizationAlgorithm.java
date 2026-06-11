package com.project.algorithm;

import com.project.model.Node;
import java.util.List;

public interface OptimizationAlgorithm {
    // Both algorithms must accept the same inputs and return the same output type
    List<Node> calculateOptimalPath(List<Node> dataset);

    // Useful for tracking running time analysis
    long getLastExecutionTime();
}
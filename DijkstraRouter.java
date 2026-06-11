package com.project.algorithm;

import com.project.model.Node;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DijkstraRouter implements WaterDistributionAlgorithm {
    private long executionTime = 0;

    @Override
    public List<Node> distributeWater(List<Node> nodes, int totalWaterAvailable) {
        long startTime = System.nanoTime();

        int[][] graph = {
                {0, 4, 0, 0, 8},
                {4, 0, 6, 0, 2},
                {0, 6, 0, 3, 0},
                {0, 0, 3, 0, 5},
                {8, 2, 0, 5, 0}
        };

        int[] shortestDistances = calculateShortestDistances(graph, 0);

        for (int i = 0; i < nodes.size() && i < shortestDistances.length; i++) {
            nodes.get(i).distanceFromReservoir = shortestDistances[i];
            nodes.get(i).waterAllocated = 0;
        }

        nodes.sort(Comparator.comparingInt(node -> node.distanceFromReservoir));

        int waterLeft = totalWaterAvailable;

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

    private int[] calculateShortestDistances(int[][] graph, int source) {
        int numberOfNodes = graph.length;
        int[] distances = new int[numberOfNodes];
        boolean[] visited = new boolean[numberOfNodes];

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        for (int count = 0; count < numberOfNodes - 1; count++) {
            int currentNode = findNearestUnvisitedNode(distances, visited);

            if (currentNode == -1) {
                break;
            }

            visited[currentNode] = true;

            for (int neighbor = 0; neighbor < numberOfNodes; neighbor++) {
                boolean hasRoad = graph[currentNode][neighbor] > 0;
                boolean canImproveDistance =
                        !visited[neighbor]
                                && hasRoad
                                && distances[currentNode] != Integer.MAX_VALUE
                                && distances[currentNode] + graph[currentNode][neighbor] < distances[neighbor];

                if (canImproveDistance) {
                    distances[neighbor] = distances[currentNode] + graph[currentNode][neighbor];
                }
            }
        }

        return distances;
    }

    private int findNearestUnvisitedNode(int[] distances, boolean[] visited) {
        int minimumDistance = Integer.MAX_VALUE;
        int minimumIndex = -1;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] <= minimumDistance) {
                minimumDistance = distances[i];
                minimumIndex = i;
            }
        }

        return minimumIndex;
    }

    @Override
    public long getExecutionTimeNano() {
        return executionTime;
    }

    @Override
    public String getAlgorithmName() {
        return "Dijkstra Shortest Path";
    }
}
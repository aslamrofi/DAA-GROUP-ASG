package com.project.ui;

import com.project.algorithm.DijkstraRouter;
import com.project.algorithm.WaterDistributionAlgorithm;
import com.project.model.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private Label resultLabel;

    @FXML
    private Button runButton;

    private static final int TOTAL_WATER_AVAILABLE = 25;

    private final WaterDistributionAlgorithm pathfinder = new DijkstraRouter();

    @FXML
    public void handleRunAlgorithm() {
        List<Node> nodes = getTestNodes();

        pathfinder.distributeWater(nodes, TOTAL_WATER_AVAILABLE);

        long timeTaken = pathfinder.getExecutionTimeNano();

        resultLabel.setText("Dijkstra Router completed in: " + timeTaken + " ns");
    }

    private List<Node> getTestNodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node("Alor Setar (High Priority)", 10, 9.5));
        nodes.add(new Node("Kangar", 5, 6.0));
        nodes.add(new Node("Sungai Petani", 12, 8.0));
        nodes.add(new Node("Baling", 8, 7.5));
        nodes.add(new Node("Pendang", 7, 5.0));
        return nodes;
    }
}
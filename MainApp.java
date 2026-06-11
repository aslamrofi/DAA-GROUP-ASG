package com.project.ui;

import com.project.algorithm.DijkstraRouter;
import com.project.algorithm.DynamicProgramming;
import com.project.algorithm.GreedyAllocator;
import com.project.model.Node;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    private TextArea outputArea;
    private Label timeLabel;
    private Label statusLabel;

    private TextField totalWaterField;

    private final List<TextField> regionNameFields = new ArrayList<>();
    private final List<TextField> waterRequiredFields = new ArrayList<>();
    private final List<TextField> priorityScoreFields = new ArrayList<>();

    private int totalWaterAvailable = 25;
    private List<Node> currentNodes = new ArrayList<>();

    private static final String BACKGROUND_STYLE = "-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #e3f2fd);";
    private static final String CARD_STYLE = "-fx-background-color: white; -fx-background-radius: 14; -fx-padding: 16; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0, 0, 4);";
    private static final String PRIMARY_BUTTON_STYLE = "-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 8 16;";
    private static final String SECONDARY_BUTTON_STYLE = "-fx-background-color: #1565c0; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 8 16;";
    private static final String WARNING_BUTTON_STYLE = "-fx-background-color: #ef6c00; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 8 16;";
    private static final String SMALL_BUTTON_STYLE = "-fx-background-color: #455a64; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 16; -fx-padding: 6 12;";
    private static final String INPUT_STYLE = "-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #90a4ae; -fx-padding: 6;";

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Northern Malaysia Drought: Water Optimization");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #1b5e20;");

        Label subtitleLabel = new Label("Compare Greedy, Dynamic Programming, and Dijkstra-based routing strategies");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        subtitleLabel.setStyle("-fx-text-fill: #455a64;");

        timeLabel = new Label("Execution Time: 0 ns");
        timeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        timeLabel.setStyle("-fx-text-fill: #263238;");

        statusLabel = new Label("Dataset ready.");
        statusLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        statusLabel.setStyle("-fx-text-fill: #2e7d32;");

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(260);
        outputArea.setFont(Font.font("Consolas", 14));
        outputArea.setStyle("-fx-control-inner-background: #263238; -fx-text-fill: #e8f5e9; -fx-highlight-fill: #4caf50;");

        currentNodes = getDefaultNodes();

        Label totalWaterLabel = new Label("Total Available Water:");
        totalWaterLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        totalWaterLabel.setStyle("-fx-text-fill: #263238;");

        totalWaterField = new TextField(String.valueOf(totalWaterAvailable));
        totalWaterField.setPrefWidth(90);
        totalWaterField.setStyle(INPUT_STYLE);

        Button btnDecreaseWater = new Button("-10");
        Button btnIncreaseWater = new Button("+10");

        btnDecreaseWater.setStyle(SMALL_BUTTON_STYLE);
        btnIncreaseWater.setStyle(SMALL_BUTTON_STYLE);

        btnDecreaseWater.setOnAction(event -> adjustTotalWater(-10));
        btnIncreaseWater.setOnAction(event -> adjustTotalWater(10));

        HBox waterInputLayout = new HBox(10);
        waterInputLayout.setAlignment(Pos.CENTER_LEFT);
        waterInputLayout.setStyle(CARD_STYLE);
        waterInputLayout.getChildren().addAll(
                totalWaterLabel,
                btnDecreaseWater,
                totalWaterField,
                btnIncreaseWater
        );

        GridPane regionInputGrid = createRegionInputGrid();
        regionInputGrid.setStyle(CARD_STYLE);

        Button btnApplyDataset = new Button("Apply Dataset");
        Button btnGreedy = new Button("Greedy");
        Button btnDP = new Button("Dynamic Programming");
        Button btnDijkstra = new Button("Dijkstra Routing");

        btnApplyDataset.setStyle(WARNING_BUTTON_STYLE);
        btnGreedy.setStyle(PRIMARY_BUTTON_STYLE);
        btnDP.setStyle(SECONDARY_BUTTON_STYLE);
        btnDijkstra.setStyle(SECONDARY_BUTTON_STYLE);

        btnApplyDataset.setPrefWidth(150);
        btnGreedy.setPrefWidth(120);
        btnDP.setPrefWidth(180);
        btnDijkstra.setPrefWidth(160);


        btnApplyDataset.setOnAction(event -> {
            if (updateDatasetFromInputs()) {
                displayDatasetPreview();
            }
        });

        btnDP.setOnAction(event -> {
            if (!updateDatasetFromInputs()) {
                return;
            }

            DynamicProgramming dp = new DynamicProgramming();
            List<Node> freshNodes = copyCurrentNodes();

            dp.distributeWater(freshNodes, totalWaterAvailable);
            displayResults("Dynamic Programming", freshNodes, dp.getExecutionTimeNano());
        });

        btnGreedy.setOnAction(event -> {
            if (!updateDatasetFromInputs()) {
                return;
            }

            GreedyAllocator greedy = new GreedyAllocator();
            List<Node> freshNodes = copyCurrentNodes();

            greedy.distributeWater(freshNodes, totalWaterAvailable);
            displayResults("Greedy Algorithm", freshNodes, greedy.getExecutionTimeNano());
        });

        btnDijkstra.setOnAction(event -> {
            if (!updateDatasetFromInputs()) {
                return;
            }

            DijkstraRouter dijkstra = new DijkstraRouter();
            List<Node> freshNodes = copyCurrentNodes();

            dijkstra.distributeWater(freshNodes, totalWaterAvailable);
            displayResults("Dijkstra Shortest Path", freshNodes, dijkstra.getExecutionTimeNano());
        });

        HBox algorithmButtonLayout = new HBox(12);
        algorithmButtonLayout.setAlignment(Pos.CENTER);
        algorithmButtonLayout.getChildren().addAll(btnGreedy, btnDP, btnDijkstra);

        HBox applyButtonLayout = new HBox(10);
        applyButtonLayout.setAlignment(Pos.CENTER_RIGHT);
        applyButtonLayout.getChildren().add(btnApplyDataset);

        VBox buttonSection = new VBox(10);
        buttonSection.setStyle(CARD_STYLE);
        buttonSection.getChildren().addAll(applyButtonLayout, algorithmButtonLayout);

        VBox headerSection = new VBox(4);
        headerSection.setAlignment(Pos.CENTER);
        headerSection.getChildren().addAll(titleLabel, subtitleLabel);

        VBox mainLayout = new VBox(14);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle(BACKGROUND_STYLE);
        mainLayout.getChildren().addAll(
                headerSection,
                waterInputLayout,
                regionInputGrid,
                buttonSection,
                statusLabel,
                timeLabel,
                outputArea
        );

        Scene scene = new Scene(mainLayout, 850, 650);
        primaryStage.setTitle("CCS4202 Assignment 1 - Water Optimization");
        primaryStage.setScene(scene);
        primaryStage.show();

        displayDatasetPreview();
    }

    private GridPane createRegionInputGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setAlignment(Pos.CENTER_LEFT);

        grid.add(new Label("Region Name"), 0, 0);
        grid.add(new Label("Water Required"), 1, 0);
        grid.add(new Label("Priority Score"), 2, 0);

        for (int i = 0; i < currentNodes.size(); i++) {
            Node node = currentNodes.get(i);

            TextField nameField = new TextField(node.regionName);
            TextField waterField = new TextField(String.valueOf(node.waterRequired));
            TextField priorityField = new TextField(String.valueOf(node.priorityScore));

            nameField.setPrefWidth(260);
            waterField.setPrefWidth(120);
            priorityField.setPrefWidth(120);

            nameField.setStyle(INPUT_STYLE);
            waterField.setStyle(INPUT_STYLE);
            priorityField.setStyle(INPUT_STYLE);

            regionNameFields.add(nameField);
            waterRequiredFields.add(waterField);
            priorityScoreFields.add(priorityField);

            grid.add(nameField, 0, i + 1);
            grid.add(waterField, 1, i + 1);
            grid.add(priorityField, 2, i + 1);
        }

        return grid;
    }

    private void adjustTotalWater(int amount) {
        try {
            int currentWater = Integer.parseInt(totalWaterField.getText().trim());
            int updatedWater = Math.max(0, currentWater + amount);

            totalWaterField.setText(String.valueOf(updatedWater));
            totalWaterAvailable = updatedWater;

            statusLabel.setText("Total available water updated to " + updatedWater + " units.");
            statusLabel.setStyle("-fx-text-fill: #2e7d32;");

            if (updateDatasetFromInputs()) {
                displayDatasetPreview();
            }

        } catch (NumberFormatException exception) {
            statusLabel.setText("Error: enter a valid number before using +10 or -10.");
            statusLabel.setStyle("-fx-text-fill: #c62828;");
        }
    }

    private boolean updateDatasetFromInputs() {
        try {
            int parsedTotalWater = Integer.parseInt(totalWaterField.getText().trim());
 

            if (parsedTotalWater < 0) {
                statusLabel.setText("Error: total available water cannot be negative.");
                statusLabel.setStyle("-fx-text-fill: #c62828;");
                return false;
            }

            List<Node> updatedNodes = new ArrayList<>();

            for (int i = 0; i < regionNameFields.size(); i++) {
                String regionName = regionNameFields.get(i).getText().trim();
                int waterRequired = Integer.parseInt(waterRequiredFields.get(i).getText().trim());
                double priorityScore = Double.parseDouble(priorityScoreFields.get(i).getText().trim());

                // ... existing code ...

                if (regionName.isEmpty()) {
                    statusLabel.setText("Error: region name cannot be empty at row " + (i + 1) + ".");
                    statusLabel.setStyle("-fx-text-fill: #c62828;");
                    return false;
                }

                if (waterRequired < 0) {
                    statusLabel.setText("Error: water required cannot be negative at row " + (i + 1) + ".");
                    statusLabel.setStyle("-fx-text-fill: #c62828;");
                    return false;
                }

                if (priorityScore < 0) {
                    statusLabel.setText("Error: priority score cannot be negative at row " + (i + 1) + ".");
                    statusLabel.setStyle("-fx-text-fill: #c62828;");
                    return false;
                }

// ... existing code ...

                updatedNodes.add(new Node(regionName, waterRequired, priorityScore));
            }

            totalWaterAvailable = parsedTotalWater;
            currentNodes = updatedNodes;
            statusLabel.setText("Dataset updated successfully.");
            statusLabel.setStyle("-fx-text-fill: #2e7d32;");
            return true;

        } catch (NumberFormatException exception) {
            statusLabel.setText("Error: please enter valid numbers for water and priority score.");
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            return false;
        }
    }

    private List<Node> copyCurrentNodes() {
        List<Node> nodes = new ArrayList<>();

        for (Node node : currentNodes) {
            nodes.add(new Node(node.regionName, node.waterRequired, node.priorityScore));
        }

        return nodes;
    }

    private List<Node> getDefaultNodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node("Alor Setar (High Priority)", 10, 9.5));
        nodes.add(new Node("Kangar", 5, 6.0));
        nodes.add(new Node("Sungai Petani", 12, 8.0));
        nodes.add(new Node("Baling", 8, 7.5));
        nodes.add(new Node("Pendang", 7, 5.0));
        return nodes;
    }

    private void displayDatasetPreview() {
        StringBuilder results = new StringBuilder();

        results.append("--- Current Dataset ---\n");
        results.append("Total Available Water: ").append(totalWaterAvailable).append(" units\n\n");

        for (Node node : currentNodes) {
            results.append(String.format(
                    "%-28s | Needed: %2d | Priority: %.2f\n",
                    node.regionName,
                    node.waterRequired,
                    node.priorityScore
            ));
        }

        outputArea.setText(results.toString());
        timeLabel.setText("Execution Time: 0 ns");
    }

    private void displayResults(String algoName, List<Node> processedNodes, long timeNano) {
        StringBuilder results = new StringBuilder();

        results.append("--- Results for: ").append(algoName).append(" ---\n");
        results.append("Total Water Capacity: ").append(totalWaterAvailable).append(" units\n\n");

        int totalAllocated = 0;
        double totalPriorityScore = 0;

        for (Node node : processedNodes) {
            results.append(String.format(
                    "%-28s | Needed: %2d | Allocated: %2d | Priority: %.2f\n",
                    node.regionName,
                    node.waterRequired,
                    node.waterAllocated,
                    node.priorityScore
            ));

            totalAllocated += node.waterAllocated;

            if (node.waterAllocated > 0) {
                totalPriorityScore += node.priorityScore;
            }
        }

        results.append("\nSummary:\n");
        results.append("Water Used: ").append(totalAllocated).append(" / ").append(totalWaterAvailable).append("\n");
        results.append("Total Priority Score Saved: ").append(totalPriorityScore).append("\n");

        outputArea.setText(results.toString());
        timeLabel.setText("Execution Time: " + timeNano + " ns");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
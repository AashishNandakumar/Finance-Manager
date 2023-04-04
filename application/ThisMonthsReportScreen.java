package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.text.DecimalFormat;

public class ThisMonthsReportScreen extends Application {

	private double totalCost = 0;
    private static final String DB_URL = "jdbc:mysql://localhost:3307/UserDB";
    private static final String USER = "root";
    private static final String PASS = "Sh@dow4580";
    private static final String[] CATEGORIES = {"Food", "Transportation", "Entertainment", "Bills", "Others"};

    public void start(@SuppressWarnings("exports") Stage thisMonthsReportStage) throws Exception {
        // Connect to database
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

        /* THIS WORKS FOR PRICES OF ALL MONTHS
        // Query database for total cost of each category
        ArrayList<Double> categoryCosts = new ArrayList<>();
        String query = "SELECT SUM(ItemPrice) FROM Information WHERE Category=?";
        for (String category : CATEGORIES) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            categoryCosts.add(rs.getDouble(1));
        }
		*/
        Calendar now = Calendar.getInstance();
        int currentMonth = now.get(Calendar.MONTH) + 1; // Add 1 because Calendar.MONTH is zero-indexed

        // Query database for total cost of each category for CURRENT MONTH
        ArrayList<Double> categoryCosts = new ArrayList<>();
        String query = "SELECT SUM(ItemPrice) FROM Information WHERE Category=? AND MONTH(Date)=?";
        for (String category : CATEGORIES) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, category);
            stmt.setInt(2, currentMonth);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            categoryCosts.add(rs.getDouble(1));
        }
       
        
        // Create pie chart
        PieChart.Data[] pieChartData = new PieChart.Data[CATEGORIES.length];
        
        for (int i = 0; i < CATEGORIES.length; i++) {
            pieChartData[i] = new PieChart.Data(CATEGORIES[i], categoryCosts.get(i));
            totalCost += categoryCosts.get(i);
        }
        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(pieChartData);
        pieChart.setTitle("This Month's Expenses by Category");

        // Add event handler to display percentage and total amount
        DecimalFormat df = new DecimalFormat("#.##");
        Label label = new Label();
        pieChart.getData().forEach(data -> {
            data.getNode().setOnMouseClicked(event -> {
                double percentage = (data.getPieValue() / totalCost) * 100;
                String text = String.format("%s: %s%% (Rs %s)", data.getName(), df.format(percentage), df.format(data.getPieValue()));
                label.setText(text);
            });
        });

        // Making field to display total savings and total expenditure
        
        double budget = 0;
        String url = "jdbc:mysql://localhost:3307/client";
        String username = "root";
        String password = "Sh@dow4580";
        try (Connection conn1 = DriverManager.getConnection(url, username, password)) {
            
            // Create SQL statement to extract budget column from credentials table
            String sql = "SELECT budget FROM credentials LIMIT 1";
            
            // Execute SQL statement
            try (Statement stmt = conn1.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    
                    // Iterate through result set and print budget values
                    while (rs.next()) {
                    	budget = rs.getDouble("budget");
                    }
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
        
        double savings = budget - totalCost;
        
        // create labels for total expenditure and total savings
        Label expenditureLabel = new Label("Total Expenditure:  " + totalCost);
        Label savingsLabel = new Label("Total Savings: " + savings);
        
        
        
        // Display pie chart and label
        VBox vbox = new VBox(pieChart, label, expenditureLabel, savingsLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox, 800, 600);
        scene.getRoot().setStyle("-fx-background-color: #D1EAF5; -fx-font-family: 'Roboto';");
        thisMonthsReportStage.setScene(scene);
        thisMonthsReportStage.show();
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}
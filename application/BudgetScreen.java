package application;

import java.sql.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;


public class BudgetScreen extends BorderPane{

	
	public double budget;
	
	public BudgetScreen(String username, String password) {
		
		// Create a label and text field to enter budget
		Label budgetLabel = new Label("Enter your budget for this month:");
		budgetLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #D8AE5E;");
		budgetLabel.setTextFill(Color.WHITE);
		TextField budgetTextField = new TextField();
		budgetTextField.setPromptText("Enter a number");
		budgetTextField.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #ccc; -fx-border-radius: 5;");
		Button submitButton = new Button("Submit");
		submitButton.setStyle("-fx-background-color: linear-gradient(to bottom, #36b9cc, #1f8e99); -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 7px 15px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
		

		
		// Create a layout for the budget screen
		VBox vbox = new VBox();
		vbox.setSpacing(20);
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: #1E3B3D; -fx-padding: 20px;");
		vbox.getChildren().addAll(budgetLabel, budgetTextField, submitButton);

		// Add the layout to the center of the border pane
		this.setCenter(vbox);

        // Handling submit button click event
        submitButton.setOnAction(event -> {
            String budgetString = budgetTextField.getText();
            try {
                // Converting budget string to double
                 budget = Double.parseDouble(budgetString);

                // Saving budget to database
                saveBudget(budget, username, password);

                // Displaying confirmation message
                Alert alert = new Alert(AlertType.CONFIRMATION, "Budget saved successfully.");
                alert.showAndWait();

                // Closing budget screen and showing the main dash board
                Stage stage = (Stage) submitButton.getScene().getWindow();
                stage.close();
                Stage dashboardStage = new Stage();
                Dashboard dashboard = new Dashboard(budget);
                
                dashboard.start(dashboardStage);  //--> modification
                
            } catch (Exception e) {
                // Displaying error message if budget is not a valid number
                Alert alert = new Alert(AlertType.ERROR, "Please enter a valid number for your budget.");
                alert.showAndWait();
            }
        });
    }

    // Method to save budget to the database
    private void saveBudget(double budget, String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;

        try { 
            // Connecting to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/Client", "root", "Sh@dow4580");

            String sql = "UPDATE credentials SET budget = ? WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, budget);
            statement.setString(2, username);
            statement.setString(3, password);


            // Executing SQL statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closing database resources
            closeStatement(statement);
            closeConnection(connection);
        }
        
    }

    // Utility methods for closing database resources
    private void closeStatement(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
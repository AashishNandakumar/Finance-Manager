package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.sql.*;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class FinanceManager extends Application
{

	// Creating a Log-In screen
	@Override
	public void start(@SuppressWarnings("exports") Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		// Define layout
		BorderPane borderPane = new BorderPane();

		// Create top pane with logo(here banner --> Like linkedIn) and title
		HBox topPane = new HBox();
		// set padding --> (top right down left)
    	topPane.setPadding(new Insets(0, 0, 0, 0));
    	// set padding b/w other nodes of topPane
		topPane.setSpacing(0);

		
		// Add top pane to border pane
		borderPane.setTop(topPane);

		// Create center pane with form elements
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20));

		// Add user name label and field
		Label usernameLabel = new Label("Username:");
		usernameLabel.setTextFill(Color.web("#D8AE5E"));

		GridPane.setConstraints(usernameLabel, 0, 0);

		TextField usernameField = new TextField();
		// Create user name field with prompt text
		usernameField.setPromptText("Enter username");
		
		// Add styling to user name field
		usernameField.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5; -fx-border-color: #a9a9a9; -fx-border-radius: 5; -fx-padding: 5px;");
		GridPane.setConstraints(usernameField, 1, 0);

		// Add password label and field
		Label passwordLabel = new Label("Password:");
		passwordLabel.setTextFill(Color.web("#D8AE5E"));
		
		GridPane.setConstraints(passwordLabel, 0, 1);

		// Create password field with prompt text
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Enter password");
		
		// Add styling to password field
		passwordField.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5; -fx-border-color: #a9a9a9; -fx-border-radius: 5; -fx-padding: 5px;");
		GridPane.setConstraints(passwordField, 1, 1);

		// Add login and cancel buttons
		Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: linear-gradient(to bottom, #36b9cc, #1f8e99); -fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-padding: 7px 15px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #f2f2f2; -fx-border-color: #cccccc; -fx-background-radius: 5; -fx-font-size: 16px; -fx-padding: 7px 15px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
		
		// create a HBox layout --> arranges child nodes in a single horizontal row 
        HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(30);
		buttonBox.getChildren().addAll(loginButton, cancelButton);
		// (column, row, column, row)
		GridPane.setConstraints(buttonBox, 0, 5, 5, 1);

		// Add elements to center pane
		gridPane.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, buttonBox);

		// Add center pane to border pane
		borderPane.setCenter(gridPane);

		// Set padding and background color for border pane
		borderPane.setPadding(new Insets(0));
		borderPane.setStyle("-fx-background-color: #1E3B3D;");

		//#292929 Default  --> BULLSHIT CLR
		// Create scene and set it on the stage
		Scene scene = new Scene(borderPane, 800, 600);
		
		
		// Create logo
		Image logoImage = new Image("file:C:/Users/Aashish Nandakumar/Desktop/Eclipse/part-2/HelloFX/src/application/entryImage.png");
		ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(scene.getWidth());
		logoImageView.setPreserveRatio(true);
		
        topPane.setAlignment(Pos.TOP_CENTER);

		// Add logo and title to top pane
		topPane.getChildren().addAll(logoImageView);
        borderPane.setTop(topPane);

		// Show login screen
		primaryStage.setScene(scene);
		primaryStage.show();

        // Define event handlers
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            Pair<Boolean, Double> result = validateCredentials(username, password);
            if (result.getKey()) {
            	double budget = result.getValue();
                if (budget == 0) {
                    // Go to budget screen to fill in budget value
                    primaryStage.setScene(new Scene(new BudgetScreen(username, password)));
                } else {
                    // Show dash board
                    Stage dashboardStage = new Stage();
                    Dashboard dashboard = new Dashboard(budget);
                    try {
						dashboard.start(dashboardStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    primaryStage.close();
                }
            } else {
                // Notify user and give option to try again
                Alert alert = new Alert(AlertType.ERROR, "Invalid username or password.");
                alert.showAndWait();
            }
        });
        
        cancelButton.setOnAction(event -> {
            // Close application
            primaryStage.close();
        });
		
	}
	static  int attempts = 0;
	private Pair<Boolean, Double> validateCredentials(String username, String password) {
	    boolean isValid = false;
	    double budget = 0;
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    try { 
	        // Prepare SQL statement to validate credentials
	        String sql = "SELECT * FROM credentials WHERE username = ? AND password = ?";
	        
	        // Connect to the database
	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/Client", "root", "Sh@dow4580"); 
	        
	        // Prepare the SQL statement and set the values of the place holders
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, username);
	        statement.setString(2, password);
	        
	        // Execute the SQL statement and get the result set
	        resultSet = statement.executeQuery();
	        
	        // Check if there is a row returned by the query
	        if (resultSet.next()) {
	            // If so, the credentials are valid
	            isValid = true;
	            budget = resultSet.getDouble("budget");
	            
	        } else {
	            // Otherwise, increment the number of attempts and display an error message
	            attempts++;
	            if (attempts == 3) {
	                // If maximum login attempts exceeded, display an error message and exit the application
	                showError("Maximum login attempts exceeded.");
	                System.exit(0);
	            } else {
	                // If not, display an error message with the number of attempts remaining
	                showError("Invalid credentials. Attempt " + (attempts) + " of 3.");
	            }
	        }
	    } catch (SQLException e) {
	        // If there is an SQL exception, display an error message
	        showError("Error validating credentials: " + e.getMessage());
	    } finally {
	        // Close the database resources
	        closeResultSet(resultSet);
	        closeStatement(statement);
	        closeConnection(connection);
	    }
	    
	    return new Pair<>(isValid, budget); 
	}

	private void showError(String message) {
	    // Display an error message to the user using a dialog box or a label on the UI
	    // For example, using a dialog box:
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Error validating credentials");
	    alert.setContentText(message);
	    alert.showAndWait();
	}


	// Utility methods for closing database resources
	private void closeResultSet(ResultSet resultSet) {
	    if (resultSet != null) {
	        try {
	            resultSet.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

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
	
	public static void main(String[] args) {
		launch(args);
	}


}

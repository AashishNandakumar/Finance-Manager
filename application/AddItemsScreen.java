package application;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.sql.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AddItemsScreen extends Application{
	
	String selectedText = "";
	private static final String DB_URL = "jdbc:mysql://localhost:3307/UserDB";
    private static final String USER = "root";
    private static final String PASS = "Sh@dow4580";
    
	private void saveItemAndPrice(String item, double price) {
		
		 try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Information(ItemName, ItemPrice, Category, Time, Date) VALUES (?, ?, ?, ?, ?)")) {
	            stmt.setString(1, item);
	            stmt.setDouble(2, price);
	            stmt.setString(3, selectedText);
	            stmt.setString(4, getCurrentTime());
	            stmt.setString(5, getCurrentDate());
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	private String getCurrentTime() {
        return java.time.LocalTime.now().toString();
    }

    private String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }
	
	@Override
	public void start(@SuppressWarnings("exports") Stage addItemsStage) throws Exception {
		// TODO Auto-generated method stub
		
		
		BorderPane borderPane = new BorderPane();
    	
    	HBox topHBox = new HBox();
    	topHBox.setPadding(new Insets(0, 0, 0, 0));
    	topHBox.setAlignment(Pos.TOP_LEFT);
    	
    	
		
		// Creating label and text field for item name
        Label itemLabel = new Label("Item Name:");
        itemLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #D8AE5E;");
        TextField itemTextField = new TextField();
        itemTextField.setPrefHeight(40);
        itemTextField.setPromptText("Enter item name");
        itemTextField.setStyle("-fx-font-size: 16px; -fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-radius: 5px;");

        // Creating label and text field for price
        Label priceLabel = new Label("Price:");
        priceLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #D8AE5E;");
        TextField priceTextField = new TextField();
        priceTextField.setPrefHeight(40);
        priceTextField.setPromptText("Enter price");
        priceTextField.setStyle("-fx-font-size: 16px; -fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-radius: 5px;");

        // Creating radio buttons for category
        Label categoryLabel = new Label("Category:");
        categoryLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #D8AE5E;");

        ToggleGroup categoryToggle = new ToggleGroup();

        RadioButton foodRadioButton = new RadioButton("Food");
        foodRadioButton.setStyle("-fx-font-size: 16px; -fx-text-fill: #E7D49E;");
        foodRadioButton.setToggleGroup(categoryToggle);

        RadioButton transportationRadioButton = new RadioButton("Transportation");
        transportationRadioButton.setStyle("-fx-font-size: 16px; -fx-text-fill: #E7D49E;");
        transportationRadioButton.setToggleGroup(categoryToggle);

        RadioButton entertainmentRadioButton = new RadioButton("Entertainment");
        entertainmentRadioButton.setStyle("-fx-font-size: 16px; -fx-text-fill: #E7D49E;");
        entertainmentRadioButton.setToggleGroup(categoryToggle);

        RadioButton billsRadioButton = new RadioButton("Bills");
        billsRadioButton.setStyle("-fx-font-size: 16px; -fx-text-fill: #E7D49E;");
        billsRadioButton.setToggleGroup(categoryToggle);

        RadioButton othersRadioButton = new RadioButton("Others");
        othersRadioButton.setStyle("-fx-font-size: 16px; -fx-text-fill: #E7D49E;");
        othersRadioButton.setToggleGroup(categoryToggle);

        // Creating confirm and cancel buttons
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: linear-gradient(to bottom, #36b9cc, #1f8e99); -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 7px 15px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
    	confirmButton.setPrefWidth(200);


        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #f2f2f2; -fx-border-color: #cccccc; -fx-background-radius: 5; -fx-font-size: 16px; -fx-padding: 7px 15px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
    	cancelButton.setPrefWidth(200);


        // Creating banner image view
        Image bannerImage = new Image("file:C:/Users/Aashish Nandakumar/Desktop/Eclipse/part-2/HelloFX/src/application/AddItems.png");
        ImageView bannerView = new ImageView(bannerImage);

        // Creating layout for the Add Items screen
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(10));
        gridPane.setStyle("-fx-background-color: #1E3B3D; -fx-border-color: #cccccc; -fx-border-radius: 10px;");

        //gridPane.add(bannerView, 0, 0, 2, 1);
        gridPane.add(itemLabel, 0, 1);
        gridPane.add(itemTextField, 1, 1);
        gridPane.add(priceLabel, 0, 2);
        gridPane.add(priceTextField, 1, 2);
        gridPane.add(categoryLabel, 0, 3);
        gridPane.add(foodRadioButton, 1, 3);
        gridPane.add(transportationRadioButton, 2, 3);
        gridPane.add(entertainmentRadioButton, 5, 3);
        gridPane.add(billsRadioButton, 1, 4);
        gridPane.add(othersRadioButton, 2, 4);
        gridPane.add(confirmButton, 1, 7);
        gridPane.add(cancelButton, 2, 7);

        borderPane.setCenter(gridPane);
        // Setting the scene for the Add Items screen
        Scene scene = new Scene(borderPane, 800, 600);
        gridPane.setStyle("-fx-background-color: #1E3B3D; -fx-font-family: 'Roboto';");
        topHBox.getChildren().add(bannerView);
        topHBox.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(topHBox);
        
        bannerView.setFitWidth(scene.getWidth());
        bannerView.setPreserveRatio(true);
        
        
        
        addItemsStage.setTitle("Add Item");
        addItemsStage.setScene(scene);
        addItemsStage.show();
    
        
		// Handle events for radio buttons
        categoryToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (categoryToggle.getSelectedToggle() != null) {
                RadioButton selectedRadioButton = (RadioButton) categoryToggle.getSelectedToggle();
                selectedText = selectedRadioButton.getText();
            }
        });
        
        // Handling confirm and cancel button click events
	    confirmButton.setOnAction(event -> {
	        String item = itemTextField.getText();
	        String priceString = priceTextField.getText();
	        try {
	            // Converting price string to double
	            double price = Double.parseDouble(priceString);

	            // Saving item and price to database
	            saveItemAndPrice(item, price);

	            // Displaying confirmation message
	            Alert alert = new Alert(AlertType.CONFIRMATION, "Item and price saved successfully.");
	            alert.showAndWait();

	            // Closing Add Items screen and showing the Dash board
	            addItemsStage.close();
	        } catch (NumberFormatException e) {
	            // Displaying error message if price is not a valid number
	            Alert alert = new Alert(AlertType.ERROR, "Please enter a valid number for the price.");
	            alert.showAndWait();
	        }
	    });
	    
	    
	    
	    cancelButton.setOnAction(event -> {
	        // Closing Add Items screen and showing the Dash board
	    	addItemsStage.close();
	    });
		
	}
	
//	public static void main(String[] args)
//	{
//		launch(args);
//	}

}

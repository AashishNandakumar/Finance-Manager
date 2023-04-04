package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.control.Alert;


public class HistoryScreen extends Application {
    private TableView<Information> table = new TableView<Information>();
    private ObservableList<Information> data;

   
    @SuppressWarnings("unchecked")
	@Override
    public void start(@SuppressWarnings("exports") Stage historyStage) throws Exception {
        historyStage.setTitle("UserDB History");

        // Connect to the database
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/UserDB", "root", "Sh@dow4580");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Information");

        // Retrieve data from the database and add to the ObservableList
        data = FXCollections.observableArrayList();
        while (rs.next()) {
            String itemName = rs.getString("ItemName");
            double itemPrice = rs.getDouble("ItemPrice");
            String category = rs.getString("Category");
            String time = rs.getString("Time");
            String date = rs.getString("Date");
            data.add(new Information(itemName, itemPrice, category, time, date));
        }
        rs.close();
        stmt.close();
        conn.close();

        // Create the table columns and set the cell value factories
        TableColumn<Information, String> itemNameCol = new TableColumn<Information, String>("Item Name");
        itemNameCol.setPrefWidth(140); // set width to 200 pixels(ideal)
        itemNameCol.setCellValueFactory(new PropertyValueFactory<Information, String>("itemName"));
        TableColumn<Information, Double> itemPriceCol = new TableColumn<Information, Double>("Item Price");
        itemPriceCol.setPrefWidth(120); // set width to 100 pixels

        itemPriceCol.setCellValueFactory(new PropertyValueFactory<Information, Double>("itemPrice"));
        TableColumn<Information, String> categoryCol = new TableColumn<Information, String>("Category");
        categoryCol.setPrefWidth(150); // set width to 150 pixels

        categoryCol.setCellValueFactory(new PropertyValueFactory<Information, String>("category"));
        TableColumn<Information, String> timeCol = new TableColumn<Information, String>("Time");
        timeCol.setPrefWidth(205); // set width to 150 pixels

        timeCol.setCellValueFactory(new PropertyValueFactory<Information, String>("time"));
        TableColumn<Information, String> dateCol = new TableColumn<Information, String>("Date");
        dateCol.setPrefWidth(160); // set width to 100 pixels

        dateCol.setCellValueFactory(new PropertyValueFactory<Information, String>("date"));

        // Add the columns to the table
        table.getColumns().addAll(itemNameCol, itemPriceCol, categoryCol, timeCol, dateCol);
//        table.setStyle("-fx-font-size: 16pt;");
        table.setStyle("-fx-background-color: #FFFFFF; -fx-font-size: 20px;");


        // Set the data to the table
        table.setItems(data);

        // Create a VBox to hold the table
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setPrefSize(500, 500);

        vbox.getChildren().addAll(table);

        // Create a scroll pane to hold the VBox
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-text-fill: #1E3B3D;");

        // Create a label for the title
        Label title = new Label("History");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #D8AE5E;");

        // Create a BorderPane to hold the title and table
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        borderPane.setCenter(scrollPane);
        borderPane.setStyle("-fx-text-fill: #1E3B3D;");
        
        // Create a button with a circular shape
        Button button = new Button("K");
        button.setShape(new Circle(20));
        button.setMinSize(40, 40);
        button.setMaxSize(40, 40);
        button.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        
        // Set the event handler for the button
        button.setOnAction(e -> {
            // Prompt the user to confirm deletion
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete all data from the database?");
            alert.setContentText("This action cannot be undone.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // User confirmed deletion, prompt again to confirm
                Alert alert2 = new Alert(AlertType.CONFIRMATION);
                alert2.setTitle("Confirmation Dialog");
                alert2.setHeaderText("This action will permanently delete all data from the database!");
                alert2.setContentText("Are you sure you want to proceed?");

                Optional<ButtonType> result2 = alert2.showAndWait();
                if (result2.get() == ButtonType.OK){
                    // User confirmed deletion, execute SQL command to delete all data
                    try {
                        // Connect to the database
                        Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3307/UserDB", "root", "Sh@dow4580");
                        Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3307/Client", "root", "Sh@dow4580");

                        // To set the budget field in the table credentials of database client equal to null
                        String sql2 = "UPDATE credentials SET budget = null";
                        
                        PreparedStatement stm2 = conn2.prepareStatement(sql2);
                        stm2.executeUpdate();
                        stm2.close();
                        conn2.close();
                        
                        // Execute the SQL command to delete all data from the Information table
                        String sql1 = "DELETE FROM Information";
                        PreparedStatement stmt1 = conn1.prepareStatement(sql1);
                        stmt1.executeUpdate();
                        
                        // Close the connection and notify the user
                        conn1.close();
                        System.out.println("All data has been deleted from the database.");
                    } catch (SQLException ex) {
                        System.out.println("An error occurred while deleting data: " + ex.getMessage());
                    }
                }
            }
        }); 

        // Create a stack pane for the button and position it at the bottom of the border pane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(button);
        BorderPane.setAlignment(stackPane, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(stackPane, new Insets(10));

        // Add the stack pane to the border pane
        borderPane.setBottom(stackPane);

        // Create the scene and set it to the stage
        Scene scene = new Scene(borderPane, 800, 600);
        
        scene.getRoot().setStyle("-fx-background-color: #1E3B3D; -fx-font-family: 'Roboto';");

        historyStage.setScene(scene);
        historyStage.show();
    }

    
    	public class Information {
        private String itemName;
        private double itemPrice;
        private String category;
        private String time;
        private String date;

        public Information(String itemName, double itemPrice, String category, String time, String date) {
            this.itemName = itemName;
            this.itemPrice = itemPrice;
            this.category = category;
            this.time = time;
            this.date = date;
        }

        public String getItemName() {
            return itemName;
        }

        public double getItemPrice() {
            return itemPrice;
        }

        public String getCategory() {
            return category;
        }

        public String getTime() {
            return time;
        }

        public String getDate() {
            return date;
        }
    }
}
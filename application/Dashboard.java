package application;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;  
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.DropShadow;


public class Dashboard extends Application{
	
	private double budget;
    public Dashboard() {}
    public Dashboard(double budget) {
    	this.budget = budget;
    }
    
    // Get method for budget
    public double getBudget() {
        return budget;
    }

    @Override
	public void start(@SuppressWarnings("exports") Stage dashboardStage) throws Exception {
		// TODO Auto-generated method stub
    	
    	// Set up the UI
    	BorderPane borderPane = new BorderPane();
    	
    	HBox topHBox = new HBox();
    	topHBox.setPadding(new Insets(0, 0, 0, 0));
    	topHBox.setAlignment(Pos.TOP_LEFT);
    	
    	GridPane gridPane = new GridPane();
    	gridPane.setAlignment(Pos.CENTER);
    	gridPane.setHgap(10);
    	gridPane.setVgap(10);
    	gridPane.setPadding(new Insets(20, 20, 20, 20));

    	
    	
    	Button addItemsButton = new Button("Add Items");
    	addItemsButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a6186, #89253e); -fx-text-fill: white;");
    	addItemsButton.setPrefSize(150, 75);
    	addItemsButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    	DropShadow dropShadow4 = new DropShadow();
    	dropShadow4.setColor(Color.rgb(0, 0, 0, 0.3));
    	dropShadow4.setRadius(10);
    	dropShadow4.setOffsetX(5);
    	dropShadow4.setOffsetY(5);
    	addItemsButton.setEffect(dropShadow4);
    	GridPane.setConstraints(addItemsButton, 0, 0);
    	
    	// OVERKILL
//    	Image backgroundImage1 = new Image("file:C:/Users/Aashish Nandakumar/Desktop/Eclipse/part-2/HelloFX/src/application/bg1.jpg");
//    	BackgroundImage background1 = new BackgroundImage(backgroundImage1, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//    	addItemsButton.setBackground(new Background(background1));

    	Button updateItemsButton = new Button("Update Items");
    	updateItemsButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #5f2c82, #49a09d); -fx-text-fill: white;");
    	updateItemsButton.setPrefSize(150, 75);
    	updateItemsButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    	DropShadow dropShadow3 = new DropShadow();
    	dropShadow3.setColor(Color.rgb(0, 0, 0, 0.3));
    	dropShadow3.setRadius(10);
    	dropShadow3.setOffsetX(5);
    	dropShadow3.setOffsetY(5);
    	updateItemsButton.setEffect(dropShadow3);
    	GridPane.setConstraints(updateItemsButton, 2, 0);
    	
    	// OVERKILL
//    	Image backgroundImage2 = new Image("file:C:/Users/Aashish Nandakumar/Desktop/Eclipse/part-2/HelloFX/src/application/bg2.jpg");
//    	BackgroundImage background2 = new BackgroundImage(backgroundImage2, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//    	updateItemsButton.setBackground(new Background(background2));

    	Button thisMonthsReportButton = new Button("Report");
    	thisMonthsReportButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #00c3ff, #ffff1c); -fx-text-fill: white;");
    	thisMonthsReportButton.setPrefSize(150, 75);
    	thisMonthsReportButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    	DropShadow dropShadow2 = new DropShadow();
    	dropShadow2.setColor(Color.rgb(0, 0, 0, 0.3));
    	dropShadow2.setRadius(10);
    	dropShadow2.setOffsetX(5);
    	dropShadow2.setOffsetY(5);
    	thisMonthsReportButton.setEffect(dropShadow2);
    	GridPane.setConstraints(thisMonthsReportButton, 0, 3);
    	
    	// OVERKILL
//    	Image backgroundImage3 = new Image("file:C:/Users/Aashish Nandakumar/Desktop/Eclipse/part-2/HelloFX/src/application/bg3.jpg");
//    	BackgroundImage background3 = new BackgroundImage(backgroundImage3, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//    	thisMonthsReportButton.setBackground(new Background(background3));

    	Button historyButton = new Button("History");
    	historyButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #ff6b6b, #ffe66d); -fx-text-fill: white;");
    	historyButton.setPrefSize(150, 75);
    	historyButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    	DropShadow dropShadow1 = new DropShadow();
    	dropShadow1.setColor(Color.rgb(0, 0, 0, 0.3));
    	dropShadow1.setRadius(10);
    	dropShadow1.setOffsetX(5);
    	dropShadow1.setOffsetY(5);
    	historyButton.setEffect(dropShadow1);
    	GridPane.setConstraints(historyButton, 2, 3);
    	
    	// OVERKILL
//    	Image backgroundImage4 = new Image("file:C:/Users/Aashish Nandakumar/Desktop/Eclipse/part-2/HelloFX/src/application/bg412.jpg");
//    	BackgroundImage background4 = new BackgroundImage(backgroundImage4, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//    	historyButton.setBackground(new Background(background4));
    	
    	Button exitButton = new Button("Exit");
    	exitButton.setStyle("-fx-background-color: linear-gradient(#8E2DE2, #4A00E0); -fx-text-fill: white; -fx-border-color: #cccccc; -fx-background-radius: 5; -fx-font-size: 16px; -fx-padding: 7px 15px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
    	exitButton.setPrefWidth(200);
    	exitButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    	DropShadow dropShadow = new DropShadow();
    	dropShadow.setColor(Color.rgb(0, 0, 0, 0.3));
    	dropShadow.setRadius(10);
    	dropShadow.setOffsetX(5);
    	dropShadow.setOffsetY(5);
    	exitButton.setEffect(dropShadow);
    	GridPane.setConstraints(exitButton, 0, 7, 7, 3, HPos.CENTER, VPos.CENTER);

    	gridPane.getChildren().addAll(addItemsButton, updateItemsButton, thisMonthsReportButton, historyButton, exitButton);

    	borderPane.setCenter(gridPane);

    	
    	
    	
    	// taking care of the footer part
        HBox bottomHBox = new HBox();
        bottomHBox.setPadding(new Insets(10, 10, 10, 10));
        bottomHBox.setAlignment(Pos.CENTER);
        
        // ;)
        Text bottomText = new Text("© 2023 Finance Manager. All rights reserved.");
        bottomText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        bottomText.setFill(Color.web("#D8AE5E"));
        bottomHBox.getChildren().add(bottomText);
        borderPane.setBottom(bottomHBox);
        
        // Set up the scene and stage
        Scene scene = new Scene(borderPane, 800, 600);
        //scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        scene.getRoot().setStyle("-fx-background-color: #1E3B3D; -fx-font-family: 'Roboto';");

        // creating a banner and fixing it to the top
        Image logoImage = new Image("file:C:/Users/Aashish Nandakumar/Desktop/Eclipse/part-2/HelloFX/src/application/logo3.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(scene.getWidth());
        logoImageView.setPreserveRatio(true);
        topHBox.getChildren().add(logoImageView);
        topHBox.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(topHBox);


        dashboardStage.setScene(scene);
        dashboardStage.setTitle("Finance Manager Dashboard");
        dashboardStage.show();
    	
    	
        // Handling button click events
        addItemsButton.setOnAction(event -> {
        	
            // Creating Add Items screen
            Stage addItemsStage = new Stage();
            AddItemsScreen addItemsScreen = new AddItemsScreen();
            try {
				addItemsScreen.start(addItemsStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        
        updateItemsButton.setOnAction(event -> {
        	// Create Update items screen
        	Stage updateItemsStage = new Stage();
        	UpdateItemsScreen updateItemsScreen = new UpdateItemsScreen();
        	try {
				updateItemsScreen.start(updateItemsStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
    
        thisMonthsReportButton.setOnAction(event -> {
            // Creating This Month's Report screen
            Stage thisMonthsReportStage = new Stage();
            ThisMonthsReportScreen thisMonthsReportScreen = new ThisMonthsReportScreen();
            try {
				thisMonthsReportScreen.start(thisMonthsReportStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        
        historyButton.setOnAction(event -> {
            // Creating History screen
            Stage historyStage = new Stage();
            HistoryScreen historyScreen = new HistoryScreen();
            try {
				historyScreen.start(historyStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });

        exitButton.setOnAction(event -> {
            // Exiting the application
            Platform.exit();
        });
		
	}
    
//    // FOR DEBUGGING ONLY ...........
//    public static void main(String[] args) {
//		launch(args);
//	}
	
}

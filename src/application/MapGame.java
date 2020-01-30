package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MapGame extends Application {
    
    @Override
    public void start(Stage primaryStage) {
	    try {
    	    Parent start = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
    	    Scene scene = new Scene(start);
    	  
    	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    	    primaryStage.setScene(scene);
    	    primaryStage.initStyle(StageStyle.UNDECORATED);
    	    primaryStage.show();
    	    primaryStage.setTitle("8-bit Dungeon");
    	       	    
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }       	
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

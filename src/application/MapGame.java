/*  edited class by JEE  */
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
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
    	    primaryStage.show();
    	    primaryStage.setTitle("Welcome to MapGame");
    	       	    
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }       	
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

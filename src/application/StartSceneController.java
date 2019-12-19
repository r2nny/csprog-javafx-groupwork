/*  added class by JEE  */

package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage; 

public class StartSceneController implements Initializable {
	
	@FXML private Button startButton;
    @FXML private Label lblNotice;    
    @FXML private TextField txtName; 
    @FXML private ToggleGroup difficulty;
    
    @FXML private RadioButton easy;
    @FXML private RadioButton normal;
    @FXML private RadioButton hard;

	AudioClip startBgm = new AudioClip(new File("./src/application/bgm/town_bgm.mp3").toURI().toString());
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		startBgm.play();
		easy.setSelected(true);
	}
	
    public void start(ActionEvent event) throws Exception{
    	RadioButton selectedRadioButton = (RadioButton) difficulty.getSelectedToggle();

    	String curName = txtName.getText();
    	String curDifficulty = selectedRadioButton.getText();
    	System.out.print(curDifficulty);

        if(curName.equals("")){
        	lblNotice.setText("Enter your name, please.");
          	
        } else {       	
        	initGameData(curName, curDifficulty);
            lblNotice.setText("Login Success");
            
            Stage primaryStage = new Stage();
            Parent main = FXMLLoader.load(getClass().getResource("MapGame.fxml"));
            Scene scene = new Scene(main);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                   
            primaryStage.setScene(scene);           
            primaryStage.show();        
            primaryStage.setTitle("MAP GAME");
            
            Stage start = (Stage) startButton.getScene().getWindow();
            start.close();
        }        

    } 
    
    public void initGameData(String curName, String curDifficulty) {
    	GameData.name = curName;
    	GameData.difficulty = curDifficulty;
    	
    	switch(curDifficulty) {
	    	case "Easy" :
	    		MapData.DIFFICULTY = 3;
	    	case "Normal" :
	    		MapData.DIFFICULTY = 5;
	    	case "Hard" :
	    		MapData.DIFFICULTY = 7;
	    	default :
	    		MapData.DIFFICULTY = 3;
	    }
    }

    public void help(ActionEvent event) throws Exception{  
    }

}

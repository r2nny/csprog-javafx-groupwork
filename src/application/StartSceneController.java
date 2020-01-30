/*  added class by JEE  */

package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
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
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage; 

public class StartSceneController implements Initializable {
	@FXML private Button easyButton;
	@FXML private Button normalButton;
	@FXML private Button hardButton; 
	@FXML private Button helpButton;
	@FXML private ImageView game_title;
    @FXML private TextField txtName; 

	AudioClip startBgm = new AudioClip(new File("./src/application/bgm/town_bgm.mp3").toURI().toString());
	
	static GameData gameData = GameData.getInstance();
	
	final int EASY = 3;
	final int NORMAL = 5;
	final int HARD = 7;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		startBgm.play();
	}
	
	public void easyButtonAction(ActionEvent event) throws Exception{
		start(EASY);
	}
	
	public void normalButtonAction(ActionEvent event) throws Exception{
		start(NORMAL);
	}
	public void hardButtonAction(ActionEvent event) throws Exception{
		start(HARD);
	}
	
    public void start(int difficulty) throws Exception{
    	String name = txtName.getText();

    	boolean isAdmin = name.equalsIgnoreCase("ああああ") || name.equalsIgnoreCase("AAAA");

        if(!name.equals("")){       	      	
        	initGameData(gameData, name, difficulty, isAdmin);
            
            Stage primaryStage = new Stage();
            Parent main = FXMLLoader.load(getClass().getResource("MapGame.fxml"));
            Scene scene = new Scene(main);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                   
            primaryStage.setScene(scene);           
            primaryStage.show();        
            primaryStage.setTitle("8-bit Dungeon");
            primaryStage.setOnCloseRequest(event -> {
            	Platform.exit();
            });
            
            Stage start = (Stage) game_title.getScene().getWindow();
            start.close();
        }        

    } 
    
    public void initGameData(GameData g, String name, int difficulty, boolean isAdmin) {
    	g.setName(name);
    	g.setDifficulty(difficulty);
    	
    	if(isAdmin)
    		g.setIsAdmin(true);
    }

    public void helpButtonAction(ActionEvent event) throws Exception{  
    }

}

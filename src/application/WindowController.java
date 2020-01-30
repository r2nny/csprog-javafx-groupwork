package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WindowController implements Initializable {	
	
	@FXML private ImageView image;
	
    GameData gameData = GameData.getInstance();
	
	Image game_over = new Image("file:src/application/png/end_scene/game_over.png");
    Image game_clear = new Image("file:src/application/png/end_scene/game_clear.png");
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		image.setImage(gameData.getIsGameClear() && gameData.getIsGameOver() ? game_clear : game_over);
	}   

}
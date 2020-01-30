package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HowToPlayController {
	@FXML private TextField txtCode;
	@FXML private Button checkButton;
	
	public void checkButtonAction(ActionEvent event) throws Exception{
		String name = txtCode.getText();
		
		if (name.equals("3C6q4#f8P!")) {
			StartSceneController.isHardLocked = false;
		}
				
	}

}

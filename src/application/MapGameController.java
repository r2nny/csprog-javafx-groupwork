package application;

import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class MapGameController implements Initializable {  
	@FXML private Label metadataLabel;	
	@FXML private Label noticeLabel;
	@FXML private ImageView heart1;
	@FXML private ImageView heart2; 
	@FXML private ImageView heart3;
	@FXML private ImageView heart4;
	@FXML private ImageView heart5;
	@FXML private ImageView heart6;
	
	@FXML private ImageView[] heart = new ImageView[6];
	
	Image heart_zero = new Image("file:src/application/png/chara/heart_zero.png");
	Image heart_half = new Image("file:src/application/png/chara/heart_half.png");
	Image heart_full = new Image("file:src/application/png/chara/heart_full.png");

    public GridPane mapGrid;
    public ImageView[] mapImageViews;
//  public Group[] mapGroups;
    
    String adminText = "";
    AudioClip startBgm = new AudioClip(new File("./src/application/bgm/town_bgm.mp3").toURI().toString());
    
    private final static int MAP_WIDTH = 52;
    private final static int MAP_HEIGHT = 36; 
    private final static int VIEW_WIDTH  = 13;
    private final static int VIEW_HEIGHT = 9;
    private static int chara_x, chara_y;
    public static String noticeText = "";
    
    // playable character
    public static final int TYPE_CHARA_PRIST = 18;
    private int mainChara = TYPE_CHARA_PRIST;
    
    GameData gameData = GameData.getInstance();
    MapData mapData = MapData.getInstance();
    MoveChara moveChara = MoveChara.getInstance();
    Chara chara = Chara.getInstance();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    	changeBgm("start");
    	
    	if(gameData.getIsAdmin()) {
    		adminText = "*** CHEAT MODE *** \n";
    	}
    	
    	heart[0] = heart1;
    	heart[1] = heart2;
    	heart[2] = heart3;
    	heart[3] = heart4;
    	heart[4] = heart5;
    	heart[5] = heart6;
    	
    	showHeart();
    	setMetadataText(gameData);
//      mapGroups = new Group[mapData.getHeight() * mapData.getWidth()];
        mapImageViews = new ImageView[MAP_HEIGHT * MAP_WIDTH];
        mapPrint(moveChara, mapData);
  
    }
    
    // refresh view 13*9
    public void loadImageViews() {   
    	mapData.setImageViews();
    	
    	for(int y=0; y<VIEW_HEIGHT; y++){
            for(int x=0; x<VIEW_WIDTH; x++){
                int index = y * VIEW_WIDTH + x;
                mapImageViews[index] = mapData.getImageView(x, y);
            }
        }    	
    }

    public void changeBgm(String scene) { 
    	startBgm.stop();
    	startBgm.setCycleCount(AudioClip.INDEFINITE);  	
    	
    	switch(scene) {
    		case "start" :
    			startBgm.play();
    			break;
    			
    		case "stop" :
    			startBgm.stop();
    			break;
    			
    	    default :
    	    	startBgm.play();
    	    	
        } 	
    }
	
    public void mapPrint(MoveChara c, MapData m){
    	loadImageViews();
        mapGrid.getChildren().clear();
        
        for(int y=0; y<VIEW_HEIGHT; y++){
            for(int x=0; x<VIEW_WIDTH; x++){
                int index = y * VIEW_WIDTH + x;
                
                // prevents illegalArgumentException
                mapGrid.getChildren().remove(mapImageViews[index]);
                mapGrid.add(mapImageViews[index], x, y);                
            }
        }
    }

    public void func1ButtonAction(ActionEvent event) { }
    public void func2ButtonAction(ActionEvent event) { }
    public void func3ButtonAction(ActionEvent event) { }
    public void func4ButtonAction(ActionEvent event) { }

    public void keyAction(KeyEvent event) throws Exception{
        KeyCode key = event.getCode();
        if (key == KeyCode.DOWN){
            buttonAction("DOWN");
        } else if (key == KeyCode.RIGHT){
            buttonAction("RIGHT");
        } else if (key == KeyCode.LEFT){
            buttonAction("LEFT");
        } else if (key == KeyCode.UP){
            buttonAction("UP");
        }
    }
    
    public void buttonAction(String key) throws Exception {
    	int dx = 0, dy = 0;
    	
    	switch(key) {
    	case "DOWN" :
    		dx =  0; dy =  1; break;
    	case "RIGHT" :
    		dx =  1; dy =  0; break;
    	case "LEFT" :
    		dx = -1; dy =  0; break;
    	case "UP" :
    		dx =  0; dy = -1; break;
    	}
    	
    	moveChara.move(dx, dy);
    	judge(mapData, gameData, dx, dy);      
        mapPrint(moveChara, mapData);
    }
    
    // TODO: refactoring
    public void judge(MapData m, GameData g, int dx, int dy) throws Exception {
    	if(g.getIsGameOver()) { 
    		changeBgm("stop");
    		Stage primaryStage = new Stage();
            Parent main = FXMLLoader.load(getClass().getResource("GameOver.fxml"));
            Scene scene = new Scene(main);
                   
            primaryStage.setScene(scene);   
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();        
    	}   	
    	
    	chara_x =  m.getChara_x();
    	chara_y =  m.getChara_y();
    	
    	noticeLabel.setText(noticeText);
        
        showHeart();
        setMetadataText(gameData);
    }
    
    public void showHeart() {
    	int curHp = chara.getHp();
    	int temHp = curHp;
    			
    	for(int i=0; i<heart.length; i++) {
    		if(temHp >= 2) {
    			heart[i].setImage(heart_full);
    			temHp -= 2;
    			continue;
    		} else if (temHp == 1) {
    			heart[i].setImage(heart_half);
    			temHp -= 1;
    			continue;
    		} else {
    			heart[i].setImage(heart_zero);
    		}
    		
    	}
    }
    
    public void setMetadataText(GameData g) {
    	metadataLabel.setText(adminText +
		   			 "NAME : " + g.getName() + "  ||  " + 
		   			 "STAGE : " + g.getStage() + "  ||  " +
		   			 "COIN : " + g.getCoin() + "  ||  " +
		   			 "Gold Key : " + g.getKey_gold() + "  ||  " +
		   			 "Silver Key : " + g.getKey_silver());   	
    }

	public void getCharaLocation() {
		int[] chara_location = new int[2];
		
		chara_location = MapData.getObject(TYPE_CHARA_PRIST);
		chara_x = chara_location[0];
		chara_x = chara_location[1];
	}		
}

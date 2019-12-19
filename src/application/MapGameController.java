package application;

import java.net.URL;
import java.io.File;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class MapGameController implements Initializable {  
	@FXML private Label name;	
	@FXML private Label noticeLabel;

    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public ImageView[] mapImageViews;
//  public Group[] mapGroups;
    
    /* added variables by JEE */ 
    String adminText = "";
    AudioClip startBgm = new AudioClip(new File("./src/application/bgm/town_bgm.mp3").toURI().toString());

    @Override
    public void initialize(URL url, ResourceBundle rb) {   	
    	/* added method by JEE */
    	changeBgm("start");
    	
    	if(GameData.isAdmin) {
    		adminText = "*** CHEAT MODE *** \n";
    	}
    	
    	name.setText(adminText +
					 "NAME : " + GameData.name + "\n" + 
   			 		 "DIFFICULTY : " + GameData.difficulty + "\n" + 
   			 		 "STAGE : " + GameData.stage + "\n" +
   			 		 "COIN : " + GameData.coin);	
    	
        mapData = new MapData(21,15, GameData.DIFFICULTY);
        chara = new MoveChara(1,1,mapData);
//      mapGroups = new Group[mapData.getHeight() * mapData.getWidth()];
        mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x,y);
            }
        }
        mapPrint(chara, mapData);
  
    }
    
    /* added method by JEE */
    public void changeBgm(String scene) { 
    	startBgm.stop();
    	startBgm.setCycleCount(AudioClip.INDEFINITE);  	
    	
    	switch(scene) {
    		case "start" :
    			startBgm.play();
    			break;
    			
    	    default :
    	    	startBgm.play();
    	    	} 	
    }
	
    public void mapPrint(MoveChara c, MapData m){
        int cx = c.getPosX();
        int cy = c.getPosY();
        mapGrid.getChildren().clear();
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                if (x==cx && y==cy) {
                    mapGrid.add(c.getCharaImageView(), x, y);
                } else {
                    mapGrid.add(mapImageViews[index], x, y);
                }
            }
        }
    }

    public void func1ButtonAction(ActionEvent event) { }
    public void func2ButtonAction(ActionEvent event) { }
    public void func3ButtonAction(ActionEvent event) { }
    public void func4ButtonAction(ActionEvent event) { }

    public void keyAction(KeyEvent event){
        KeyCode key = event.getCode();
        if (key == KeyCode.DOWN){
            downButtonAction();
        } else if (key == KeyCode.RIGHT){
            rightButtonAction();
        } else if (key == KeyCode.LEFT){
            leftButtonAction();
        } else if (key == KeyCode.UP){
            upButtonAction();
        }
    }

    public void outputAction(String actionString) {
        System.out.println("Select Action: " + actionString);
    }

    public void downButtonAction(){
        outputAction("DOWN");
        chara.setCharaDir(MoveChara.TYPE_DOWN);
        chara.move(0, 1);
        
        /* 松本 */
        judge();
        
        mapPrint(chara, mapData);
    }
    
    public void downButtonAction(ActionEvent event) {
        downButtonAction();
    }

    public void rightButtonAction(){
        outputAction("RIGHT");
        chara.setCharaDir(MoveChara.TYPE_RIGHT);
        chara.move( 1, 0);
        
        /* 松本 */
        judge();
        
        mapPrint(chara, mapData);
    }
    public void rightButtonAction(ActionEvent event) {
        rightButtonAction();
    }
    
    /* added method by JEE (LEFT, UP) */
    public void leftButtonAction(){
        outputAction("LEFT");
        chara.setCharaDir(MoveChara.TYPE_LEFT);
        chara.move(-1, 0);
        
        /* 松本 */
        judge();
        
        mapPrint(chara, mapData);
    }
    
    public void leftButtonAction(ActionEvent event) {
        leftButtonAction();
    }
    
    public void upButtonAction(){
        outputAction("UP");
        chara.setCharaDir(MoveChara.TYPE_UP);
        chara.move(0, -1);
        
        /* 松本 */
        judge();
        
        mapPrint(chara, mapData);
    }
    
    public void upButtonAction(ActionEvent event) {
        upButtonAction();
    }
    
    /* 松本 */
    public void judge() {
        if (mapData.getMap(chara.getPosX(), chara.getPosY()) == MapData.TYPE_COIN){
            GameData.coin ++;
            
            /* JEE */
            name.setText(adminText +
            			 "NAME : " + GameData.name + "\n" + 
            			 "DIFFICULTY : " + GameData.difficulty + "\n" +
            			 "STAGE : " + GameData.stage + "\n" +
            			 "COIN : " + GameData.coin);
            
            mapData.setMap(chara.getPosX(), chara.getPosY(), MapData.TYPE_NONE);
            mapData.setImageViews();
            mapImageViews[chara.getPosY() * mapData.getWidth() + chara.getPosX()] = mapData.getImageView(chara.getPosX(), chara.getPosY());
        }
        
        if (mapData.getMap(chara.getPosX(), chara.getPosY()) == MapData.TYPE_GOAL){
            if(GameData.coin ==  GameData.DIFFICULTY){
                initialize(null,null);
                GameData.coin = 0;
                GameData.stage ++;
                
                name.setText(adminText +
                			 "NAME : " + GameData.name + "\n" + 
		           			 "DIFFICULTY : " + GameData.difficulty + "\n" +
		           			 "STAGE : " + GameData.stage + "\n" +
		           			 "COIN : " + GameData.coin);
                
                noticeLabel.setText("Stage " + GameData.stage);	
                
            } else {
                System.out.println("You don't have enough coin.");
                noticeLabel.setText("You don't have enough coin.");	            
            }
        }
    }
}

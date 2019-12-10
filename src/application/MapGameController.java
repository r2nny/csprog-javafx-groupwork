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

    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public ImageView[] mapImageViews;
//  public Group[] mapGroups;
    
    /* added variables by JEE */
    AudioClip startBgm = new AudioClip(new File("./src/application/bgm/town_bgm.mp3").toURI().toString());

    @Override
    public void initialize(URL url, ResourceBundle rb) {   	

    	/* added method by JEE */
    	changeBgm("start");
    	name.setText("NAME : " + GameData.name + "\n" + "DIFFICULTY : " + GameData.difficulty);
    	
        mapData = new MapData(21,15);
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
    	startBgm.setCycleCount(AudioClip.INDEFINITE);  	
    	startBgm.stop();
    	
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
        mapPrint(chara, mapData);
    }
    public void downButtonAction(ActionEvent event) {
        downButtonAction();
    }

    public void rightButtonAction(){
        outputAction("RIGHT");
        chara.setCharaDir(MoveChara.TYPE_RIGHT);
        chara.move( 1, 0);
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
        mapPrint(chara, mapData);
    }
    
    public void leftButtonAction(ActionEvent event) {
        leftButtonAction();
    }
    
    public void upButtonAction(){
        outputAction("UP");
        chara.setCharaDir(MoveChara.TYPE_UP);
        chara.move(0, -1);
        mapPrint(chara, mapData);
    }
    public void upButtonAction(ActionEvent event) {
        upButtonAction();
    }
}

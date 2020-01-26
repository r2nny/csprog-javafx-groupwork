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
	@FXML private Label metadataLabel;	
	@FXML private Label noticeLabel;

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
    
    // playable character
    public static final int TYPE_CHARA_PRIST = 20;
    private int mainChara = TYPE_CHARA_PRIST;
    
    GameData gameData = GameData.getInstance();
    MapData mapData = MapData.getInstance();
    MoveChara moveChara = MoveChara.getInstance();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {   	
    	changeBgm("start");
    	
    	if(gameData.getIsAdmin()) {
    		adminText = "*** CHEAT MODE *** \n";
    	}
    	
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
        moveChara.move(0, 1);

        judge(mapData, gameData);      
        mapPrint(moveChara, mapData);
        mapData.printMap();
    }
    
    public void downButtonAction(ActionEvent event) {
        downButtonAction();
    }

    public void rightButtonAction(){
        outputAction("RIGHT");
        moveChara.move( 1, 0);

        judge(mapData, gameData);      
        mapPrint(moveChara, mapData);
        mapData.printMap();
    }
    public void rightButtonAction(ActionEvent event) {
        rightButtonAction();
    }

    public void leftButtonAction(){
        outputAction("LEFT");
        moveChara.move(-1, 0);

        judge(mapData, gameData);       
        mapPrint(moveChara, mapData);
        mapData.printMap();
    }
    
    public void leftButtonAction(ActionEvent event) {
        leftButtonAction();
    }
    
    public void upButtonAction(){
        outputAction("UP");
        moveChara.move(0, -1);

        judge(mapData, gameData);       
        mapPrint(moveChara, mapData);
        mapData.printMap();
    }
    
    public void upButtonAction(ActionEvent event) {
        upButtonAction();
    }

    public void judge(MapData m, GameData g) {
    	chara_x =  m.getChara_x();
    	chara_y =  m.getChara_y();
        
        if (mapData.getMap(chara_x, chara_y) == MapData.TYPE_GOAL){
            if(g.getCoin() ==  g.getDifficulty()){
                initialize(null,null);
                g.addCoin();
                g.addStage();
                
                noticeLabel.setText("Stage " + g.getStage());	
                
            } else {
                System.out.println("You don't have enough coin.");
                noticeLabel.setText("You don't have enough coin.");	            
            }
        }
        
        setMetadataText(gameData);
    }
    
    public void setMetadataText(GameData g) {
    	metadataLabel.setText(adminText +
		   			 "NAME : " + g.getName() + "\n" + 
		   			 "DIFFICULTY : " + g.getDifficulty() + "\n" +
		   			 "STAGE : " + g.getStage() + "\n" +
		   			 "COIN : " + g.getCoin());
    	
    }

	public void getCharaLocation() {
		int[] chara_location = new int[2];
		
		chara_location = MapData.getObject(TYPE_CHARA_PRIST);
		chara_x = chara_location[0];
		chara_x = chara_location[1];
	}		
}

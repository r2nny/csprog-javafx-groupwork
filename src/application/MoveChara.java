package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;

public class MoveChara {  
	// tiles
	public static final int TYPE_WALKABLE_TILE = 0;
	// playable character
    public static final int TYPE_CHARA_PRIST = 20;
    
	public static MoveChara moveChara = new MoveChara();
    MapData mapData = MapData.getInstance();
    GameData gameData = GameData.getInstance();
   
    MoveChara(){	
    }
    
    public static MoveChara getInstance() {
        return moveChara;
    }
    
    public boolean canMove(int dx, int dy){
    	int type = mapData.getMap(mapData.getChara_x()+dx, mapData.getChara_y()+dy);
    	
    	switch(type) {
	    	case MapData.TYPE_WALKABLE_TILE : 
	    	case MapData.TYPE_COIN :
	    	case MapData.TYPE_ENEMY_SLIME :
	    		return true;
	    	default : 
	    		return false;
    	}
    }

    public boolean move(int dx, int dy){
        if (canMove(dx,dy)){
        	judgeItem(mapData, gameData);
        	mapData.addChara_x(dx);
        	mapData.addChara_y(dy);
        	mapData.setMap(mapData.getChara_x(), mapData.getChara_y(), TYPE_CHARA_PRIST);
            return true;
        } else {
            return false;
        }
    }   
    
    public void judgeItem(MapData m, GameData g) {
    	int chara_x =  m.getChara_x();
    	int chara_y =  m.getChara_y();
    	
        if (mapData.getMap(chara_x, chara_y) == MapData.TYPE_COIN){
            g.addCoin();
            
        } else if (mapData.getMap(chara_x, chara_y) == MapData.TYPE_GOAL){
        	
        }
        
        mapData.setMap(chara_x, chara_y, MapData.TYPE_WALKABLE_TILE);
    }
}

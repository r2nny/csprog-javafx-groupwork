package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;

public class MoveChara {  
	// tiles
	public static final int TYPE_WALKABLE_TILE = 0;
	// playable character
    public static final int TYPE_CHARA_PRIST = 18;
    
	public static MoveChara moveChara = new MoveChara();
    MapData mapData = MapData.getInstance();
    GameData gameData = GameData.getInstance();
    Chara chara = Chara.getInstance();
   
    MoveChara(){	
    }
    
    public static MoveChara getInstance() {
        return moveChara;
    }
    
    public boolean canMove(int dx, int dy){
    	int type = mapData.getMap(mapData.getChara_x()+dx, mapData.getChara_y()+dy);
    	
    	switch(type) {
	    	case MapData.TYPE_WALKABLE_TILE : 
	    	case MapData.TYPE_ITEM_COIN :
	    	case MapData.TYPE_ITEM_KEY_GOLD :
	    	case MapData.TYPE_ITEM_KEY_SILVER :
	    		return true;
	    	case MapData.TYPE_TILE_GOAL :
	    	case MapData.TYPE_ENEMY_SLIME_HP3 :
	    	case MapData.TYPE_ENEMY_SLIME_HP2 :
	    	case MapData.TYPE_ENEMY_SLIME_HP1 :
	    	case MapData.TYPE_ENEMY_FLY_HP3 :
	    	case MapData.TYPE_ENEMY_FLY_HP2 :
	    	case MapData.TYPE_ENEMY_FLY_HP1 :
	    		return false;
	    	default : 
	    		return false;
    	}
    }

    public void move(int dx, int dy){
        if (canMove(dx,dy)){
        	mapData.setMap(mapData.getChara_x(), mapData.getChara_y(), TYPE_WALKABLE_TILE);
        	mapData.addChara_x(dx);
        	mapData.addChara_y(dy);
        	judgeMoveable();
        	mapData.setMap(mapData.getChara_x(), mapData.getChara_y(), TYPE_CHARA_PRIST);
        } else {
        	judgeUnmoveable(dx, dy);
        }
    }   
    
    public void judgeMoveable() {
    	int chara_x =  mapData.getChara_x();
    	int chara_y =  mapData.getChara_y();
    	int type = mapData.getMap(chara_x, chara_y);
    	
    	switch(type) {
    		case MapData.TYPE_ITEM_COIN:
		        gameData.addCoin(); 
		        mapData.setMap(chara_x, chara_y, MapData.TYPE_WALKABLE_TILE);
		        break;
    			
    		case MapData.TYPE_WALKABLE_TILE:
    			break;
    		
    		case MapData.TYPE_ITEM_KEY_GOLD :
    			mapData.setMap(chara_x, chara_y, MapData.TYPE_WALKABLE_TILE);
    			break;
    			
	    	case MapData.TYPE_ITEM_KEY_SILVER :
	    		mapData.setMap(chara_x, chara_y, MapData.TYPE_WALKABLE_TILE);
	    		break;
    		default:
	        	
    	}
    }
    
    public void judgeUnmoveable(int dx, int dy) {
    	int chara_x =  mapData.getChara_x();
    	int chara_y =  mapData.getChara_y();
    	int enemy_type = mapData.getMap(chara_x + dx, chara_y + dy);
    	
    	// return if type is MAP TILE
    	if (enemy_type < TYPE_CHARA_PRIST) return;
    	
    	// TODO : refactoring
    	switch(enemy_type) {
	    	case MapData.TYPE_ENEMY_SLIME_HP3 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_SLIME_HP2); break;
	    	case MapData.TYPE_ENEMY_SLIME_HP2 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_SLIME_HP1); break;
	    	case MapData.TYPE_ENEMY_SLIME_HP1 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_WALKABLE_TILE); break;
	    	case MapData.TYPE_ENEMY_FLY_HP3 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_FLY_HP2); break;
	    	case MapData.TYPE_ENEMY_FLY_HP2 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_FLY_HP1); break;
	    	case MapData.TYPE_ENEMY_FLY_HP1 :  
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_WALKABLE_TILE); break;
	    		
	    	// return case (hp doesn't fall)
	    	case MapData.TYPE_ITEM_CHEST:
	    		mapData.setMap(chara_x + dx, chara_y + dy, 
	    				      (int)(Math.random() * 10) < 3 ? MapData.TYPE_ITEM_KEY_GOLD : MapData.TYPE_ITEM_KEY_SILVER); 
	    		return;
	    		
	    	case MapData.TYPE_TILE_GOAL:
	    		if(gameData.getCoin() >=  gameData.getDifficulty()) {
	    			gameData.resetCoin();
	                gameData.addStage();
	                mapData.newStage();
	    		}
	    		return;
    	}
    	
    	chara.downHp();
    	if(chara.getHp() <= 0) {
    		gameData.setIsGameOver(true);
    	}
    }
}

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
	    	case MapData.TYPE_ITEM_POTION :
	    	case MapData.TYPE_ITEM_ELIXIR :    
	    	case MapData.TYPE_ITEM_FORBIDDEN_BOOK :
	    		return true;
	    	case MapData.TYPE_TILE_GOAL :
	    	case MapData.TYPE_ENEMY_SLIME_HP1 :
	    	case MapData.TYPE_ENEMY_SKULL_HP2 :
	    	case MapData.TYPE_ENEMY_SKULL_HP1 :
	    	case MapData.TYPE_ENEMY_FLY_HP3 :
	    	case MapData.TYPE_ENEMY_FLY_HP2 :
	    	case MapData.TYPE_ENEMY_FLY_HP1 :
	    	case MapData.TYPE_ENEMY_SKELETON1_HP3 :
	    	case MapData.TYPE_ENEMY_SKELETON1_HP2 :
	    	case MapData.TYPE_ENEMY_SKELETON1_HP1 :
	    	case MapData.TYPE_ENEMY_SKELETON2_HP3 :
	    	case MapData.TYPE_ENEMY_SKELETON2_HP2 :
	    	case MapData.TYPE_ENEMY_SKELETON2_HP1 :
	    	case MapData.TYPE_ENEMY_VAMPIRE_HP4 :
	    	case MapData.TYPE_ENEMY_VAMPIRE_HP3 :
	    	case MapData.TYPE_ENEMY_VAMPIRE_HP2 :
	    	case MapData.TYPE_ENEMY_VAMPIRE_HP1 :
	    	case MapData.TYPE_ENEMY_KRAKEN_HP7 :
	    	case MapData.TYPE_ENEMY_KRAKEN_HP6 :
	    	case MapData.TYPE_ENEMY_KRAKEN_HP5 :
	    	case MapData.TYPE_ENEMY_KRAKEN_HP4 :
	    	case MapData.TYPE_ENEMY_KRAKEN_HP3 :
	    	case MapData.TYPE_ENEMY_KRAKEN_HP2 :
	    	case MapData.TYPE_ENEMY_KRAKEN_HP1 :
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
        
        mapData.moveEnemy();
    }   
    
    public int chooseRandomItem() {
    	int value = (int)(Math.random() * 10);
    	
    	switch(value) {
    		case 0 :
    			return MapData.TYPE_ITEM_KEY_GOLD;
    		case 1 : case 2 :
    			return MapData.TYPE_ITEM_KEY_SILVER;
    		case 3 : case 4 : case 5 : case 6 : case 7 :
    			return MapData.TYPE_ITEM_POTION;
    		case 8 : case 9 :
    			return MapData.TYPE_ITEM_ELIXIR;
    			
    	}
    	return 0;
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
    			gameData.addKey_gold();
    			mapData.setMap(chara_x, chara_y, MapData.TYPE_WALKABLE_TILE);
    			break;
    			
	    	case MapData.TYPE_ITEM_KEY_SILVER :
	    		gameData.addKey_silver();
	    		mapData.setMap(chara_x, chara_y, MapData.TYPE_WALKABLE_TILE);
	    		break;
	    		
	    	case MapData.TYPE_ITEM_POTION :
	    		if(chara.getHp() != 12) chara.upHp();
	    		mapData.setMap(chara_x, chara_y, MapData.TYPE_WALKABLE_TILE);
	    		break;
	    		
	    	case MapData.TYPE_ITEM_ELIXIR : 
	    		while(chara.getHp() != 12) chara.upHp();
	    		mapData.setMap(chara_x, chara_y, MapData.TYPE_WALKABLE_TILE);
	    		break;
	    	
	    	case MapData.TYPE_ITEM_FORBIDDEN_BOOK :
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
	    	case MapData.TYPE_ENEMY_SLIME_HP1 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_WALKABLE_TILE); break;
	    	case MapData.TYPE_ENEMY_SKULL_HP2 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_SKULL_HP1); break;
	    	case MapData.TYPE_ENEMY_SKULL_HP1 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_WALKABLE_TILE); break;
	    	case MapData.TYPE_ENEMY_FLY_HP3 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_FLY_HP2); break;
	    	case MapData.TYPE_ENEMY_FLY_HP2 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_FLY_HP1); break;
	    	case MapData.TYPE_ENEMY_FLY_HP1 :  
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_WALKABLE_TILE); break;
	    	case MapData.TYPE_ENEMY_SKELETON1_HP3 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_SKELETON1_HP2); break;
	    	case MapData.TYPE_ENEMY_SKELETON1_HP2 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_SKELETON1_HP1); break;
	    	case MapData.TYPE_ENEMY_SKELETON1_HP1 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_WALKABLE_TILE); break;
	    	case MapData.TYPE_ENEMY_SKELETON2_HP3 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_SKELETON2_HP2); break;
	    	case MapData.TYPE_ENEMY_SKELETON2_HP2 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_SKELETON2_HP1); break;
	    	case MapData.TYPE_ENEMY_SKELETON2_HP1 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_WALKABLE_TILE); break;
	    	case MapData.TYPE_ENEMY_VAMPIRE_HP4 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_VAMPIRE_HP3); break;
	    	case MapData.TYPE_ENEMY_VAMPIRE_HP3 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_VAMPIRE_HP2); break;
	    	case MapData.TYPE_ENEMY_VAMPIRE_HP2 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_VAMPIRE_HP1); break;
	    	case MapData.TYPE_ENEMY_VAMPIRE_HP1 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_WALKABLE_TILE); break;
	    	case MapData.TYPE_ENEMY_KRAKEN_HP7 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_KRAKEN_HP6); break;
	    	case MapData.TYPE_ENEMY_KRAKEN_HP6 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_KRAKEN_HP5); break;
	    	case MapData.TYPE_ENEMY_KRAKEN_HP5 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_KRAKEN_HP4); break;
	    	case MapData.TYPE_ENEMY_KRAKEN_HP4 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_KRAKEN_HP3); break;
	    	case MapData.TYPE_ENEMY_KRAKEN_HP3 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_KRAKEN_HP2); break;
	    	case MapData.TYPE_ENEMY_KRAKEN_HP2 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ENEMY_KRAKEN_HP1); break;
	    	case MapData.TYPE_ENEMY_KRAKEN_HP1 :
	    		mapData.setMap(chara_x + dx, chara_y + dy, MapData.TYPE_ITEM_FORBIDDEN_BOOK); break;
	    		
	    	// return case (hp doesn't fall)
	    	case MapData.TYPE_ITEM_CHEST:
	    		mapData.setMap(chara_x + dx, chara_y + dy, 
	    				      chooseRandomItem()); 
	    		return;
	    		
	    	case MapData.TYPE_TILE_GOAL:
	    		if(gameData.getCoin() >=  gameData.getDifficulty()) {
	    			gameData.resetCoin();
	                gameData.addStage();
	                mapData.newStage();
	                MapGameController.noticeText = "Stage " + Integer.toString(gameData.getStage());
	    		} else if (gameData.getKey_gold() >= 1) {
	    			gameData.remKey_gold();
	    			gameData.resetCoin();
	                gameData.addStage();
	                mapData.newStage();
	                MapGameController.noticeText = "GOLD KEY! \nStage " + Integer.toString(gameData.getStage());
	    		}
	    		return;
    	}
    	
    	chara.downHp();
    	
    	// kraken critical attack (30%)
    	if(MapData.TYPE_ENEMY_KRAKEN_HP7 <= enemy_type && enemy_type <= MapData.TYPE_ENEMY_KRAKEN_HP1) {
    		if((int)(Math.random() * 10) < 3){
    			chara.downHp();
    		}
    	}
    	if(chara.getHp() <= 0) {
    		gameData.setIsGameOver(true);
    	}
    	
    	
    }
}

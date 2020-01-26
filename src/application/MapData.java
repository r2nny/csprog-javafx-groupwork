package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData { // Singleton class
	// TODO: remove "static"
    public static final int TYPE_WALKABLE_TILE = 0;
    public static final int TYPE_TOP_TILE = 1;
    public static final int TYPE_RIGHT_TILE = 2;
    public static final int TYPE_BOTTOM_TILE = 3;
    public static final int TYPE_LEFT_TILE = 4;
    public static final int TYPE_TOPLEFT_TILE = 5;
    public static final int TYPE_TOPRIGHT_TILE = 6;
    public static final int TYPE_BOTTOMRIGHT_TILE = 7;
    public static final int TYPE_BOTTOMLEFT_TILE = 8;
    public static final int TYPE_AISLE_BOTTOMRIGHT_TILE = 9;
    public static final int TYPE_AISLE_BOTTOMLEFT_TILE = 10;
    public static final int TYPE_AISLE_TOPLEFT_TILE = 11;
    public static final int TYPE_AISLE_TOPRIGHT_TILE = 12;
    public static final int TYPE_FULL_TILE = 13;
    public static final int TYPE_TWO_TONE1_TILE = 14;
    public static final int TYPE_TWO_TONE2_TILE = 15;
    public static final int TYPE_BLANK = 16;
    
    public static final int TYPE_COIN = 17;
    public static final int TYPE_GOAL = 18;
    public static final int TYPE_ENEMY_SLIME = 19;
    
    // playable character
    public static final int TYPE_CHARA_PRIST = 20;
    
    private static final String mapImageFiles[] = {
    	// tiles (17 types) 0~16
    	"file:src/application/png/tiles/walkable_tile_1.png",
        "file:src/application/png/tiles/top_tile_1.png",
        "file:src/application/png/tiles/right_tile_2.png",
        "file:src/application/png/tiles/bottom_tile_1.png",
        "file:src/application/png/tiles/left_tile_2.png",
        "file:src/application/png/tiles/left_tile_1.png",
        "file:src/application/png/tiles/right_tile_1.png",
        "file:src/application/png/tiles/bottomright_tile.png",
        "file:src/application/png/tiles/bottomleft_tile.png",
        "file:src/application/png/tiles/aisle_bottomright_tile.png",
        "file:src/application/png/tiles/aisle_bottomleft_tile.png",
        "file:src/application/png/tiles/aisle_topleft_tile.png",
        "file:src/application/png/tiles/aisle_topright_tile.png",
        "file:src/application/png/tiles/full_tile.png",
        "file:src/application/png/tiles/two_tone_tile_1.png",
        "file:src/application/png/tiles/two_tone_tile_2.png",
        "file:src/application/png/tiles/blank.png",
        // items
        
        "file:src/application/png/items/COIN.gif",
        "file:src/application/png/tiles/GOAL.png",
        "file:src/application/png/enemies/SLIME.gif",
        "file:src/application/png/chara/prist.gif",
        "file:src/application/png/tiles/walkable_tile_1.png"
    };
    
    private int DIFFICULTY = 3;
    private int mainChara = TYPE_CHARA_PRIST;
    private final int numOfImage = mapImageFiles.length;
    
    // Map size =  52 * 36, editable part size = 42 * 30 ,View = 13 * 9
    // Width and height must be "odd"
    private final static int MAP_WIDTH = 52;
    private final static int MAP_HEIGHT = 36; 
    private final static int MAP_EDITABLE_WIDTH = 42;
    private final static int MAP_EDITABLE_HEIGHT = 30; 
    private final static int VIEW_WIDTH  = 13;
    private final static int VIEW_HEIGHT = 9;
    private static int chara_x, chara_y;
    private static int[][] maps = new int[MAP_HEIGHT][MAP_WIDTH]; 
    
    private Image[] mapImages = new Image[numOfImage];
    private static ImageView[][] mapImageViews = new ImageView[VIEW_HEIGHT][VIEW_WIDTH];
   
    static GameData gameData = GameData.getInstance();
    private static MapData mapData = new MapData(gameData.getDifficulty(), gameData.getStage(), gameData.getMainChara());
    
    // Character will always be center
    // ImageView just show 13*9 of map based on character's location
    MapData(int DIFFICULTY, int currentStage, int chara){
    	this.DIFFICULTY = DIFFICULTY;
    	this.setMainChara(chara);
        
        for (int i=0; i<numOfImage; i++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }
        
        maps = new int[MAP_HEIGHT][MAP_WIDTH];
        
        readMap();       
        setObject(mainChara);        
        setObject(TYPE_COIN);
        setObject(TYPE_GOAL);
        setObject(TYPE_ENEMY_SLIME);
        setImageViews();
    }
    
    public static MapData getInstance() {
        return mapData;
    }

    public static void readMap() {
    	// read .csv file
    	final String extension = ".csv";
        BufferedReader br = null;
        
        try{
            br = Files.newBufferedReader(Paths.get("./src/application/map/B" + 
            										Integer.toString(gameData.getStage()) + "F" + extension));
            String line = "";
            int row = 0, i;
            
            while((line = br.readLine()) != null){
            	// separate by comma
                String token[] = line.split(",");
                
                for(i=0; i<MAP_WIDTH;i++) {
                	maps[row][i] = Integer.parseInt(token[i]);
                }
                
                row++;             
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(br != null){
                    br.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        
        printMap();
    	printCharaLocation();
    }

    public int getMap(int x, int y) {
        if (x < 0 || MAP_WIDTH <= x || y < 0 || MAP_HEIGHT <= y) {
            return -1;
        }
        return maps[y][x];
    }

    public void setMap(int x, int y, int type){
        if (x < 0 || MAP_WIDTH <= x || y < 0 || MAP_HEIGHT <= y) {
            return;
        }
        maps[y][x] = type;
    }
    
    // set view 13 * 9
    public static ImageView getImageView(int x, int y) {
        return mapImageViews[y][x];
    }

    public void setImageViews() {
        for (int y=chara_y-(VIEW_HEIGHT/2), index_y=0; y<chara_y + (VIEW_HEIGHT/2) + 1; y++, index_y++) {
            for (int x=chara_x-(VIEW_WIDTH/2), index_x=0; x<chara_x + (VIEW_WIDTH/2) + 1; x++, index_x++) {
                mapImageViews[index_y][index_x] = new ImageView(mapImages[getMap(x, y)]);
            }
        }
    }

    // get or set object's location   
    public static int[] getObject(int object) {    	
    	switch(object) {
    		case TYPE_CHARA_PRIST :
    			int[] chara_location = new int[2];
    	    	chara_location[0] = chara_x;
    	    	chara_location[1] = chara_y;
    	    	
    	    	return chara_location;
    	}
    	
    	return null;
    }
    
    public void setObject(int object) {
    	int x, y, i = 0;
    	
    	repeat:
    	while(true) {    	
	    	do { 
	    		// from editable part
		    	x = (int)(Math.random() * MAP_EDITABLE_WIDTH) + (VIEW_WIDTH/2);
		        y = (int)(Math.random() * MAP_EDITABLE_HEIGHT) + (VIEW_HEIGHT/2);
	    	} while (getMap(x, y) != TYPE_WALKABLE_TILE);
		        
	    	switch(object) {
	    		//TODO : case OTHER_CHARACTER :
	    		case TYPE_CHARA_PRIST :
	            	setMap(x, y, TYPE_CHARA_PRIST);
	            	chara_x = x;
	            	chara_y = y;
	            	
	            	printMap();
	            	printCharaLocation();
	            	break;
	            	
	    		case TYPE_GOAL :
	    			setMap(x, y, TYPE_GOAL);
	    			break; 
	
	    		case TYPE_COIN :
	    	        setMap(x, y, TYPE_COIN);  	
	    	        i++;
	    	        
	    			if(i>DIFFICULTY) break;
	    	        continue repeat;
	    	        
	    	    // TODO : add other enemies
	    		case TYPE_ENEMY_SLIME :
                	setMap(x, y, TYPE_ENEMY_SLIME);
	    	        i++;
	    	        
	    	        if(i>DIFFICULTY -3) break;
	    	        continue repeat;	    	      	    				    			    			
	    	}
	    	
	    	break; // for while(true)
    	}
    }
    
	public int getMainChara() {
		return mainChara;
	}

	public void setMainChara(int mainChara) {
		this.mainChara = mainChara;
	}
	
	public int getChara_x() {
		return chara_x;
	}
	
	public int getChara_y() {
		return chara_y;
	}
	
	public void setChara_x(int chara_x) {
		this.chara_x = chara_x;
	}
	
	public void setChara_y(int chara_y) {
		this.chara_y = chara_y;
	}
	
	public void addChara_x(int chara_x) {
		this.chara_x += chara_x;
	}
	
	public void addChara_y(int chara_y) {
		this.chara_y += chara_y;
	}
	
	// test method
	public static void printMap() {
		for(int a=0; a<MAP_HEIGHT; a++) {
        	for(int b=0; b<MAP_WIDTH; b++) {
        		System.out.printf("%3d", maps[a][b]);
        	}
        	System.out.print("\n");
        }
	}
	
	public static void printCharaLocation() {
		System.out.println("X :" + chara_x);
        System.out.println("Y :" + chara_y);
	}
}

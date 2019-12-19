package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData {
    public static final int TYPE_NONE = 0;
    public static final int TYPE_WALL = 1;
    public static final int TYPE_COIN = 2;
    public static final int TYPE_GOAL = 3;
    public static final int TYPE_ENEMY_SLIME = 4;
    
    private int DIFFICULTY = 3;
    
    private static final String mapImageFiles[] = {
        "file:src/application/png/SPACE.png",
        "file:src/application/png/WALL.png",
        "file:src/application/png/COIN.gif",
        "file:src/application/png/GOAL.png",
        "file:src/application/png/SLIME.gif"
    };
   
    private Image[] mapImages;
    private ImageView[][] mapImageViews;
    private int[][] maps;
    private int width;
    private int height;
    
    MapData(int x, int y, int DIFFICULTY){
    	this.DIFFICULTY = DIFFICULTY;
        mapImages     = new Image[5];
        mapImageViews = new ImageView[y][x];
        for (int i=0; i<5; i++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }

        width  = x;
        height = y;
        maps = new int[y][x];

        fillMap(MapData.TYPE_WALL);
        digMap(1, 3);
        setGoal();
        setCoin(DIFFICULTY);
        setEnemy(DIFFICULTY);
        setImageViews();
        printMap();
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return maps[y][x];
    }

    public ImageView getImageView(int x, int y) {
        return mapImageViews[y][x];
    }

    public void setMap(int x, int y, int type){
        if (x < 1 || width <= x-1 || y < 1 || height <= y-1) {
            return;
        }
        maps[y][x] = type;
    }

    public void setImageViews() {
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
            }
        }
    }

    public void fillMap(int type){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                maps[y][x] = type;
            }
        }
    }

    public void digMap(int x, int y){
        setMap(x, y, MapData.TYPE_NONE);
        int[][] dl = {{0,1},{0,-1},{-1,0},{1,0}};
        int[] tmp;

        for (int i=0; i<dl.length; i++) {
            int r = (int)(Math.random()*dl.length);
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        for (int i=0; i<dl.length; i++) {
            int dx = dl[i][0];
            int dy = dl[i][1];
            if (getMap(x+dx*2, y+dy*2) == MapData.TYPE_WALL){
                setMap(x+dx, y+dy, MapData.TYPE_NONE);
                digMap(x+dx*2, y+dy*2);

            }
        }
    }
    
    /* 松本 */
    public void setCoin(int DIFFICULTY){ 	
        int x,y;
        int i = 0;
        
        while(i < DIFFICULTY) {
            x = (int)(Math.random() * (width -2) + 1);
            y = (int)(Math.random() * (height-2) + 1);
            if (getMap(x, y) == MapData.TYPE_NONE){
                setMap(x, y, MapData.TYPE_COIN);
                i++;
            }
        }
    }
    
    public void setGoal(){
        setMap(19, 13, MapData.TYPE_GOAL);
    }
    
    public void setEnemy(int DIFFICULTY) {
    	int x,y;
        int i = 0;
        
        while(i < DIFFICULTY-3) {
            x = (int)(Math.random() * (width -2) + 1);
            y = (int)(Math.random() * (height-2) + 1);
            if (getMap(x, y) == MapData.TYPE_NONE){
                setMap(x, y, MapData.TYPE_ENEMY_SLIME);
                i++;
            }
        }
    }

    public void printMap(){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
            	 if (getMap(x,y) == MapData.TYPE_WALL) {
                     System.out.print("++");
                 } else if(getMap(x,y) == MapData.TYPE_COIN) {
                     System.out.print("CN");
                 } else if(getMap(x,y) == MapData.TYPE_GOAL) {
                     System.out.print("GL");
                 } else if(getMap(x,y) == MapData.TYPE_ENEMY_SLIME) {
                     System.out.print("EN");
                 } else {
                     System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
    }   
}

/*  added class by JEE  */
package application;

public class GameData {	
	public static String name;
	public static String difficulty;
    public static int DIFFICULTY = 3;
	
	public static boolean isAdmin = false;
	public static int stage = 1;
	public static int coin = 0;
	
    public void countCoin(){
        coin++;
    }
    
    public void countStage(){
        stage++;
    }

    public void resetCoin(){
        coin = 0;
    }
    
    public int getCoin(){
        return coin;
    }

    public int getStage(){
        return stage;
    }
	
	/*
	private String name;
	private String difficulty;
	
	GameData(String name, String difficulty){
		this.name = name;
		this.difficulty = difficulty;
	}
	
	GameData(){
		this.name = "NONE";
		this.difficulty = "NONE";
	}
	
	public void setName(String n) {name = n;}
	public void setDifficulty(String n) {this.difficulty = n;}
	
	public String getName() {return this.name;}
	public String getDifficulty() {return this.difficulty;}
	*/

}

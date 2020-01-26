/*  added class by JEE  */
package application;

public class GameData {	
	private static GameData gameData = new GameData();
	
	// playable character
    public static final int TYPE_CHARA_PRIST = 20;

	// default
	private static String name = "NONE";
	private static boolean isAdmin = false;
	private static int difficulty = 3;
	private static int mainChara = TYPE_CHARA_PRIST;
	private static int stage = 1;
	private static int coin = 0;
	
	GameData(){
	}
	
	public static GameData getInstance() {
        return gameData;
    }
	
    public void addCoin() {coin++;} 
    public void addStage(){stage++;}

    public void resetCoin(){coin = 0;}
    
    // getters and setters
	public void setName(String n)    {this.name = n;}
	public void setIsAdmin(boolean n){this.isAdmin = n;}
	public void setDifficulty(int n) {this.difficulty = n;}
	public void setMainChara(int n)  {this.mainChara = n;}
	public void setStage(int n)      {this.stage = n;}
	public void setCoin(int n)       {this.coin = n;}
	
	public static String getName()    {return name;}
	public static boolean getIsAdmin(){return isAdmin;}
	public static int getDifficulty() {return difficulty;}
	public static int getMainChara()  {return mainChara;}
	public static int getStage()      {return stage;}
	public static int getCoin()       {return coin;}



}

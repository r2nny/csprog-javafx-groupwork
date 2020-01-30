/*  added class by JEE  */
package application;

public class GameData {	
	private static GameData gameData = new GameData();
	
	// playable character
    public static final int TYPE_CHARA_PRIST = 18;

	// default
	private static String name = "NONE";
	private static boolean isAdmin = false;
	private static int difficulty = 3;
	private static int mainChara = TYPE_CHARA_PRIST;
	private static int stage = 1;
	private static int coin = 0;
	private static int key_gold = 0;
	private static int key_silver = 0;
	
	private static boolean isGameOver = false;
	private static boolean isGameClear = false;
	
	GameData(){
	}
	
	public static GameData getInstance() {
        return gameData;
    }
	
    public void addCoin() {coin++;} 
    public void addStage(){stage++;}
    public static void addKey_gold() {key_gold++;}
	public static void addKey_silver() {key_silver++;}
	public static void remKey_gold() {key_gold--;}
	public static void remKey_silver() {key_silver--;}

    public void resetCoin(){coin = 0;}
    
    // getters and setters
	public void setName(String n)    {this.name = n;}
	public void setIsAdmin(boolean n){this.isAdmin = n;}
	public void setDifficulty(int n) {this.difficulty = n;}
	public void setMainChara(int n)  {this.mainChara = n;}
	public void setStage(int n)      {this.stage = n;}
	public void setCoin(int n)       {this.coin = n;}
	
	public void setIsGameOver(boolean isGameOver) {this.isGameOver = isGameOver;}
	public void setIsGameClear(boolean isGameClear) {this.isGameClear = isGameClear;}
	
	public static String getName()    {return name;}
	public static boolean getIsAdmin(){return isAdmin;}
	public static int getDifficulty() {return difficulty;}
	public static int getMainChara()  {return mainChara;}
	public static int getStage()      {return stage;}
	public static int getCoin()       {return coin;}
	public static int getKey_gold() {return key_gold;}
	public static int getKey_silver() {return key_silver;}
	public boolean getIsGameOver() {return isGameOver;}
	public boolean getIsGameClear() {return isGameClear;}


}

package application;

public class Chara {
	private static Chara chara = new Chara("PRIST", 12);
	
	// playable character
    public static final int TYPE_CHARA_PRIST = 18;

	// default
	private String name;
	private int hp;
	
	Chara(){	
	}
	
	Chara(String name, int hp){
		this.name = name;
		this.hp = hp;		
	}
	
	public static Chara getInstance() {
        return chara;
    }
    
    // getters and setters
	public void setName(String n){this.name = n;}
	public String getName(){return name;}
	
	public void upHp() {this.hp++;}
	public void downHp() {this.hp--;}
	public int getHp(){return hp;}
}

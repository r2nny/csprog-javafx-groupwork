package application;

// not used

public class Enemy {	
	private String name;
	private int hp;
	
	Enemy(){
		
	}
	
	Enemy(String name, int hp){
		this.name = name;
		this.hp = hp;		
	}
    
	public void setName(String n){this.name = n;}
	public String getName(){return name;}
	
	public void upHp(int hp) {this.hp++;}
	public void downHp(int hp) {this.hp--;}
}

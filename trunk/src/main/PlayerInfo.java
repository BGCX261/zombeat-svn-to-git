package main;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import states.GameplayState;
import utils.FontLoader;
import utils.InGameMessage;

public class PlayerInfo {
	private static PlayerInfo _instance = null;
	public static int SCOREPOSX = 480;
	public static int SCOREPOSY = 0;
	public static int SCOREMAX = 7800;
	public static int SCOREMAXLEVEL[] = {0,0,1200,1200,1700,1200};
	private Color red = new Color(1.0f, 0.0f, 0.0f);
	HashMap<Integer, Boolean> accomplissement = new HashMap<Integer, Boolean>();
	
	public static PlayerInfo getCurrentPlayerInfo(){
		return _instance;
	}
 
	public static PlayerInfo createNewCurrentPlayerInfo(){
		_instance = new PlayerInfo();
		return getCurrentPlayerInfo();
	}
 
	private String name;
	private int score;
 
 
	private PlayerInfo(){
		name ="Zombies";
		score = 0;
	}
 
 
	public final String getName() {
		return name;
	}
 
	public final int getScore() {
		return score;
	}
 
 
	public final void setName(String name) {
		this.name = name;
	}
 
	public final void displayAndAddScore(InGameMessage.TEXTS text, int score){
		GameplayState.messages.add(new InGameMessage(text, score));
	}
	
	public final void addScore(int score) {
		this.score += score;
	}
 
	public final void decreaseScore(int score) {
		this.score -= score;
	}

	public void setScore(int score) {
			this.score = score;
	}
	
	public boolean getAccomplissement(int levelNb){
    	return accomplissement.get(levelNb);
    }
	
    public void setAccomplissement(int levelNb, boolean complet){
    	accomplissement.put(levelNb, complet);
    }
	
	public void draw(Graphics g){
//		bckg.draw(SCOREPOSX, SCOREPOSY);
		FontLoader.font26.drawString(SCOREPOSX, SCOREPOSY, "Score: " + score, red);
//		g.drawString("Score: " + score, SCOREPOSX + 3, SCOREPOSY);
	}
}
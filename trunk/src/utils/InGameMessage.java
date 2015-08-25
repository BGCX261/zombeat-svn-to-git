package utils;

import main.PlayerInfo;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import states.ZombEatGame;
import tools.ResourceManager;

public class InGameMessage {
	public static enum TEXTS {BONUS_ANIMAL, BONUS_ENNEMY, BONUS_LEVEL};
	private int duration;
	
	private float scaleStep = 0.0001f;
	private float MessageScale = 1;
	private Image message = null;
	private static Image bonusAnimal = ResourceManager.getInstance().getResources().getImageId("texts").getSubImage(0, 0, 240, 69);
	private static Image bonusEnnemy = ResourceManager.getInstance().getResources().getImageId("texts").getSubImage(0, 69, 240, 69);
	private static Image bonusLevel = ResourceManager.getInstance().getResources().getImageId("texts").getSubImage(0, 138, 240, 69);
	private Position pos;
	//movements to the score pos
	private int dx;
	private int dy;
	private int score=0;


	public InGameMessage(TEXTS text, int score){
		this.score = score;
		switch (text){
			case BONUS_ANIMAL:
				message = bonusAnimal;
				duration = 1000;
				break;
				
			case BONUS_ENNEMY:
				message = bonusEnnemy;
				duration = 1000;
				break;
			
			case BONUS_LEVEL:
				message = bonusLevel;
				duration = 1000;
				break;
				
			default:
				break;
		}
		
		pos = new Position((ZombEatGame.SCREENWIDTH - message.getWidth())*0.5f, (ZombEatGame.SCREENHEIGHT - message.getHeight())*0.5f);
		dx = (int) ((PlayerInfo.SCOREPOSX-pos.getX())/10);
		dy = (int) ((PlayerInfo.SCOREPOSY-pos.getY())/10);
	}
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void update(int delta){
		if (duration>0) {
			duration -= delta;
			MessageScale += scaleStep * delta;
		}else if (MessageScale>0 && pos.getX()< PlayerInfo.SCOREPOSX && pos.getY() > PlayerInfo.SCOREPOSY){
			 MessageScale -= scaleStep * 50 * delta;
			 pos.addX(dx);
			 pos.addY(dy);
		} else if (MessageScale != 0){
			PlayerInfo.getCurrentPlayerInfo().addScore(score);
			MessageScale = 0;
		}
	}
	
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
    	if (MessageScale>0) message.draw(pos.getX(), pos.getY(), MessageScale);
    }

}

package main;

import tools.Level;
import utils.InGameMessage;


public class Bonus extends CollidableAnimation{
	
	private static int pauseDuration=2;
	private float speed = 0.5f;
	static private int points = 100; 

	public Bonus(Level level, int minx, int maxx, int y, String id, String classe){
		super(level,minx,maxx,y,id, classe);	
	}

	protected int getPauseDuration() {
		return pauseDuration;
	}

	protected float getSpeed() {
		return speed;
	}
	
	protected int getPoints(){
		return points;
	}
	
	@Override
	public void isKilledBy(Zombie z){
		super.isKilledBy(z);
		PlayerInfo.getCurrentPlayerInfo().displayAndAddScore(InGameMessage.TEXTS.BONUS_ANIMAL, 100);
		speed *= 0.6;
	}
}
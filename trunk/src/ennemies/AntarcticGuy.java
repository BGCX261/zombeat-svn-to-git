package ennemies;

import ammos.Balls;
import main.Ennemy;
import main.Zombie;
import tools.Level;

public class AntarcticGuy extends Ennemy {
	
	private float speed = 1.0f;
	private int pauseDuration=1;

	public AntarcticGuy(Level level, int minx, int maxx, int y, String id) {
		super(level, minx, maxx, y, id, "ANTARCTICGUY", new Balls(level, id + "_AMMO"));
		this.speed+=(Math.random()*0.5);
	}
	
	@Override
	public void kills(Zombie z){
		z.killedByExplosion();
	}

	@Override
	protected float getSpeed() {
		return (float) ((isKilled())? speed*0.4 : speed);
	}

	@Override
	protected int getPauseDuration() {
		return (isKilled())? pauseDuration*2 : pauseDuration;
	}
}

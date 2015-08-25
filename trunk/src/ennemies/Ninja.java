package ennemies;

import ammos.Saber;
import main.Ennemy;
import main.Zombie;
import tools.Level;

public class Ninja extends Ennemy {

	private float speed = 1.2f;
	private int pauseDuration=1;
	
	public Ninja(Level level, int minx, int maxx, int y, String id) {
		super(level, minx, maxx, y, id, "NINJA", new Saber(level, id + "_AMMO"));
		this.speed+=(Math.random()*0.5);
	}
	
	@Override
	public void kills(Zombie z){
		z.killedByDecapitation();
	}

	@Override
	protected float getSpeed() {
		return (float) ((isKilled())? speed*0.5 : speed);
	}

	@Override
	protected int getPauseDuration() {
		return (isKilled())? pauseDuration*2 : pauseDuration;
	}
}

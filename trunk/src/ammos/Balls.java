package ammos;

import org.newdawn.slick.Sound;

import main.Ammo;
import main.Zombie;
import tools.Level;

public class Balls extends Ammo {

	static private Sound mus = defineSound("data/music/explosionForte.ogg"); 
	
	public Balls(Level level, String idLeft) {
		super(level, idLeft, "ANTARCTICGUY_AMMO");
		
	}
	
	@Override
	public void kills(Zombie z){
		z.killedByExplosion();
	}
	
	public Sound getMusic(){
		return mus;
	}
	
}

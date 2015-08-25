package ammos;

import org.newdawn.slick.Sound;

import main.Ammo;
import main.Zombie;
import tools.Level;

public class FlameThrower extends Ammo {

	static private Sound mus = defineSound("data/music/lanceflamme.ogg"); 
	
	public FlameThrower(Level level, String idLeft) {
		super(level, idLeft, "FLAMEGUY_AMMO");
		
	}
	
	@Override
	public void kills(Zombie z){
		z.killedByFire();
	}
	
	public Sound getMusic(){
		return mus;
	}
	
}

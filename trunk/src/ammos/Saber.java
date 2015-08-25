package ammos;

import org.newdawn.slick.Sound;

import main.Ammo;
import main.Zombie;
import tools.Level;

public class Saber extends Ammo {

	static private Sound mus = defineSound("data/music/sabre.ogg"); 
	
	public Saber(Level level, String idLeft) {
		super(level, idLeft, "SABER_AMMO");
	}
	
	@Override
	public void kills(Zombie z){
		z.killedByDecapitation();
	}
	
	public Sound getMusic(){
		return mus;
	}
}

package objects;
import main.Zombie;
import org.newdawn.slick.geom.Polygon;
 
public class CityEscape extends CollidableObject {
	public CityEscape(int x, int y, int test[],String type) {
		super(new Polygon(new float[]{
				x+test[0], y+test[1],
				x+test[2], y+test[3],
				x+test[4], y+test[5],
				x+test[6], y+test[7],
        }), true);   
	}
 
	@Override
	public void effect(Zombie z) {
		z.inFinalPosition(true);
	}

	public void update(int delta) {
	}
}
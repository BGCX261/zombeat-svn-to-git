package objects;
import main.Zombie;
import org.newdawn.slick.geom.Polygon;

import states.GameplayState;

public class Champi extends CollidableObject {
	boolean used=false;
	int x;
	int y;
	
	public Champi(int x, int y, int test[],String type) {
		super(new Polygon(new float[]{
				x+test[0], y+test[1],
				x+test[2], y+test[3],
				x+test[4], y+test[5],
				x+test[6], y+test[7],
        }), true);   
		
		this.x=x;
		this.y=y;
	}
 
	@Override
	public void effect(Zombie z) {
		if (!used){
			z.getFriend().inverseGravity();
			used=true;
			GameplayState.getLevel().getMap().tmap.setTileId(x/16, y/16, 0, 10);
//			BlockMap.nonBlockingEntities.get(GameplayState.level).remove(this);
		}
		else return;
	}

	public void update(int delta) {
	}
	
//	@Override
//	public void draw(Graphics g) {
//		if(!used) g.draw(poly);
//		else{
//			System.out.println(x);
//			System.out.println(BlockMap.tmap.getTileId(x, y, 0));
//			BlockMap.tmap.setTileId(x, y, 0, 1);		
//		}
//	}
	
}
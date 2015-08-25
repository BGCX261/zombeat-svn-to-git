package objects;

import main.Zombie;
import org.newdawn.slick.geom.Polygon;


abstract public class CollidableObject {
	private boolean overlap = false;
	protected Polygon poly;
	
	public CollidableObject(Polygon area, boolean overlap){
		this.overlap=overlap;
		poly=area;
	}
	
	public void draw(org.newdawn.slick.Graphics g){
		g.draw(poly);
		return;	
	}
	
	public abstract void effect(Zombie z);
	
	public Polygon getPoly(){
		return poly;
	}

	/**
	 * 
	 * @return true if zombies can overlap that element
	 */
	public boolean isOverlapAllowed(){
		return overlap;
		
	}
	
	
	
}

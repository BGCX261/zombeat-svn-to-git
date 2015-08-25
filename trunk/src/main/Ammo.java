package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import tools.Level;
import utils.Position;

public abstract class Ammo {
	private Animation animAmmo;
	private Animation animAmmoLeft;
	private Animation animAmmoRight;
	private Polygon polyAmmo;
	private Position pos;
	private int widestRange=0;
	private boolean directionLeft=true;
//	private String id;
	
	public Ammo(Level level, String id, String classe){
		pos=new Position(1000, 1000);
		this.animAmmoLeft=level.getAnimation(classe + "_LEFT");
		this.animAmmoRight=level.getAnimation(classe + "_RIGHT");
		this.animAmmo = animAmmoLeft;
		this.animAmmo.setLooping(true);		
//		this.id=id;
		float[] points= {
				pos.getX(), pos.getY(), 
				pos.getX() + animAmmo.getWidth(), pos.getY(),
				pos.getX() + animAmmo.getWidth(), pos.getY() + animAmmo.getHeight(),
				pos.getX(), pos.getY() + animAmmo.getHeight()}; 
		
		this.polyAmmo = new Polygon(points);
		
		for (int i=0; i<animAmmo.getFrameCount(); i++){
			if (animAmmo.getImage(i).getWidth() > widestRange) widestRange=animAmmo.getImage(i).getWidth();
		}
	}
	
	public void draw(Graphics g) {
		if (directionLeft){
			this.animAmmo.draw((int)pos.getX()-animAmmo.getWidth(), (int)pos.getY());
		} else {
			this.animAmmo.draw((int)pos.getX(), (int)pos.getY());
		}
		
		if (!getMusic().playing()) getMusic().play();
	}
	
	public Polygon getPoly(){
		return polyAmmo;
	}
	
	abstract public Sound getMusic();
	
	public void kills(Zombie z){
		System.out.println("ammo");
	}
	
	public void setLocation(boolean left, Position pos){
		if (left){
			if (!animAmmo.equals(animAmmoLeft)) animAmmo = animAmmoLeft;
			this.pos = pos;
			polyAmmo.setX(pos.getX() - animAmmo.getWidth());
			polyAmmo.setY(pos.getY());
			directionLeft=true;
		}else{
			if (!animAmmo.equals(animAmmoRight)) animAmmo=animAmmoRight;
			this.pos = pos;
			polyAmmo.setX(pos.getX());
			polyAmmo.setY(pos.getY());
			directionLeft=false;
		}
	}
	
	public void update(){
		float[] points2={};
		
		if (directionLeft){
			float[] points= {
					pos.getX() - animAmmo.getWidth(), pos.getY(), 
					pos.getX(),  pos.getY(),
					pos.getX(),  pos.getY() + animAmmo.getHeight(),
					pos.getX() - animAmmo.getWidth(),  pos.getY() + animAmmo.getHeight()};
			points2=points;
		}else{
				float[] points= {
						pos.getX(), pos.getY(), 
						pos.getX() + animAmmo.getWidth(),  pos.getY(),
						pos.getX() + animAmmo.getWidth(),  pos.getY() + animAmmo.getHeight(),
						pos.getX(), pos.getY() + animAmmo.getHeight()};
				points2=points;
			}
		
		this.polyAmmo = new Polygon(points2);
	}
	
	
	/**
	 * get the widest area of impact of the ammo (depends on the ennemy pos)
	 * @return
	 */
	public Shape getWidesthRangeArea(){
		return (directionLeft)? new Rectangle(pos.getX() - widestRange, pos.getY(), widestRange, polyAmmo.getHeight()) : new Rectangle(pos.getX(), pos.getY(), widestRange, polyAmmo.getHeight());
	}
	
	public int getWidesthRange(){
		return widestRange;
	}
	
	protected final static Sound defineSound(String ref){
		Sound s = null;
		try {
			s = new Sound(ref);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		return s;
		
	}
	
}

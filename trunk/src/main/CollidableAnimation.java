package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import states.GameplayState;
import tools.Level;
import utils.Position;

public abstract class CollidableAnimation {
	protected Animation anim;
protected Animation animLeft;
	protected Animation animRight;
	protected Animation animStandingLeft;
	protected Animation animStandingRight;
	//	private String id;
	private String classe;
	//duration at which the animation change (in ms)
	private int duration=1000;
	protected boolean goLeft=true;
	private boolean killed=false;
	protected int maxx;
	protected int minx;
	//	//nb of animation changes the zombie stays still
//	private int pauseDuration;
	private int pauseTimeElapsed=0;
	protected Polygon poly;
Position pos;
	private int timeSpent=0;
	
	public CollidableAnimation (Level level, int minx, int maxx, int y, String id, String classe){
		pos=new Position(minx, y);
		this.minx=minx;
		this.maxx=maxx;
		this.animLeft = level.getAnimation(classe + "_LEFT");
		this.animRight = level.getAnimation(classe + "_RIGHT");
		this.animStandingLeft = level.getAnimation(classe + "_STANDING_LEFT");
		this.animStandingRight = level.getAnimation(classe + "_STANDING_RIGHT");
//		this.id=id;
		this.classe=classe;
		this.anim=animStandingLeft;

		float[] points= {
				minx, y, 
				minx + anim.getWidth(), y,
				minx + anim.getWidth(), y + anim.getHeight(),
				minx, y + anim.getHeight()}; 
		this.poly = new Polygon(points);
		this.poly.setLocation(pos.getX(), pos.getY());
	}
	
	public void draw(Graphics g) {
		if (!killed)	anim.draw((int)pos.getX(), (int)pos.getY());
		else if (anim.equals(GameplayState.getLevel().getAnimation("SMOKE"))) anim.draw((int)pos.getX(), (int)pos.getY(), (int)GameplayState.getLevel().getAnimation(classe + "_STANDING_LEFT").getWidth(), (int) GameplayState.getLevel().getAnimation(classe + "_STANDING_LEFT").getHeight());
		else anim.draw((int)pos.getX(), (int)pos.getY(), new Color(0, 233, 0));
	}
	
	abstract protected int getPauseDuration();
	
	
	protected abstract int getPoints();
	
	public Polygon getPoly(){
		return poly;
	}
	
	abstract protected float getSpeed();
	
	public boolean isKilled(){
		return killed;
	}
	
	public void isKilledBy(Zombie z){
		if (killed) return;
		Animation anim2 = GameplayState.getLevel().getAnimation("SMOKE");
		anim2.setLooping(false);
		anim2.restart();
		this.anim=anim2;
		System.out.println("Killed!");
		killed=true;
	}
	
	public void update(int delta){
		if (killed && anim.isStopped()) {
			//on arrive ici des fois?
			this.anim=GameplayState.getLevel().getAnimation(classe+"_STANDING_LEFT");
		}
		
		float[] points= {
				0, pos.getY(), 
				0 + anim.getWidth(), pos.getY(),
				0 + anim.getWidth(), pos.getY() + anim.getHeight(),
				0, pos.getY() + anim.getHeight()}; 
		
		this.poly = new Polygon(points);
		this.poly.setLocation(pos.getX(), pos.getY());
		
		
		//If we move
		if (minx != maxx){
			timeSpent+=delta;
			if (this.anim.getFrame()==this.anim.getFrameCount()-1 && timeSpent>=duration){
				timeSpent=0;
				double r = Math.random();
				if (goLeft){
					if(pos.getX()>minx) this.anim = (r>0.4)? this.animLeft : this.animStandingLeft;
					else{
						if (pauseTimeElapsed==getPauseDuration()) {this.anim=this.animRight; pauseTimeElapsed=0;}
						else {this.anim=this.animStandingLeft; pauseTimeElapsed++;}
					}
				}else{
					if(pos.getX()<maxx - this.anim.getWidth()) this.anim = (r>0.4)? this.animRight : this.animStandingRight;
					else{
						if (pauseTimeElapsed==getPauseDuration()) {this.anim=this.animLeft; pauseTimeElapsed=0;}
						else {this.anim=this.animStandingRight; pauseTimeElapsed++;}
					}
				}
			}
			
			if (this.anim.equals(this.animLeft)){
				goLeft=true;
				if (pos.getX()>minx) pos.substractX(getSpeed()); else this.anim=this.animStandingLeft;
			}
			
			if (this.anim.equals(this.animRight)){
				goLeft=false;
				if (pos.getX()<maxx - this.anim.getWidth()) pos.addX(getSpeed()); else this.anim=this.animStandingRight;
			}
		}
		
		if (killed) this.anim.setSpeed(0.6f);
	}
}

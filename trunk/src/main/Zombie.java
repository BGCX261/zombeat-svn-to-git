package main;
import objects.BlockMap;
import objects.CollidableObject;
import objects.Teleport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.StateBasedGame;

import states.GameplayState;
import states.ZombEatGame;
import tools.Level;
import tools.ResourceManager;
import utils.Position;



public class Zombie{
	static private Animation animDeathDecapitate;
	static private Animation animDeathFire;
	static private Animation animExplosion;
	public static BlockMap blockMap;
	public static final String LEFT_DIRECTION= "LEFT";
	public static final String RIGHT_DIRECTION= "RIGHT";
	public static final int ZOMBIE_HEIGHT= 45;
	public static final int ZOMBIE_SPEED = 50;
	public static final int ZOMBIE_WIDTH= 29;
	public static List<Zombie> zombies = new ArrayList<Zombie>(); 
	private float acceleration=0;
	private Animation animAvancerDos;
	private Animation animAvancerDroite;
	private Animation animAvancerGauche;
	private Animation animAvancerInvDroite;
	private Animation animAvancerInvGauche;
	private Animation animEating;
	private Color color;
	private String direction;
	float falling =0;
	float horizontalSpeed = 1.0f;
	private boolean inFinalPosition=false;
	private boolean isBlocked=false;
	private boolean isMoving=false;
	private boolean killed;
	private int left;
	private Level level = new Level(99);
	private boolean onIce=false;
	private Polygon poly;
	private Position pos;
	private int right;
	float verticalSpeed = 1.5f;
	private float vitesse=0;
	private Animation zombieAnim;
	private int zombieID;
	
	public Zombie(int leftCode, int rightCode, int zombieID) throws SlickException{
		try {
			ResourceManager.getInstance().loadResourcesIntoLevel(new FileInputStream("xml/resources.xml"), level);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.zombieID=zombieID;
		
		poly = new Polygon(new float[]{
				11,0,
//				8,13,
//				0,13,
//				0,26,
//				8,26,
				11,ZOMBIE_HEIGHT,
				23,ZOMBIE_HEIGHT,
				23,0
		});
		
		setDirection(RIGHT_DIRECTION);
		resetPosition();
		animAvancerGauche = level.getAnimation("AVATAR_WALKING_LEFT");
		animAvancerDroite = level.getAnimation("AVATAR_WALKING_RIGHT");
		animAvancerInvGauche = level.getAnimation("AVATAR_WALKING_INV_LEFT");
		animAvancerInvDroite = level.getAnimation("AVATAR_WALKING_INV_RIGHT");
		animAvancerDos = level.getAnimation("AVATAR_WALKING_BACK");
		animEating = level.getAnimation("AVATAR_EATING");
		animDeathFire = level.getAnimation("AVATAR_BURNING");
		animDeathDecapitate = level.getAnimation("AVATAR_DECAPITATE");
		animExplosion = level.getAnimation("AVATAR_EXPLOSION");
		
		animAvancerGauche.setLooping(false);
		animAvancerDroite.setLooping(false);
		animAvancerInvGauche.setLooping(false);
		animAvancerInvDroite.setLooping(false);
		animAvancerDos.setLooping(false);
		animEating.setLooping(false);
		animDeathFire.setLooping(false);
		animExplosion.setLooping(false);
		animDeathDecapitate.setLooping(false);
		
		this.zombieAnim=animAvancerDroite;
		this.zombieAnim.stop();
		
		
		this.right = rightCode;
		this.left = leftCode;
		if (zombieID==2) color = new Color(0.8f, 1.0f, 0.9f);
		else color = new Color(1.0f, 1.0f, 1.0f);
		
		zombies.add(this);
	}
	
	public void draw(Graphics g) {
		if (this.zombieAnim.getHeight()>ZOMBIE_HEIGHT) this.zombieAnim.draw((int)pos.getX(), (int)pos.getY()+ZOMBIE_HEIGHT - zombieAnim.getHeight());
		else this.zombieAnim.draw((int)pos.getX(), (int)pos.getY(), color);
	}
	
	public boolean entityCollisionWith() throws SlickException {
		BlockMap map = GameplayState.getLevel().getMap();
		for (CollidableObject c : map.getBlockingEntities()) {
			if (poly.intersects(c.getPoly())) {
				c.effect(this);
				return true;
			}        
		}
		
		for (int i = 0; i < map.getNonBlockingEntities().size(); i++) {
			CollidableObject entity1 = map.getNonBlockingEntities().get(i);
			if (poly.intersects(entity1.getPoly()) || poly.contains(entity1.getPoly())) {
				entity1.effect(this);
			}
		}
		
		for (Ennemy e :GameplayState.getLevel().getEnnemies()){
			if (poly.intersects(e.getPoly()) && !e.isKilled()) {
				isBlocked=true;
				zombieAnim = animEating;
				zombieAnim.restart();
				e.isKilledBy(this);
				continue;
			}
		}
		
		for (Bonus b: GameplayState.getLevel().getBonus()){
			if (poly.intersects(b.getPoly()) && !b.isKilled()){
				isBlocked=true;
				zombieAnim = animEating;
				zombieAnim.restart();
				b.isKilledBy(this);
			}
		}
		
		for (Teleport t: GameplayState.getLevel().getTeleport()){
			if (poly.intersects(t.getPoly())){
				t.effect(this);
			}
		}
		
		return false;
	}
	
	public Animation getActualAnim(){
		return zombieAnim;
	}
	
	public String getDirection() {
		return this.direction;
	}

	public Zombie getFriend() {
		for (int i =0; i<zombies.size(); ++i){
			if (zombies.get(i) != this) return zombies.get(i);
		}
		
		
		
		return null;
	}

	public Polygon getPoly(){
		return poly;
	}

	public void gravity() throws SlickException{
		pos.addY(verticalSpeed);
		poly.setY(pos.getY());
        
        if (entityCollisionWith()){
        	pos.substractY(verticalSpeed);
        	poly.setY(pos.getY());
        	verticalSpeed= (isGravityInversed()? -1.5f : 1.5f); 	
        }else{
        	verticalSpeed+= (isGravityInversed()? -0.5f : 0.5f);
        }
	}
	
//	public void goLeft(int delta) throws SlickException{
//		if(this.zombieAnim!=animAvancerGauche && !isGravityInversed()) {this.zombieAnim=animAvancerGauche;}
//		if(this.zombieAnim!=animAvancerInvGauche && isGravityInversed()) {this.zombieAnim=animAvancerInvGauche;}
//		if (this.zombieAnim.isStopped()) this.zombieAnim.restart();
//        pos.substractX(50 * horizontalSpeed * delta/1000f);
//        poly.setX(pos.getX());
//        if (entityCollisionWith() || pos.getX()<0){
//        	pos.addX(50 * horizontalSpeed * delta/1000f);
//            poly.setX(pos.getX());
//        }
//        setDirection(LEFT_DIRECTION);
//	}
//	
//	public void goRight(int delta) throws SlickException{
//		if(this.zombieAnim!=animAvancerDroite && !isGravityInversed()) {this.zombieAnim=animAvancerDroite;}
//		if(this.zombieAnim!=animAvancerInvDroite && isGravityInversed()) {this.zombieAnim=animAvancerInvDroite;}
//
//		vitesse+= acceleration*delta;
//		if (this.zombieAnim.isStopped()) this.zombieAnim.restart();
//		pos.addX(vitesse/1000f);
//		poly.setX(pos.getX());
//	    if (entityCollisionWith() || pos.getX()>640-ZOMBIE_WIDTH){
//	    	pos.substractX(50 * horizontalSpeed * delta/1000f);
//	    	poly.setX(pos.getX());
//	    }
//	    setDirection(RIGHT_DIRECTION);
//	}
	
	
	public void inFinalPosition(boolean inFinalPosition) {
		this.inFinalPosition=inFinalPosition;	
	}
	
	public void inverseGravity(){
		verticalSpeed= - verticalSpeed;
		if (killed) return;
		if(this.zombieAnim==animAvancerDroite){
			this.zombieAnim=animAvancerInvDroite;
		}else if(this.zombieAnim==animAvancerInvDroite){
			this.zombieAnim=animAvancerDroite;
		}else if(this.zombieAnim==animAvancerGauche){
			this.zombieAnim=animAvancerInvGauche;
		}else{
			this.zombieAnim=animAvancerGauche;
		}
	}
	
	public boolean isGameOver(){
		return (killed && this.zombieAnim.isStopped());
	}
	
	public boolean isGravityInversed(){
		return verticalSpeed<0? true : false;
	}
	
	public boolean isInFinalPosition() {
		return this.inFinalPosition;	
	}
	
	
	public boolean isKilled(){
		if (killed) return true;
		return false;
	}

	
 
	public boolean isVitesseNegative(){
		return (vitesse<0);
	}


	public void killedByDecapitation() {
		killed=true;
		isBlocked=true;
		this.zombieAnim=animDeathDecapitate;
		this.zombieAnim.restart();
	}
	
	public void killedByFire(){
		killed=true;
		isBlocked=true;
		this.zombieAnim=animDeathFire;
		this.zombieAnim.restart();
	}
	
	public void killedByExplosion(){
		killed=true;
		isBlocked=true;
		this.zombieAnim=animExplosion;
		this.zombieAnim.restart();
	}

	public void move(int delta) throws SlickException{
		if (getDirection().equals(RIGHT_DIRECTION)){
			if(this.zombieAnim!=animAvancerDroite && !isGravityInversed()) {this.zombieAnim=animAvancerDroite;}
			if(this.zombieAnim!=animAvancerInvDroite && isGravityInversed()) {this.zombieAnim=animAvancerInvDroite;}
			if (isMoving && this.zombieAnim.isStopped()) this.zombieAnim.restart();
			
			vitesse+= acceleration*delta;
			if (!onIce && isMoving) vitesse = ZOMBIE_SPEED*delta;
			pos.addX(vitesse/1000f);
			setPosition(pos);
		    if (entityCollisionWith()){
		    	pos.substractX(vitesse/1000f);
		    	setPosition(pos);
		    }
		    setDirection(RIGHT_DIRECTION);
		}
		
		if (getDirection().equals(LEFT_DIRECTION)){
			if(this.zombieAnim!=animAvancerGauche && !isGravityInversed()) {this.zombieAnim=animAvancerGauche;}
			if(this.zombieAnim!=animAvancerInvGauche && isGravityInversed()) {this.zombieAnim=animAvancerInvGauche;}
			if (isMoving && this.zombieAnim.isStopped()) this.zombieAnim.restart();
			
			vitesse+= acceleration*delta;
			if (!onIce && isMoving) vitesse = ZOMBIE_SPEED*delta;
	        pos.substractX(vitesse/1000f);
	        setPosition(pos);
	        if (entityCollisionWith()){
	        	pos.addX(vitesse/1000f);
	        	setPosition(pos);
	        }
	        setDirection(LEFT_DIRECTION);
		}
		
		if (pos.getX()>640-ZOMBIE_WIDTH){
			setPosition(new Position(640 - ZOMBIE_WIDTH, pos.getY()));
			vitesse=0;
		}
		if (pos.getX()<0){
			setPosition(new Position(0, pos.getY()));
			vitesse=0;
		}
	}
	
	public boolean outOfWindow(){
		if(this.pos.getY()<0 || this.pos.getY()>ZombEatGame.SCREENHEIGHT) return true;
		return false;
	}
	
	public void reset(){
		killed=false;
		isBlocked=false;
		vitesse=0;
		if (isGravityInversed()) inverseGravity();
		resetPosition();
		inFinalPosition(false);
		zombieAnim = animAvancerDroite;
		setDirection(RIGHT_DIRECTION);
	}
	
	public void resetPosition(){
		setPosition(GameplayState.getLevel().getInitialCoordinates(zombieID));
	}
	
	public void setAnimationDos(){
		zombieAnim = animAvancerDos;
		zombieAnim.restart();
	}
	
	public void setAnimationEating(){
		zombieAnim = animEating;
	}
	
	public void setDirection(String direction) {
		if (direction==LEFT_DIRECTION || direction==RIGHT_DIRECTION)
			this.direction = direction;
		
//		if (direction == LEFT_DIRECTION) poly.setLocation(poly.getX() + 10, poly.getY());
	}
	
	public void setOnIce(boolean isOnIce){
		this.onIce = isOnIce;
	}

	public void setPosition(Position pos){
		this.pos = pos;
		poly.setLocation(pos.getX()+11, pos.getY());
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta){
		if (isBlocked && !zombieAnim.isStopped()) {
			try {
				if (isGravityInversed()) {
					inverseGravity();
				}
				gravity();
			} catch (SlickException e) {
				e.printStackTrace();
			}
			return;
		}
		if (killed) return;
		isBlocked = false;
		try {
			acceleration=0;
			isMoving=false;
			if (!gc.getInput().isKeyDown(left) && !gc.getInput().isKeyDown(right) && vitesse!=0){
				vitesse = (vitesse>0)? vitesse-50 : vitesse+50;
				if (vitesse<50 && vitesse>-50) vitesse=0;
			}
			
			if (gc.getInput().isKeyDown(left)) {
				acceleration=2;
				if (getDirection().equals(RIGHT_DIRECTION)) vitesse *= -1;
				setDirection(LEFT_DIRECTION);
				isMoving = true;
			} else if (gc.getInput().isKeyDown(right)) {
				acceleration=2;
				if (getDirection().equals(LEFT_DIRECTION)) vitesse *= -1;
				setDirection(RIGHT_DIRECTION);
				isMoving = true;
			}
			//Cas ou c'est l'ennemi qui vient tuer le zombie
			entityCollisionWith();
			this.move(delta);
			this.gravity();
		} catch (SlickException e) {
			e.printStackTrace();
		}
        
		
	}
	
}

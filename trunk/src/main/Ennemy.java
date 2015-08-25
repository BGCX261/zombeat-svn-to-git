package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import tools.Level;
import utils.InGameMessage;
import utils.Position;

public abstract class Ennemy extends CollidableAnimation{
	private Ammo ammo;
	/**
	 * Area in which an ennemy can kill a zombie
	 */
	private Polygon attackArea;
	private Zombie hunted = null;
	private Animation animAttackLeft;
	private Animation animAttackRight;
	static private int points = 500; 
	
	public Ennemy(Level level, int minx, int maxx, int y, String id, String classe, Ammo ammo){
		super(level,minx,maxx,y,id, classe);
		this.ammo= ammo;
		ammo.setLocation(goLeft, new Position (pos.getX(), pos.getY()));
		//on rajoute anim.getWidth() car l'ennemi est dessiné depuis son coin supérieur gauche et dépasse donc du cadre sinon
		float[] attackPoints= { 0, 0,(maxx-minx) + 2*ammo.getWidesthRange(), 0, (maxx-minx) + 2*ammo.getWidesthRange(), anim.getHeight(), 0, anim.getHeight()}; 
		this.attackArea= new Polygon(attackPoints);
		this.attackArea.setLocation(pos.getX() - ammo.getWidesthRange(), pos.getY());
		this.animAttackLeft = level.getAnimation(classe + "_ATTACK_LEFT");
		this.animAttackRight = level.getAnimation(classe + "_ATTACK_RIGHT");
	}
	
	public void draw(Graphics g) {
		super.draw(g);
//		if ((this.anim.equals(animAttackLeft) || this.anim.equals(animAttackRight)) && hunted!=null && !hunted.isKilled()){
//			Remplacer par cette ligne pour continuer le feu de l'arme qd le zombie est mort			
		if (this.anim.equals(animAttackLeft) || this.anim.equals(animAttackRight) && hunted!=null && !hunted.isKilled()) {
			ammo.draw(g);
		}
	}
	
	public Ammo getAmmo(){
		return this.ammo;
	}
	
	abstract public void kills(Zombie z);
	
	@Override
	public void isKilledBy(Zombie z){
		super.isKilledBy(z);
		this.ammo.setLocation(goLeft, new Position(1000, 0));
		PlayerInfo.getCurrentPlayerInfo().displayAndAddScore(InGameMessage.TEXTS.BONUS_ENNEMY, 500);
	}
	
	public void update(int delta){
		if (isKilled()) {
			super.update(delta);
			return;
		}
		
		//on check si on a un zombie dans la zone d'attaque
		hunted=zombieInArea();
		int oldHeight=0;
		//S'il n'y en pas, on fait un update normal
		if (hunted==null || hunted.isGameOver()) {
			if (hunted != null && hunted.isGameOver()) {
				System.out.println("ici");
				oldHeight=this.anim.getHeight();
				this.anim=animStandingLeft;
				pos.setY(pos.getY() + oldHeight - this.anim.getHeight());
			}
			super.update(delta);
			if (goLeft) ammo.setLocation(goLeft, pos);
			else ammo.setLocation(goLeft, new Position(pos.getX()+ this.animRight.getWidth(), pos.getY()));
		} else{
			//Si le zombie est dans le rayon d'attaque de l'arme, on bouge plus et on attaque
			if (hunted.getPoly().intersects(ammo.getWidesthRangeArea()) || ammo.getWidesthRangeArea().contains(hunted.getPoly())){
				if ((hunted.getPoly().intersects(ammo.getPoly()) || ammo.getPoly().contains(hunted.getPoly())) && !hunted.isKilled()) this.kills(hunted);
				
				if (goLeft){
					if (!this.anim.equals(animAttackLeft)) {
						oldHeight=this.anim.getHeight();
						this.anim=animAttackLeft;
						pos.setY(pos.getY() + oldHeight - this.anim.getHeight());
					}
					this.ammo.setLocation(goLeft, new Position(pos.getX(), pos.getY())); 
				} else {
					if (this.anim!=animAttackRight) {
						oldHeight=this.anim.getHeight();
						this.anim=animAttackRight;
						pos.setY(pos.getY() + oldHeight - this.anim.getHeight());
					}
					this.ammo.setLocation(goLeft, new Position(pos.getX()+ this.animRight.getWidth(), pos.getY()));
				}
			
				this.ammo.update();
			//Sinon on se dirige vers l'ennemi	
			} else {
				//on détermine la position (droite/gauche de l'ennemi)
				goLeft = (hunted.getPoly().getX() < this.poly.getX());
				
				if (goLeft){
					if(pos.getX()>minx) {
						if (this.anim!=animLeft || (this.anim==animLeft && this.anim.isStopped())) {
							oldHeight=this.anim.getHeight();
							this.anim = animLeft;
							pos.setY(pos.getY() + oldHeight - this.anim.getHeight());
						}
						
						pos.substractX(1);
						this.ammo.setLocation(goLeft, new Position(pos.getX(), pos.getY()));
					}else{
						oldHeight=this.anim.getHeight();
						this.anim = this.animStandingLeft;
						pos.setY(pos.getY() + oldHeight - this.anim.getHeight());
					}
				}else{
					if(pos.getX()<maxx - this.anim.getWidth()) {
						if (this.anim!=animRight || (this.anim==animRight && this.anim.isStopped())) {
							oldHeight=this.anim.getHeight();
							this.anim = animRight;
							this.anim.restart();
							pos.setY(pos.getY() + oldHeight - this.anim.getHeight());
						}
						
						pos.addX(1);
						this.ammo.setLocation(goLeft, new Position(pos.getX() + this.animRight.getWidth(), pos.getY()));
					}else{
						oldHeight=this.anim.getHeight();
						this.anim = this.animStandingRight;
						pos.setY(pos.getY() + oldHeight - this.anim.getHeight());
					}
				}
			}
			
			float[] points= {
					0, pos.getY(), 
					0 + anim.getWidth(), pos.getY(),
					0 + anim.getWidth(), pos.getY() + anim.getHeight(),
					0, pos.getY() + anim.getHeight()}; 
			
			this.poly = new Polygon(points);
			this.poly.setLocation(pos.getX(), pos.getY());
		}
	}
	
	
	private Zombie zombieInArea(){
		for (Zombie z : Zombie.zombies){
			if ((attackArea.intersects(z.getPoly()) || attackArea.contains(z.getPoly())) && ((z.getPoly().getX() < pos.getX()) == goLeft)){
				return z;
			}
		}
		return null;
	}
	
	
	abstract protected float getSpeed();
	
	protected int getPoints(){
		return points;
	}
	
}

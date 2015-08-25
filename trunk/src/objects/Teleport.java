package objects;


import main.Zombie;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

import tools.Level;
import utils.Position;

public class Teleport extends CollidableObject{
	static int choixCol=0;
	static Color col[] = {new Color(255,255,255), new Color(0,255,0), new Color(0,0,255), new Color(0,120,40)};
	static float square[] = {1,1,16,1,16,60,1,60};
	Color color;
	String id;
	Image img;
	int level;
	int x;
	int xtogo;
	int y;
	int ytogo; 
	
	public Teleport(Level level, String id, int x, int y, int xtogo, int ytogo){
		super(new Polygon(new float[]{
				x+square[0], y+square[1],
				x+square[2], y+square[3],
				x+square[4], y+square[5],
				x+square[6], y+square[7],
        }), true);  
		this.x=x;
		this.y=y;
		this.xtogo=xtogo;
		this.ytogo=ytogo;
		this.level=level.getNumber();
		this.img=level.getImageId("TELEPORT");
		if (choixCol <2){
			color=col[0];
		}else if (choixCol <4){
			color=col[1];
		}else if(choixCol <6){
			color=col[2];
		}else if(choixCol <8){
			color=col[3];
		}else{
			color=col[0];
			choixCol=0;
		}
		choixCol++;
		
	}
	
	
	@Override
	public void draw(org.newdawn.slick.Graphics g) {
		img.draw((int)x, (int)y,color);
	}

	@Override
	public void effect(Zombie z) {
		if((z.getDirection() == Zombie.RIGHT_DIRECTION && !z.isVitesseNegative()) || (z.getDirection() == Zombie.LEFT_DIRECTION && z.isVitesseNegative()))	z.setPosition(new Position(xtogo+square[2]+1, ytogo));
		else z.setPosition(new Position(xtogo-Zombie.ZOMBIE_WIDTH, ytogo));
	}
	
	public static void initializeColor(){
		choixCol=0;
	}
}

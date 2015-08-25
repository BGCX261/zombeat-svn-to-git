package objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utils.Position;

public class MovingPicture extends Image{
	private Position current;
	private Position mouvement; // Vecteur déplacement
	private Position end;
	private int time; // Temps pour accomplir l'annimation
	
	public MovingPicture(int x, int y, int to_x, int to_y, int time, String path) throws SlickException{
		super(path);
		this.time = time;
		this.current = new Position(x, y);
		// TODO virer le 60
		this.mouvement = new Position((to_x - x) / (60.0 * this.time), (to_y - y) / (60.0 * this.time)); // TODO changer pour utiliser le delta depuis la dernière mise à jour de update
//		System.out.println("mouvement:\n\tx:" + mouvement.getX() + "\n\ty:" + mouvement.getY());
		this.end = new Position(to_x, to_y);
	}
	
	public void update(){
		if (current.getX() < (end.getX() - 1) || current.getX() > (end.getX() + 1))
			current.setX(current.getX() + mouvement.getX());
		if (current.getY() < (end.getY() - 1) || current.getY() > (end.getY() + 1))
			current.setY(current.getY() + mouvement.getY());
	}

	public void dessiner(){
		this.draw((int)current.getX(), (int)current.getY());
	}
}

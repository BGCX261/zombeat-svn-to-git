package utils;

public class Position {
	private float x;
	private float y;
	
	public Position(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Position(double x, double y) {
		this.x = (float) x;
		this.y = (float) y;
	}

	public void setXY(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void addX (float x) {
		this.x+=x;
	}
	
	public void substractX (float x){
		this.x-=x;
	}
	
	public void addY (float y){
		this.y +=y;
	}
	
	public void substractY (float y){
		this.y-=y;
	}
}

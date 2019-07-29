package tank;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Bullets extends Sprite {
	private double speed;
	private double damage;
	private double bulletMaxX;
	private double bulletMinX;
	private double bulletMinY;
	private double bulletMaxY;
	
	public Bullets(Pane layer, Image image, double x, double y, double dx, double dy, double speed, double damage, double r, double changeR, String player) {
		super(layer, image, x, y, dx, dy, player, r, changeR);
		this.speed = speed;
		this.damage = damage;
		createBounds();
	}
	
	private void createBounds() {
		this.bulletMinX = 0 - this.getWidth();
		this.bulletMaxX = Settings.SCENE_WIDTH + this.getWidth();
		this.bulletMinY = 0 - this.getHeight();
		this.bulletMaxY = Settings.SCENE_HEIGHT + this.getHeight();
	}
	
	@Override
	public void move() {
		if (getR() == 0) {
			setX(speed + getX());
		}
		else if (getR() <= 0){
			setX((speed + getX()));
	        setR(getR());
	        setY((-speed / 4 + getY()));
		}
		else {
			setX((speed + getX()));
	        setR(getR());
	        setY((speed / 4 + getY()));
		}
        
    }
	
	public double getDamage() {
		return damage;
	}
	public double getSpeed() {
		return speed;
	}
	public double getTimeTo(double tx) {   // tank x
		double by = this.getY();           // bullet y
		double bx = this.getX();           // bullet x
		double dy = this.getChangeY();
		int i;
		for(i = 0; bx + (i * speed) < tx; i++)
			by += dy;
		return i;
	}
	public double getYFromProjectile(double tx, double ty) {   // tank x,y
		double by = this.getY();           // bullet y
		double bx = this.getX();           // bullet x
		double dy = this.getChangeY();
		int i;
		for(i = 0; bx + (i * speed) < tx; i++)
			by += dy;
		return by - ty;
	}
	public boolean outOfBounds() {
		double buffer = 0.0;
        if (Double.compare(getX(), bulletMinX) < buffer) {
            return true;
        } 
        else if (Double.compare(getX(), bulletMaxX) > buffer) {
            return true;
        }
        
        if (Double.compare(getY(), bulletMinY) < buffer) {
            return true;
        }
        else if (Double.compare(getY(), bulletMaxY) > buffer) {
            return true;
        }
        return false;
    }    
}
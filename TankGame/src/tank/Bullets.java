package tank;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
/**
 * 
 * @author Joshua
 *
 */
public class Bullets extends Sprite {
	private double speed;
	private double damage;
	private boolean isRemovable;
	
	public Bullets(Pane layer, Image image, double x, double y, double dx, double dy, double speed, double damage, double r, double changeR, String player, boolean isRemovable) {
		super(layer, image, x, y, dx, dy, player, r, changeR);
		this.speed = speed;
		this.damage = damage;
		this.isRemovable = isRemovable;
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
	
	public boolean getIsRemovable() {
		return isRemovable;
	}
	
	public void setIsRemovable(boolean isRemovable) {
		this.isRemovable = isRemovable;
	}
}
	
package tank;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
/**
 * 
 * @author Joshua
 *
 */
public class Sprite {
    private Image image;
    private ImageView imageView;
    private Pane layer;
    private String player;

    private double x;
    private double y;
    private double r;

    private double changeX;
    private double changeY;
    private double changeR;


    private double width;
    private double height;

    public Sprite(Pane layer, Image image, double x, double y, double changeX, double changeY, String player, double r, double changeR) {
        this.layer = layer;
        this.image = image;
        this.x = x;
        this.y = y;
        this.r = r;
        this.changeX = changeX;
        this.changeY = changeY;
        this.changeR = changeR;

        this.player = player;

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);
        this.imageView.setRotate(r);

        this.width = image.getWidth();
        this.height = image.getHeight();

        addToLayer();
    }

    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }
    
    //used to remove sprite
    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public Pane getLayer() {
        return layer;
    }

    public void setLayer(Pane layer) {
        this.layer = layer;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getChangeX() {
        return changeX;
    }

    public void setChangeX(double changeX) {
        this.changeX = changeX;
    }

    public double getChangeY() {
        return changeY;
    }

    public void setChangeY(double changeY) {
        this.changeY = changeY;
    }
    
    public void setR(double r) {
    	this.r = r;
    }
    
    public double getR() {
    	return r;
    }
    
    public void setChangeR(double changeR) {
    	this.changeR = changeR;
    }
    
    public double getChangeR() {
    	return changeR;
    }

    public void move() {
        setX(getChangeX() + getX());
        setY(getChangeY() + getY());
        setR(getChangeR() + getR());
    }
    
    public ImageView getView() {
        return imageView;
    }

    public void updateUI() {
        imageView.relocate(getX(), getY());
        imageView.setRotate(getR());
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getPlayer() {
		return player;
	}
	
	public boolean hasCollided(Sprite sprite) {
		if (sprite.getPlayer().equals("player1"))
			return (sprite.getX() + (sprite.getWidth() - 50) >= this.getX() && sprite.getY() + sprite.getHeight() >= getY() && sprite.getX() - 50 <= this.getX() + this.getWidth() && sprite.getY() <= this.getY() + this.getHeight());
		else 
			return (sprite.getX() + (sprite.getWidth() + 50) >= this.getX() && sprite.getY() + sprite.getHeight() >= getY() && sprite.getX() + 50 <= this.getX() + this.getWidth() && sprite.getY() <= this.getY() + this.getHeight());
	}
}
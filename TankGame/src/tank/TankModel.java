package tank;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
/**
 * 
 * @author Joshua
 *
 */
public class TankModel extends Sprite {

    private double tankMinX;
    private double tankMaxX;
    private double tankMinY;
    private double tankMaxY;
    private double tankMaxUpRotation;
    private double tankMinDownRotation;

    private P1Controls c1;
    private P2Controls c2;

    private double speed;
    private double health;

    public TankModel(Pane playerlayer, Image image, double x, double y, double  changeX, double changeY, double health, double speed, P1Controls c1, P2Controls c2, String player, double r, double changeR) {
        super(playerlayer, image, x, y, changeX, changeY, player, r, changeR);
        this.health = health;
        this.speed = speed;
        this.c1 = c1;
        this.c2 = c2; 
        

        createBounds();
    }
    
    private void createBounds() {
        // calculate movement bounds of the player tank
        // allow half of the tank to be outside of the screen 
    	// player1 can travel from left to middle
    	if (getPlayer() == "player1") {
    		tankMinX = 0 - this.getImage().getWidth() / 2.00;
            tankMaxX = (Settings.SCENE_WIDTH / 2.0) - (this.getImage().getWidth() / 2.00) - 50.0;
            tankMinY = Settings.SCENE_HEIGHT * .73;
        	tankMaxY = Settings.SCENE_HEIGHT - (this.getImage().getHeight() / 2);
        	tankMaxUpRotation = -15;
        	tankMinDownRotation = 15;
    	}
    	// player2 can travel from right to middle
    	else {
    		tankMinX = (Settings.SCENE_WIDTH / 2.0) - (this.getImage().getWidth() / 2.00 - 125);
            tankMaxX = Settings.SCENE_WIDTH - (this.getImage().getWidth() / 2.00);
            tankMinY = Settings.SCENE_HEIGHT * .63;
            tankMaxY = Settings.SCENE_HEIGHT - (this.getImage().getHeight() / 2);
            tankMaxUpRotation = 15;
        	tankMinDownRotation = -15;
    	}
    	
    
    }

    public void processInput() {
    	if (getPlayer().equals("player1")) {
        // horizontal direction for player 1
    		if (c1.isMoveLeft()) {
    			setChangeX(-speed);
        	} 
    		else if (c1.isMoveRight()) {
        		setChangeX(speed);
        	}  
    		else if (c1.isMoveUp()) {
        		setChangeY(-(speed / 2));
        	}
    		else if (c1.isMoveDown()) {
        		setChangeY(speed / 2);
        	}
    		else if (c1.isRotateUp()) {
    			setChangeR(-2.5);
    		}
    		else if (c1.isRotateDown()) {
    			setChangeR(2.5);
    		}
    		else {
        		setChangeX(0.0);
        		setChangeY(0.0);
        		setChangeR(0.0);
        	}
    	} 
    	else if (getPlayer().equals("player2")){
    	// horizontal direction for player 2
    		if (c2.isMoveLeft()) {
    			setChangeX(-speed);
    		}
    		else if (c2.isMoveRight()) {
    			setChangeX(speed);
    		}
    		else if (c2.isMoveUp()) {
        		setChangeY(-(speed / 2));
        	}
    		else if (c2.isMoveDown()) {
        		setChangeY(speed / 2);
        	}
    		else if (c2.isRotateUp()) {
    			setChangeR(2.5);
    		}
    		else if (c2.isRotateDown()) {
    			setChangeR(-2.5);
    		}
    		else {
    			setChangeX(0.0);
    			setChangeY(0.0);
    			setChangeR(0.0);
    		}
    	}    			
    }
    
    @Override
    public void move() {
        super.move();
        // tank can't move outside of bounds set in init()
        checkBounds();
    }

    public void checkBounds() {  
        if (Double.compare(getX(), tankMinX) < 0.00) {
            setX(tankMinX);
        } 
        else if (Double.compare(getX(), tankMaxX) > 0.00) {
            setX(tankMaxX);
        }
        
        if (Double.compare(getY(), tankMinY) < 0.00) {
        	setY(tankMinY);
        }
        else if (Double.compare(getY(), tankMaxY) > 0.00) {
        	setY(tankMaxY);
        }
        
        if (Double.compare(getR(), tankMaxUpRotation) > 0.00 && this.getPlayer().equals("player2")) {
        	setR(tankMaxUpRotation);
        }
        else if (Double.compare(getR(), tankMinDownRotation) < 0.00 && this.getPlayer().equals("player2")) {
        	setR(tankMinDownRotation);
        }
        
        if (Double.compare(getR(), tankMaxUpRotation) < 0.00 && this.getPlayer().equals("player1")) {
        	setR(tankMaxUpRotation);
        }
        else if (Double.compare(getR(), tankMinDownRotation) > 0.00 && this.getPlayer().equals("player1")) {
        	setR(tankMinDownRotation);
        }
    }    
    
    public P1Controls getP1Controls() {
		return c1;
	}
	
	public P2Controls getP2Controls() {
		return c2;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
}
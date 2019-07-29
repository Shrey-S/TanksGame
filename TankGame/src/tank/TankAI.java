package tank;

import java.util.List;
import java.io.*;
import java.util.Random;
/**
 * @author Shrey
 */
public class TankAI {
	private double playerX;
    private double tankX;
    private double playerY;
    private double tankY;
    private double playerRotation;
    private double tankRotation;
    private boolean moveUp;
    private boolean moveDown;
    private boolean moveRight;
    private boolean moveLeft;
    private boolean rotateUp;
    private boolean rotateDown;
    private boolean isFiring;
    private int deadpbullets;
    private int deadtbullets;
    private int lastpbullets;
    private int lasttbullets;
    private int xMove;
    private int deadpbulletscounter;
    private int deadtbulletscounter;
    
	List<Bullets> pbullets; 
    List<Bullets> tbullets; 
    BulletMemory bmemory;
    CollisionMemory cmemory;
    public TankAI() 
    {
    	deadpbullets = 0;
    	deadtbullets = 0;
    	xMove = 0;
    	bmemory = new BulletMemory();
	    try
	    {
	    	cmemory = new CollisionMemory();
	    }
	    catch(IOException ex)
	    {
	        System.out.println (ex.toString());
	        System.out.println("Could not instantiate cmemory");
	    }
    }
    public void giveInfo(double px, double tx, double py, double ty, double pr, double tr, List<Bullets> pb, List<Bullets> tb) 
    {
    	this.playerX = px;
    	this.tankX = tx;
    	this.playerY = py;
    	this.tankY = ty;
    	this.playerRotation = pr;
    	this.tankRotation = tr;
    	this.pbullets = pb;
    	this.tbullets = tb;
    	savePBullets();
    	saveTBullets();
    	bmemory.update();
    }
    public void savePBullets() 
    {
    	int size = pbullets.size();
    	if (lastpbullets < size) 
    	{
    		bmemory.addPBullets(size - lastpbullets);
    		lastpbullets = size;
    	}
    }
    public void saveTBullets() 
    {
    	int size = tbullets.size();
    	if (lasttbullets < size) 
    	{
    		bmemory.addTBullets(size - lasttbullets);
    		lasttbullets = size;
    	}
    }
    public void calculateMove() 
    {
    	bmemory.update(); //System.out.println("Calculating");
    	moveUp = false;
        moveDown = false;
        moveRight = false;
        moveLeft = false;
        rotateUp = false;
        rotateDown = false;
        isFiring = false;
    	if (pbullets.size() == 0) 
    	{    // no incoming bullets, move and shoot at player
    		Random rand = new Random();
    		int val = rand.nextInt(30);
    		if (playerY + val < tankY) 
    		{
    			moveUp = true;
    		}
    		if (playerY - val > tankY) 
    		{
    			moveDown = true;
    		}
    		if (playerY < tankY + 5 && playerY > tankY - 5)
    		{
    			isFiring = true;
    		}
    	} else {
    		int incomingBullets = bmemory.getPBullets();
    		int offset = pbullets.size() - bmemory.getPBullets();
    		double bulletETA;
    		double yFromProj;
    		double xFromBullet;
    		int space = 50;     // minimum space between tank and incoming bullets to not move
    		for (int i = 0; i < incomingBullets; i++) 
    		{
    			bulletETA = pbullets.get(offset + i).getTimeTo(tankX);
    			yFromProj = pbullets.get(offset + i).getYFromProjectile(tankX, tankY);
    			xFromBullet = pbullets.get(offset + i).getX() - tankX;
    			if (i == 0)
    			{
    				System.out.print("bulletETA: ");
    				System.out.print(bulletETA);
    				System.out.print("   yFromProjectile: ");
    				System.out.print(yFromProj);
            		System.out.print("   xFromBullet: ");
            		System.out.println(xFromBullet);
    			}
        		
        		if (bulletETA >= 0.0)
    			{
	    			if (bulletETA > 60) 
	    			{
	    				if (yFromProj < (60/bulletETA) * space/4 + 20 && yFromProj > (60/bulletETA) * -space/4 + 20) 
		    			{
		    				if (yFromProj > 0) 
		    				{
		    					if (!moveDown) 
		    					{
		    						moveUp = true;
		    					}
		    				} else {
		    					if (!moveUp) 
		    					{
		    						moveDown = true;
		    					}
		    				}
		    			}
	    			} else if (bulletETA > 40) {
	    				if (yFromProj < (40/bulletETA) * space/3 + 20 && yFromProj > (40/bulletETA) * -space/3 + 20) 
		    			{
		    				if (yFromProj > 0) 
		    				{
		    					if (!moveDown) 
		    					{
		    						moveUp = true;
		    					}
		    				} else {
		    					if (!moveUp) 
		    					{
		    						moveDown = true;
		    					}
		    				}
		    			}
	    			} else if (bulletETA > 20) {
	    				if (yFromProj < (20/bulletETA) * space + 20 && yFromProj > (20/bulletETA) * -space + 20) 
		    			{
		    				if (yFromProj > 0) 
		    				{
		    					if (!moveDown) 
		    					{
		    						moveUp = true;
		    					}
		    				} else {
		    					if (!moveUp) 
		    					{
		    						moveDown = true;
		    					}
		    				}
		    			}
	    			} else {
	    				if (yFromProj < space + 20 && yFromProj > -space + 20) 
		    			{
		    				if (yFromProj > 0) 
		    				{
		    					if (!moveDown) 
		    					{
		    						moveUp = true;
		    					}
		    				} else {
		    					if (!moveUp) 
		    					{
		    						moveDown = true;
		    					}
		    				}
		    			}
	    			}
    			}
    		}
    		Random rand = new Random();
    		int val = rand.nextInt(30);
    		if (playerY + val < tankY) 
    		{
    			if (!moveDown) 
				{
					moveUp = true;
				}
    		}
    		if (playerY - val > tankY) 
    		{
				if (!moveUp) 
				{
					moveDown = true;
				}
    		}
    		if (playerY < tankY + 5 && playerY > tankY - 5)
    		{
    			isFiring = true;
    			System.out.println("firing");
    		}
    		val = rand.nextInt(3) - 1;
    		int pastCollisionAvgX = cmemory.getAvgX();       // TankAI will try to move away
    		xMove += val;									 // from where it last got hit
    		if (pastCollisionAvgX > 50)
    		{
	    		if (pastCollisionAvgX > tankX + 50)
	    		{
	    			xMove -= 1;
	    		}
	    		if (pastCollisionAvgX < tankX - 50)
	    		{
	    			xMove += 1;
	    		}
    		}
    		if (xMove > 2) 
    		{
    			moveRight = true;
    		} 
    		else if (xMove < -2) 
    		{
    			moveLeft = true;
    		}
    		if (xMove > 3) 
    		{
    			xMove = 1;
    		}
    		if (xMove < -3)
    		{
    			xMove = -1;
    		}
    	}
    }
    public boolean isMoveLeft() 
    {
        return moveLeft;  
    }
    public boolean isMoveRight() 
    {
    	return moveRight;
    }
    public boolean isFire() 
    {
    	return isFiring;
    }
    public boolean isMoveUp() 
    {
    	return moveUp;
    }
    public boolean isMoveDown() 
    {
        return moveDown;
    }
    public boolean isRotateUp() 
    {
        return rotateUp;
    }
    public boolean isRotateDown() 
    {
        return rotateDown;
    }
    public void calculateProjectile(Bullets b) 
    {
		pbullets.get(0).getSpeed();
    }
    public void addDeadPBullet() 
    {
    	deadpbulletscounter++;
    	if (deadpbullets >= deadpbullets) 
    	{
    		deadpbullets++;
    		deadpbulletscounter = 0;
    	}
    }
    public void addDeadTBullet() 
    {
    	deadpbulletscounter++;
    	if (deadtbullets >= deadtbullets) 
    	{
    		deadtbullets++;
    		deadtbulletscounter = 0;
    	}
    }
    public void saveCollision(double x)
    {
    	cmemory.saveCollision(x);
    }
}
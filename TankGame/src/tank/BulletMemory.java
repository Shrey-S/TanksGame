package tank;

import java.util.ArrayList;
import java.util.List;

public class BulletMemory 
{
	List<Integer> pbtimer = new ArrayList<>();     // Player bullet timer
    List<Integer> tbtimer = new ArrayList<>();
    List<Integer> pbtracker = new ArrayList<>();
    List<Integer> tbtracker = new ArrayList<>();   // Tank bullet tracker
    private final static int timeOnScreen = 250;
    
    public BulletMemory() 
    {
    	System.out.println("instantiated Bullet Memory");
    }
    public void addPBullets(int num) 
    {
    	pbtimer.add(timeOnScreen);
    	pbtracker.add(num);
    }
    public void addTBullets(int num) 
    {
    	tbtimer.add(timeOnScreen);
    	tbtracker.add(num);
    }
    public void update() 
    {
    	for (int i = 0; i < pbtimer.size(); i++) 
    	{
    		pbtimer.set(i, pbtimer.get(i)-1);
    		if (pbtimer.get(i) <= 0) {
    			pbtimer.remove(i);
    			pbtracker.remove(i);
    		}
    	}
    	for (int i = 0; i < tbtimer.size(); i++) 
    	{
    		tbtimer.set(i, tbtimer.get(i)-1);
    		if (tbtimer.get(i) <= 0) {
    			tbtimer.remove(i);
    			tbtracker.remove(i);
    		}
    	}
    }
    public int getPBullets() 
    {
    	int sum = 0;
    	for (int i = 0; i < pbtracker.size(); i++) 
    	{
    		sum += pbtracker.get(i);
    	}
    	return sum;
    }
    public int getTBullets() 
    {
    	int sum = 0;
    	for (int i = 0; i < tbtracker.size(); i++) 
    	{
    		sum += tbtracker.get(i);
    	}
    	return sum;
    }
}
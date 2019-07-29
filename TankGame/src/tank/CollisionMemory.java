package tank;

import java.io.*;
import java.util.Scanner;

public class CollisionMemory 
{
	CollisionMemory() throws IOException
	{
		initialize();
	}
	public void initialize() throws IOException 
	{
		boolean isFile = isFilePresent();
		if (isFile) 
		{
			return;
		} 
		else 
		{
			try
		    {
				PrintWriter pw = new PrintWriter("collisions.txt");
				pw.println("0");
				pw.close();
		    }
		    catch(IOException ex)
		    {
		        System.out.println (ex.toString());
		        System.out.println("Could not set");
		    }
		}
	}
	public boolean isFilePresent()
	{
		try
	    {
			File f = new File("collisions.txt");
			Scanner sc = new Scanner(f);
			int num = sc.nextInt();
			System.out.println("Read: " + num);
			sc.close();
			return true;
	    }
	    catch(IOException ex)
	    {
	        System.out.println (ex.toString());
	        System.out.println("File doesnt already exist");
	        return false;
	    }
	}
	public void set(int num) 
	{
		try
	    {
			PrintWriter pw = new PrintWriter("collisions.txt");
			pw.println(num);
			pw.close();
	    }
	    catch(IOException ex)
	    {
	        System.out.println (ex.toString());
	        System.out.println("Could not set");
	    }
	}
	public void append(int i)  
	{
		try
	    {
			FileWriter fw = new FileWriter("collisions.txt", true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(i);
			pw.close();
	    }
	    catch(IOException ex)
	    {
	        System.out.println (ex.toString());
	        System.out.println("Could not append");
	    }
	}
	public int read() 
	{
		try
	    {
			File f = new File("collisions.txt");
			Scanner sc = new Scanner(f);
			int num = sc.nextInt();
			System.out.println("Read: " + num);
			sc.close();
			return num;
	    }
	    catch(IOException ex)
	    {
	        System.out.println (ex.toString());
	        System.out.println("Could not read");
	    }
		return -1;
	}
	public int getAvgX() 
	{
		try
	    {
			int num;
			File f = new File("collisions.txt");
			Scanner sc = new Scanner(f);
			num = sc.nextInt();
			sc.close();
			if (num == 0)
			{
				return 0;
			} 
			else 
			{
				int dif = num % 10000;
				num = (num - dif) / 10000;
				return num;
			}
	    }
	    catch(IOException ex)
	    {
	        System.out.println (ex.toString());
	        System.out.println("Could not getAvgX");
	        return -1;
	    }
	}
	public int getNumEntries()
	{
		try
	    {
			int num;
			File f = new File("collisions.txt");
			Scanner sc = new Scanner(f);
			num = sc.nextInt();
			sc.close();
			if (num == 0)
			{
				return 0;
			} 
			else 
			{
				int dif = num % 10000;
				num = (num - dif) / 10000;
				return dif;
			}
	    }
	    catch(IOException ex)
	    {
	        System.out.println (ex.toString());
	        System.out.println("Could not getNumEntries");
	        return -1;
	    }
	}
	public void saveCollision(double x)
	{
		int numEntries = getNumEntries();
		int avgX = getAvgX();
		double sum = (avgX * numEntries) + x;
		numEntries++;
		sum = sum / numEntries;
		avgX = (int) Math.round(sum);
		avgX = avgX + (numEntries * 10000);
		this.set(avgX);
	}
}
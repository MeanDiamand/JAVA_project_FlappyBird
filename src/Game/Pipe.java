package Game;

import java.awt.image.BufferedImage;

public class Pipe 
{
    private int x;
    private int y;
    private int width; 
    private int height;
    private BufferedImage image; 
    private int gap; // gap between the upper and lower parts of the pipe
    private int distance; // distance between consecutive pipes
    private int min = -(1200 / 2 - 144 / 2); // Minimum vertical position for the upper part of the pipe
    private int max = 644 - 146 - 144 / 2 - 1200 / 2; // Maximum vertical position for the upper part of the pipe
    private int speed; // Movement speed of the pipe

    public Pipe(int s) 
    {
        gap = 144;
        distance = 244;
        image = BirdGame.Pipe_image;
        width = image.getWidth();
        height = image.getHeight();
        x = BirdGame.bg.getWidth();
        y = (int) (Math.random() * (max - min) + min);
        this.speed = s;
    }
    
    public void setX(int x)
    {
    	this.x = x;
    }
    
    public int getX()
    {
    	return this.x;
    }
    
    public int getY()
    {
    	return this.y;
    }
    
    public int getWidth()
    {
    	return this.width;
    }
    
    public int getHeight()
    {
    	return this.height;
    }
    
    public BufferedImage getImage()
    {
    	return this.image;
    }
    
    public int getGap()
    {
    	return this.gap;
    }
    
    public int getDistance()
    {
    	return this.distance;
    }

    public void step() 
    {
    	// Decrease the horizontal position of the pipe by the speed variable
    	// which determine how fast the pipe is moving to the left side of the screen
        x-=speed;
        
        // Check if entire pipe has moved completely off the left side of the screen
        if (x <= -width)
        {
        	// Reset the horizontal position of the pipe to the right of the screen
            x = BirdGame.bg.getWidth();
            
            // Generates a new random vertical positions for the gap of the pipe to appears at different height
            y = (int) (Math.random() * (max - min) + min);
        }
    }
}
package Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Bird 
{
    private int x;
    private int y;
    private int width; 
    private int height;
    private BufferedImage image; 
    private BufferedImage[] images; 
    
    // Bird properties
    private int g; //gravitational acceleration
    private double t;  //Time interval
    private double v = 0; // vertical velocity
    private double h = 0; // vertical displacement(height change)

    public Bird(int g, double t, String c) 
    {
        images = new BufferedImage[8];
        for (int i = 0; i < images.length; i++) 
        {
            try 
            {
                images[i] = ImageIO.read(Bird.class.getResourceAsStream(c + i + ".png"));
//                images[i] = ImageIO.read(Bird.class.getResourceAsStream("Pikazhu.png"));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
        image = BirdGame.bird_image;
        width = image.getWidth();
        height = image.getHeight();
        x = 120;
        y = 240;
        this.g = g;
        this.t = t;
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

    int index = 0;

    //Animates the bird's flight by cycling through a sequence of images
    public void fly() 
    {
    	// Assign the selected image from the images array to the image variable
    	// I'm using the % modulus operator to ensure that index stays
    	// in the valid range of the array indices
        image = images[index % images.length];
        
        // Increment the index to prepare it for the next frame of animation
        // which ensure that when the next time fly is called, a different 
        // image from the sequence will be select that result in the animation
        // of the bird flapping it wings
        index++;
    }

    //Simulates the bird's downward motion due to gravity
    public void down() 
    {
    	// Calculating the vertical displacement of the bird
    	// using the equations of motion under constant acceleration.
        h = v * t + g * t * t / 2; 
        
        // Update the vertical velocity of the bird by adding
        // the gravitational acceleration times with the time interval
        v = v + g * t; 
        
        // Update the vertical coordinate of the bird by incrementing
        // the the y axis with the height displacement
        y += (int) h;
    }

    //Makes the bird jump upward by setting its vertical velocity
    public void up() 
    {
        v = -30;
    }

    //Checks if the bird hits the ground
    public boolean hitGround(Ground ground) 
    {
    	// this.y is the position of the top of the bird, adding this.height
    	// to it, give us the position of the bottom of the bird.
    	// ground.getY() gave us the position of the top of the ground
    	// Here, we can check if the bottom of the bird touches the top 
    	// of the ground then it will return true
        boolean isHit = this.y + this.height >= ground.getY();
        return isHit;
    }

    //Checks if the bird hits the ceiling
    public boolean hitCeiling() 
    {
    	// this.y is the position of the top of the bird
    	// Here, we can check if the top of the bird is touching the ceiling
    	// which located at the 0 coordinate of the y-axis
    	// then it will return true;
        boolean isHit = this.y <= 0;
        return isHit;
    }

    //Checks if the bird hits a pipe by comparing its position with the pipe's dimensions
    public boolean hitPipe(Pipe c) 
    {
    	// this.x is the position of the left side of the bird
    	// adding this.width results in the position of the right side of the bird
    	// c.getX() will return the left side of the pipe
    	// Here, we can check if the right side of the bird touches the left
    	// side of the pipe then b1 is true
        boolean b1 = this.x + this.width >= c.getX();
        
        // Here, we can check if the left side of the bird touches the right
        // side of the pipe then b2 is true
        boolean b2 = this.x <= c.getX() + c.getWidth();
        
        // Here, we can check if the top of the bird touches the top edge of
        // the open gap then b3 is true
        boolean b3 = this.y <= c.getY() + c.getHeight() / 2 - c.getGap() / 2;
        
        // Here, we can check if the bottom of the bird touches the bottom
        // edge of the open gap then b4 is true
        boolean b4 = this.y + this.height >= c.getY() + c.getHeight() / 2 + c.getGap() / 2;
        
        // This method returns true if the bird is touching the pipe both
        // horizontally and vertically
        return b1 && b2 && (b3 || b4);
    }
}
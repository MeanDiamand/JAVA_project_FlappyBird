package Game;

import java.awt.image.BufferedImage;

public class Ground 
{
    private int x;
    private int y;
    private int width; 
    private int height;
    private BufferedImage image; 

    public Ground()
    {
        image = BirdGame.ground_image;
        x = 0;
        y = BirdGame.bg.getHeight() - image.getHeight();

        width = image.getWidth();
        height = image.getHeight();
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

    // Animates the movement of the ground in the game
    public void step()
    {
    	// Decrements the horizontal position of the ground which moving the one
    	// pixel to the left
        x--;
        
        // Check if the ground has moved completely out of the visible frame
        if(x <= 432 - width)
        {
        	// Reset the horizontal position of the ground 
        	// resulting the a scrolling effect of the ground
            x=0;
        }
    }
}


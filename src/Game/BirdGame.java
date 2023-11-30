package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

import HighScore.Collection;
import HighScore.HighScoreEntry;
import Screen.FlappyBirdMainMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings("serial")
public class BirdGame extends JPanel
{
    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int GAME_OVER = 2;  
    int state = START;
    int score = 0; 

    static BufferedImage bg = null; 
    static BufferedImage start = null; 
    static BufferedImage ground_image = null;
    static BufferedImage bird_image = null; 
    static BufferedImage Pipe_image = null; 
    private int gravitationalAcceleration;
    private double timeInterval;
    private int speed; 
    private String color;
    
    private Timer gameTimer;
    
    Collection collection = new Collection();
    
    Ground ground;
    Bird bird;
    Pipe Pipe1;
    Pipe Pipe2;
    
    private FlappyBirdMainMenu mainMenu; // Add a field to store the reference

    // Loading the images needed for the game into memory when the BirdGame class is instantiated
    static 
    {
        try 
        {
            bg = ImageIO.read(BirdGame.class.getResourceAsStream("bg.png"));
            start = ImageIO.read(BirdGame.class.getResourceAsStream("start.png"));
            ground_image = ImageIO.read(BirdGame.class.getResourceAsStream("ground.png"));
            Pipe_image = ImageIO.read(BirdGame.class.getResourceAsStream("Pipe.png"));
            bird_image = ImageIO.read(BirdGame.class.getResourceAsStream("cyan0.png"));
            bird_image = ImageIO.read(BirdGame.class.getResourceAsStream("white0.png"));
        } 
        catch (IOException e) 
        {
        	// It will print exception stack trace when IOException occurs during loading the images
            e.printStackTrace();
        }
    }

    public BirdGame(FlappyBirdMainMenu mainMenu, String c, int g, double t, int s) 
    {
    	this.mainMenu = mainMenu; // Store the reference
    	
    	// Setting Focus and Requesting Window focus to ensure that the keyboard
    	// input events are captured by the game panel
    	this.setFocusable(true);
    	this.requestFocusInWindow();
    	
    	// Setting initial parameter
    	this.gravitationalAcceleration = g;
    	this.timeInterval = t;
    	this.speed = s;
    	this.color = c;
    	
    	// Creating game objects
        bird = new Bird(gravitationalAcceleration, timeInterval, color);
        ground = new Ground();
        Pipe1 = new Pipe(speed);
        Pipe2 = new Pipe(speed);
        int tmp = Pipe1.getX() + Pipe1.getDistance();
        Pipe2.setX(tmp);
        
        // Initialize the swing Timer
        // The timer trigger an ActionEvent every 10 ms
        gameTimer = new Timer(10, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
					gameUpdate();
				} 
                catch (Throwable e1) 
                {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	repaint();
            }
        });

        // Start the timer
        gameTimer.start();
    }
    
    public int getScore()
    {
    	return this.score;
    }
    
    public int getState()
    {
    	return this.state;
    }
    
    public void startGame() 
    {
        if (state == START) 
        {
            state = RUNNING;
        }
    }
    
    @Override
    public void paint(Graphics g) 
    {
    	//ensures that the panel is properly rendered before adding custom painting
    	super.paintComponent(g);
    	
    	// Draw the background at the top-left corner
        g.drawImage(bg, 0, 0, null); 
        
        // Draw the Start screen
        if (state == START) 
        {
            g.drawImage(start, 0, 0, null);  
        }
        
        // Draw the pipes
        g.drawImage(Pipe1.getImage(), Pipe1.getX(), Pipe1.getY(), null); 
        g.drawImage(Pipe2.getImage(), Pipe2.getX(), Pipe2.getY(), null); 
        
        // Draw the bird
        g.drawImage(bird.getImage(), bird.getX(), bird.getY(), null); 
        
        // Draw the ground
        g.drawImage(ground.getImage(), ground.getX(), ground.getY(), null);  
        
        // Create font for displaying the score
        Font font = new Font("Arial", Font.BOLD, 25); 
        
        g.setFont(font); 
        
        // Making the shadow effect by adding the black text
        g.setColor(Color.BLACK);  
        g.drawString("SCORE:  " + score, 30, 50);
        
        // Adding the white text on the black text to give a finishing touch
        g.setColor(Color.WHITE);  
        g.drawString("SCORE:  " + score, 28, 48);
    }

    
    public boolean isGameOver() 
    {
        boolean isHit = false;

        if (bird.hitGround(ground)) 
        {
            isHit = true;
        } 
        else if (bird.hitCeiling()) 
        {
            isHit = true;
        } 
        else if (bird.hitPipe(Pipe1)) 
        {
            isHit = true;
        } 
        else if (bird.hitPipe(Pipe2)) 
        {
            isHit = true;
        }

        return isHit;
    }

    public void gameUpdate() throws Throwable 
    {
        if (state == START) 
        {
            ground.step();
            bird.fly();
        } 
        else if (state == RUNNING) 
        {
            ground.step();
            Pipe1.step();
            Pipe2.step();
            bird.fly();
            bird.down();
            if (isGameOver()) 
            {
                state = GAME_OVER;
                System.out.println("Game Over!");
                showGameOverDialog();
                gameTimer.stop();  // Stop the timer when the game is over
            }

            // Check if the bird pass through the pipes then increment the 
            // score by one point per each pass
            if (bird.getX() / speed == Pipe1.getX() / speed + Pipe1.getWidth() / speed + 1 || bird.getX() / speed == Pipe2.getX() / speed + Pipe2.getWidth() / speed + 1) 
            {
                score += 1;
            }
        }
    }

    
    public void action() throws Exception 
    {
    	//Play by Spacebar key click
        this.addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e) 
            {
                System.out.println(e.getKeyCode());
                if(e.getKeyCode() == 32) //32 is the spacebar keycode
                {   
                	startGame();

                    if (state == RUNNING) 
                    {
                        bird.up(); 
                    }
                }
            }
        });

        //Play by mouse click
        this.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            { 
            	System.out.println("mouse");
            	startGame();

                if (state == RUNNING) 
                {
                    bird.up(); 
                }
            }
        });
    	gameTimer.start();
    }
    
    public void showGameOverDialog() throws Throwable 
    {
        JTextField usernameField = new JTextField();
        Object[] message = {
                "Game Over!\nYour score: " + score,
                "Enter your username:",
                usernameField
        };

        int option = JOptionPane.showOptionDialog(
                this,
                message,
                "Game Over",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Play Again", "Exit"}, // Buttons array
                JOptionPane.DEFAULT_OPTION); // No default button
        
        if (option == JOptionPane.OK_OPTION) 
        {
            String username = usernameField.getText().trim();
            if (!username.isEmpty()) 
            {
                collection.add(new HighScoreEntry(username, this.score));
                collection.save("Highscore.ser");
            }
            if (mainMenu != null) 
            {
                mainMenu.goToLevelSelection();
            }
        }
        else if (option == 1) // Exit button
        { 
        	String username = usernameField.getText().trim();
        	if (!username.isEmpty()) 
        	{
                collection.add(new HighScoreEntry(username, this.score));
                collection.save("Highscore.ser");
            }
            System.exit(0); //terminate the game
        }
    }
}

package Screen;

import HighScore.HighScoreScreen;
import Game.BirdGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlappyBirdMainMenu implements LevelSelectionScreen.LevelSelectionListener, HighScoreScreen.HighScoreScreenListener
{
	private JFrame frame;
    private JButton startButton, exitButton, highscoreButton;
    private JLabel imageLabel;
    private ImageIcon originalIcon;
    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;

    public FlappyBirdMainMenu() 
    {
        frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(432, 644);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);  // Prevent resizing

        cardLayout = new CardLayout();
        cardLayoutPanel = new JPanel(cardLayout);
        setupMainMenu();

        frame.add(cardLayoutPanel);
        frame.setVisible(true);
    }
    
    public void setupMainMenu() 
    {
    	// Set up the main panel with a BorderLayout
        JPanel mainMenuPanel = new JPanel(new BorderLayout());

        // Load the background image and set it to the imageLabel
        originalIcon = new ImageIcon("flappy_bird_image.png");
        imageLabel = new JLabel(originalIcon);
        imageLabel.setLayout(new BorderLayout()); // Let imageLabel act as a container

        // Create the transparent buttons panel and center it on the image
        JPanel buttonsPanel = createCenteredButtonsPanel();

        imageLabel.add(buttonsPanel, BorderLayout.CENTER); // Add the buttons directly to the imageLabel
        mainMenuPanel.add(imageLabel, BorderLayout.CENTER); 
        cardLayoutPanel.add(mainMenuPanel, "MainMenu");
    }
    
    public JPanel createCenteredButtonsPanel() 
    {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS)); // Arrange the buttons in a vertical order
        buttonsPanel.setOpaque(false); // Make it transparent

        // Instantiate a Font class object for setting the font style for the text
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        
        // Setting up the start button
        startButton = new JButton("Start Game");
        styleButton(startButton, buttonFont);
        startButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	setupLevelSelection();
                cardLayout.show(cardLayoutPanel, "LevelSelection");
            }
        });

        highscoreButton = new JButton("High Score");
        styleButton(highscoreButton, buttonFont);
        highscoreButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	setupHighScore();
                cardLayout.show(cardLayoutPanel, "HighScores");
            }
        });

        exitButton = new JButton("Exit");
        styleButton(exitButton, buttonFont);
        exitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.exit(0);
            }
        });

        buttonsPanel.add(Box.createVerticalGlue());
        buttonsPanel.add(startButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(highscoreButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(exitButton);
        buttonsPanel.add(Box.createVerticalGlue());

        return buttonsPanel;
    }
    
    public void styleButton(JButton button, Font font) 
    {
        button.setFont(font); // Set the font style for the text
        button.setMaximumSize(new Dimension(150, 40)); // Set the dimension of the button
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Align the button horizontally in the center
    }
    
    public void setupLevelSelection() 
    {
        LevelSelectionScreen levelSelectionPanel = new LevelSelectionScreen(this);
        cardLayoutPanel.add(levelSelectionPanel, "LevelSelection");
    }
    
    public void setupHighScore() 
    {
        HighScoreScreen HighScorePanel = new HighScoreScreen(this);
        cardLayoutPanel.add(HighScorePanel, "HighScores");
    }
    
    public void goToLevelSelection() {
        cardLayout.show(cardLayoutPanel, "LevelSelection");
    }

    @Override
    public void onEasySelected() throws Exception {
        BirdGame game = new BirdGame(this, "cyan", 6, 0.15, 1);
        cardLayoutPanel.add(game, "EasyMode");
        cardLayout.show(cardLayoutPanel, "EasyMode");
        game.requestFocusInWindow(); // Request focus after showing the panel
        game.action();
    }

    @Override
    public void onHardSelected() throws Exception {
        BirdGame game = new BirdGame(this, "white", 8, 0.20, 3);
        cardLayoutPanel.add(game, "HardMode");
        cardLayout.show(cardLayoutPanel, "HardMode");
        game.requestFocusInWindow(); // Request focus after showing the panel
        game.action();
    }

    @Override
    public void onBackSelected() 
    {
        cardLayout.show(cardLayoutPanel, "MainMenu");
    }
}
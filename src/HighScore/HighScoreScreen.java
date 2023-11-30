package HighScore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@SuppressWarnings("serial")
public class HighScoreScreen extends JPanel
{
	private JButton backButton;
	private JLabel backgroundLabel;
	private JTextArea title;
	private JTextArea highScoreTextArea; // Added JTextArea to display high score
	
	public interface HighScoreScreenListener
	{
		void onBackSelected();
	}
	
	private HighScoreScreenListener listener;
	public Collection highScoreCollection;
	
	public HighScoreScreen(HighScoreScreenListener listener)
	{
		this.listener = listener;
		setLayout(new BorderLayout());
		
		ImageIcon backgroundImageIcon = new ImageIcon("bg.png");
		
		backgroundLabel = new JLabel(backgroundImageIcon);
		backgroundLabel.setLayout(new BorderLayout());
		
		//setup the highscore list and the button
		setup();
		
		//add the bg.png as the background
		add(backgroundLabel);
		
		// Load high scores when the screen is constructed
        loadHighScores();
        
        // Display high scores on the screen
        displayHighScores();
	}
	
	public void setup() 
    {
        setBackground(new Color(78,192,201));
        
        title = new JTextArea("Highscore");
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setEditable(false); // Make it non editable
        title.setOpaque(false); // Make it transparent
        title.setForeground(Color.GREEN); // Set text color to white
        backgroundLabel.add(title, BorderLayout.NORTH);
        
        highScoreTextArea = new JTextArea();
        highScoreTextArea.setFont(new Font("Arial", Font.BOLD, 20));
        highScoreTextArea.setEditable(false); // Make it non editable
        highScoreTextArea.setOpaque(false); // Make it transparent
        highScoreTextArea.setForeground(Color.BLUE);// Set text color to white

        backgroundLabel.add(highScoreTextArea, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.setOpaque(false); // This makes the panel transparent to see the background image

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Dimension maxButtonSize = new Dimension(150, 40);

        backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setMaximumSize(maxButtonSize);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(listener != null) listener.onBackSelected();
            }
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(Box.createVerticalStrut(30));  // Add space below the button

        backgroundLabel.add(buttonsPanel, BorderLayout.SOUTH);
    }
	
	public void loadHighScores() 
	{
        // Load high scores from a serialized file using your Collection class
        highScoreCollection = new Collection();
        try 
        {
            highScoreCollection.load("Highscore.ser");
        } 
        catch (Throwable e) 
        {
            e.printStackTrace();
        }
    }
	
	public void displayHighScores() 
	{
        // Get the list of high scores from the collection
        List<HighScoreEntry> highScores = highScoreCollection.list();

        // Display high scores in the JTextArea
        StringBuilder stringBuilder = new StringBuilder();

        for (HighScoreEntry entry : highScores) 
        {
            stringBuilder.append(entry.getPlayerName()).append(": ").append(entry.getScore()).append("\n");
            System.out.println(entry.getPlayerName());
        }

        highScoreTextArea.setText(stringBuilder.toString());
    }
}

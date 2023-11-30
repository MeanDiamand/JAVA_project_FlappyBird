package Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class LevelSelectionScreen extends JPanel 
{

    private JButton easyButton;
    private JButton hardButton;
    private JButton backButton;
    private JLabel backgroundLabel; 
    
    // Define an interface to handle button clicks in FlappyBirdMainMenu class
    public interface LevelSelectionListener 
    {
        void onEasySelected() throws Exception;
        void onHardSelected() throws Exception;
        void onBackSelected();
    }

    private LevelSelectionListener listener;

    public LevelSelectionScreen(LevelSelectionListener listener) 
    {
    	this.listener = listener;
        setLayout(new BorderLayout());

        // Load the background image
        ImageIcon backgroundImageIcon = new ImageIcon("bg.png");
        
        backgroundLabel = new JLabel(backgroundImageIcon);
        
        // Set the layout manager for backgroundLabel to be a BorderLayout
        backgroundLabel.setLayout(new BorderLayout());

        // Set up the other components on top of the background
        setup();

        add(backgroundLabel);
    }

    public void setup() 
    {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS)); // Arrange the buttons in a vertical order
        buttonsPanel.setOpaque(false); // This makes the panel transparent to see the background image

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Dimension maxButtonSize = new Dimension(150, 40); 
        
        easyButton = new JButton("Easy Mode");
        easyButton.setFont(buttonFont);
        easyButton.setMaximumSize(maxButtonSize);
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Align the button horizontally in the center
        easyButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(listener != null)
                {
                	try 
                	{
						listener.onEasySelected();
					} 
                	catch (Exception e1) 
                	{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }
        });
        
        hardButton = new JButton("Hard Mode");
        hardButton.setFont(buttonFont);
        hardButton.setMaximumSize(maxButtonSize);
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Align the button horizontally in the center
        hardButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(listener != null)
                {
                	try 
                	{
						listener.onHardSelected();
					} 
                	catch (Exception e1) 
                	{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }
        });

        backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setMaximumSize(maxButtonSize);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Align the button horizontally in the center
        backButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(listener != null) listener.onBackSelected();
            }
        });

        buttonsPanel.add(Box.createVerticalGlue());
        buttonsPanel.add(easyButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(hardButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(backButton);
        buttonsPanel.add(Box.createVerticalGlue()); 

        backgroundLabel.add(buttonsPanel, BorderLayout.CENTER);
    }
}

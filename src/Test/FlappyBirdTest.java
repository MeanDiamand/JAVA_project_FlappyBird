package Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Game.Bird;
import Game.BirdGame;
import HighScore.Collection;
import HighScore.HighScoreEntry;
import HighScore.HighScoreScreen;
import HighScore.ScoreComparator;

public class FlappyBirdTest 
{
	private BirdGame birdGame;
	private Bird bird;
	private ScoreComparator scoreComparator;
	private Collection highScoreCollection;
	private HighScoreScreen highScoreScreen;
	
	@Before
	public void setup()
	{
		birdGame = new BirdGame(null, "cyan", 6, 0.20, 1);
		bird = new Bird(6, 0.20, "cyan");
		scoreComparator = new ScoreComparator();
		highScoreCollection = new Collection();
		highScoreScreen = new HighScoreScreen(null);
	}
	
	// BirdGame JUnit test
	// Test if the game started with a correct state and the zero point or not
	@Test
	public void testInitialGameState() 
	{
        assertEquals(BirdGame.START, birdGame.getState());
        assertEquals(0, birdGame.getScore());
    }
	
	// Test if the state of the game is changing or not when starting the game
	@Test
    public void testGameStartChangesState() throws Exception 
	{
        birdGame.startGame();
        assertEquals(BirdGame.RUNNING, birdGame.getState());
    }
	
	// Test if the Highscore.ser file exist and can be read or not
	@Test
    public void testHighScoreFileExistandCanRead() 
	{
        // Specify the path to the Highscore.ser file
        String fileName = "Highscore.ser";

        try 
        {
            File file = new File(fileName);

            // Check if the file exists
            assertTrue("Highscore.ser file does not exist", file.exists());

            // Check if the file can be read
            assertTrue("Highscore.ser file cannot be read", file.canRead());
        } 
        catch (Exception e) 
        {
            fail("Exception occurred while testing Highscore.ser file: " + e.getMessage());
        }
    }
	
	// Bird JUnit test
	@Test
    public void testBirdInitialization() 
	{
        assertEquals(120, bird.getX());
        assertEquals(240, bird.getY());
        assertNotNull(bird.getImage());
    }
	
	@Test
    public void testFly() 
	{
        BufferedImage initialImage = bird.getImage();
        bird.fly();
        assertNotEquals(initialImage, bird.getImage());
    }
	
	// ScoreComparator JUnit test
	@Test
	public void testCompare() 
	{
	        HighScoreEntry entry1 = new HighScoreEntry("Player1", 10);
	        HighScoreEntry entry2 = new HighScoreEntry("Player2", 20);
	        HighScoreEntry entry3 = new HighScoreEntry("Player3", 15);

	        List<HighScoreEntry> scores = new ArrayList<>();
	        scores.add(entry1);
	        scores.add(entry2);
	        scores.add(entry3);

	        // Sorting the collection in descending order
	        Collections.sort(scores, scoreComparator);

	        // Check if the list is sorted in descending order based on score or not
	        assertEquals(entry2, scores.get(0)); // Highest score
	        assertEquals(entry3, scores.get(1)); // Second-highest score
	        assertEquals(entry1, scores.get(2)); // Lowest score
    }
	
	// Collection JUnit test
	@Test
    public void testAdd() 
	{
        HighScoreEntry highScoreEntry = new HighScoreEntry("Player1", 8);
        highScoreCollection.add(highScoreEntry);

        List<HighScoreEntry> entries = highScoreCollection.list();
        
        // Check if the list's size is equal to one or not since we just added one player
        assertEquals(1, entries.size());
        
        // Check if the data is the same or not
        assertEquals(highScoreEntry, entries.get(0));
    }
	
	@Test
    public void testSaveAndLoad() throws Throwable 
	{
        // Adding some test entries
        HighScoreEntry entry1 = new HighScoreEntry("Player1", 10);
        HighScoreEntry entry2 = new HighScoreEntry("Player2", 15);

        highScoreCollection.add(entry1);
        highScoreCollection.add(entry2);

        // Save the entries to a test file
        highScoreCollection.save("testHighscore.ser");

        // Load the entries from the test file
        Collection loadedCollection = new Collection();
        loadedCollection.load("testHighscore.ser");

        // Check if the loaded entries match the original ones
        List<HighScoreEntry> loadedEntries = loadedCollection.list();

        assertEquals(2, loadedEntries.size());

        // Assuming the entries are sorted by score in descending order
        assertEquals("Player2", loadedEntries.get(0).getPlayerName());
        assertEquals(15, loadedEntries.get(0).getScore());

        assertEquals("Player1", loadedEntries.get(1).getPlayerName());
        assertEquals(10, loadedEntries.get(1).getScore());

        // Clean up: Delete the test file
        File testFile = new File("testHighscore.ser");
        assertTrue(testFile.delete());
    }	
    
    @Test
    public void testListSorting() 
    {
        HighScoreEntry highScoreEntry1 = new HighScoreEntry("Player1", 10);
        HighScoreEntry highScoreEntry2 = new HighScoreEntry("Player2", 12);
        HighScoreEntry highScoreEntry3 = new HighScoreEntry("Player3", 8);

        highScoreCollection.add(highScoreEntry1);
        highScoreCollection.add(highScoreEntry2);
        highScoreCollection.add(highScoreEntry3);

        List<HighScoreEntry> sortedEntries = highScoreCollection.list();

        // Check if the list is sorted in descending order by score
        assertEquals(highScoreEntry2, sortedEntries.get(0));
        assertEquals(highScoreEntry1, sortedEntries.get(1));
        assertEquals(highScoreEntry3, sortedEntries.get(2));
    }
    
    // HighScoreScreen JUnit test
    @Test
    public void testLoadHighScores() 
    {
        // Assume that the "Highscore.ser" file contains some Username and Highscore 
        highScoreScreen.loadHighScores();

        // Check if the highScoreCollection is not null after loading
        assertNotNull(highScoreScreen.highScoreCollection);

        // Check if the loaded high scores are not empty
        assertFalse(highScoreScreen.highScoreCollection.list().isEmpty());
    }
}

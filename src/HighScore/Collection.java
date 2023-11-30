package HighScore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Collection 
{
	List<HighScoreEntry> collection;
	
	public Collection()
	{
		this.collection = new ArrayList<>();
	}
	
	public void add(HighScoreEntry highscore)
	{
		collection.add(highscore);
	}
	
	public void save(String file) throws Throwable 
	{
        // Load existing entries
        List<HighScoreEntry> existingEntries = loadExistingEntries(file);

        // Append the new entry to the existing list
        existingEntries.addAll(collection);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) 
        {
            oos.writeObject(existingEntries);
        }
    }

    public void load(String file) throws Throwable 
    {
        // Load existing entries
        collection = loadExistingEntries(file);
    }

    public List<HighScoreEntry> list() 
    {
    	Comparator<HighScoreEntry> comparator = new ScoreComparator();
    	Collections.sort(collection, comparator);
        return new ArrayList<>(collection);
    }

    @SuppressWarnings("unchecked")
	public List<HighScoreEntry> loadExistingEntries(String file) 
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) 
        {
            return (List<HighScoreEntry>) ois.readObject();
        } 
        catch (FileNotFoundException e) 
        {
            // File not found, return an empty list
            return new ArrayList<>();
        } 
        catch (IOException | ClassNotFoundException e) 
        {
            e.printStackTrace();
            // If an exception occurs during reading the object 
            // or if the class of the serialized object cannot be found
            return new ArrayList<>();
        }
    }
}

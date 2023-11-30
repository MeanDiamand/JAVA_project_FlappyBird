package HighScore;

import java.io.Serializable;

public class HighScoreEntry implements Serializable 
{
    private String playerName;
    private int score;
    private static final long serialVersionUID = 123456789L;

    public HighScoreEntry(String playerName, int score) 
    {
        this.playerName = playerName;
        this.score = score;   
    }
    
    public String getPlayerName()
    {
    	return this.playerName;
    }
    
    public int getScore()
    {
    	return this.score;
    }

    @Override
    public String toString() 
    {
        return playerName + ": " + score;
    }
    
    public int compareTo(HighScoreEntry other)
	{
		return Integer.compare(this.score, other.score);
	}
}

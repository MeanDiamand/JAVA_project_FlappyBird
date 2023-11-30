package HighScore;

import java.util.Comparator;

public class ScoreComparator implements Comparator<HighScoreEntry>
{
	@Override
	public int compare(HighScoreEntry s1, HighScoreEntry s2)
	{
		return s2.getScore() - s1.getScore();		
	}
}
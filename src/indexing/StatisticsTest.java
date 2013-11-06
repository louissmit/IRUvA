package indexing;

import java.util.HashMap;
import java.util.TreeMap;

public class StatisticsTest {

	public static int numberOfTokens(HashMap <String, TreeMap <String, Integer>> invIndex)
	{
		int totalNumber=0;
		for(String token:invIndex.keySet())
		{
			TreeMap <String, Integer> occurences=invIndex.get(token);
			for(String occur:occurences.keySet())
			{
				totalNumber+=occurences.get(occur);
			}	
		}
		return totalNumber;
	}
	public static int numberOfUniqueTokens(HashMap <String, TreeMap <String, Integer>> invIndex)
	{
		return invIndex.size();
	}
	public static int numberOfOccurences(HashMap <String, TreeMap <String, Integer>> invIndex,String token)
	{
		int totalNumber=0;
		TreeMap <String, Integer> occurences=invIndex.get(token);
		for(String occur:occurences.keySet())
		{
			totalNumber+=occurences.get(occur);
		}
		return totalNumber;
	}
}

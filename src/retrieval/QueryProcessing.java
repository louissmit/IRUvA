package retrieval;

import indexing.Indexor;
import indexing.StatisticsTest;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeMap;

import models.IQuery;
import models.StringQuery;

public class QueryProcessing {
	
	protected IRetrievalModel retrievalModel;
	protected String docPath;
	protected HashMap <String, TreeMap <String, Integer>> invIndex;
	protected double avgdl;
	protected HashMap <String, Integer> docList;
	
	public QueryProcessing(IRetrievalModel _retrievalModel,String _docPath)
	{
		this.docPath=_docPath;
		this.retrievalModel=_retrievalModel;
		Indexor ind = new Indexor();
		this.invIndex = ind.makeIndex(docPath);
		this.avgdl = ind.getAvgLength();
		this.docList = ind.getDocList();	
	}
	
	public HashMap <String, Double> CalculateRank(IQuery query)
	{
		HashMap <String, Double> rank = this.retrievalModel.getRanking(query, invIndex, docList, avgdl);
		return rank;
	}
	
	public void PrintStatistics()
	{
		System.out.println("Statistics:");
		System.out.println("Number of tokens: "+StatisticsTest.numberOfTokens(invIndex));
		System.out.println("Number of unique tokens: "+StatisticsTest.numberOfUniqueTokens(invIndex));
		System.out.println("Number of \'of\' tokens: "+StatisticsTest.numberOfOccurences(invIndex, "of"));
	}
	
	public HashMap <String, Double> CalculateAndSaveToFileRank(IQuery query,String name)
	{
		HashMap <String, Double> rank = this.CalculateRank(query);
		PrintWriter writer;
		try {
			writer = new PrintWriter(name);
			int i=0;
			for (String fileName:rank.keySet())
			{
				i++;
				String line=query.getQueryID()+ " Q0 ";
				line+=fileName;
				line=line+" "+Integer.toString(i)+" ";
				line+=Double.toString(rank.get(fileName));
				line=line+" "+this.retrievalModel.toString();
				writer.println(line);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return rank;
	}

}

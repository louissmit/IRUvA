package main;

import java.util.HashMap;
import java.util.TreeMap;

import com.sun.corba.se.spi.monitoring.StatisticsAccumulator;

import models.IQuery;
import models.StringQuery;
import retrieval.BM25;
import retrieval.IRetrievalModel;
import indexing.*;

public class Main {

	
	public static void main(String[] args) {
		
		Indexor ind = new Indexor();
		
		HashMap <String, TreeMap <String, Integer>> invIndex = ind.makeIndex();
		double avgdl = ind.getAvgLength();
		HashMap <String, Integer> docList = ind.getDocList();
		String [] queryStrings= {"think", "adwadwa"};
		IQuery query=new StringQuery(queryStrings);
		IRetrievalModel retrevalModel=new BM25();
		HashMap <String, Double> rank = retrevalModel.getRanking(query, invIndex, docList, avgdl);
		
		String ranking = "";
		
		for(String document: rank.keySet()){
			ranking = "Document: "+document+ ", Score : "+ rank.get(document) +"\n"+ranking;
		}
		
		System.out.println(ranking);
		System.out.println("Statistics:");
		System.out.println(StatisticsTest.numberOfTokens(invIndex));
		System.out.println(StatisticsTest.numberOfUniqueTokens(invIndex));
		System.out.println(StatisticsTest.numberOfOccurences(invIndex, "of"));
		
	/*for(String token: invIndex.keySet()){
			
			System.out.println(token+":");
			
			for (String doc : invIndex.get(token).keySet()){
				System.out.print(doc+":"+invIndex.get(token).get(doc)+", ");
			}
			
			System.out.println("");
			
		}
		*/
	}

}

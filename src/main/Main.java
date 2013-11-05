package main;

import java.util.HashMap;
import java.util.TreeMap;

import models.IQuery;
import models.StringQuery;
import retrieval.BM25;
import indexing.Indexor;

public class Main {

	
	public static void main(String[] args) {
		
		Indexor ind = new Indexor();
		
		HashMap <String, TreeMap <String, Integer>> invIndex = ind.makeIndex();
		double avgdl = ind.getAvgLength();
		HashMap <String, Integer> docList = ind.getDocList();
		String [] queryStrings= {"think", "adwadwa"};
		IQuery query=new StringQuery(queryStrings);
		HashMap <String, Double> rank = BM25.getRanking(query, invIndex, docList, avgdl);
		
		String ranking = "";
		
		for(String document: rank.keySet()){
			ranking = "Document: "+document+ ", Score : "+ rank.get(document) +"\n"+ranking;
		}
		
		System.out.println(ranking);
		
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

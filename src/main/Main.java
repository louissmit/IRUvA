package main;

import java.util.HashMap;
import java.util.TreeMap;

import retrieval.BM25;
import indexing.Indexor;

public class Main {

	
	public static void main(String[] args) {
		
		Indexor ind = new Indexor();
		
		HashMap <String, TreeMap <String, Integer>> invIndex = ind.makeIndex();
		double avgdl = ind.getAvgLength();
		HashMap <String, Integer> docList = ind.getDocList();
		String [] query = {"think", "adwadwa"};
		TreeMap <Double, String> rank = BM25.getRanking(query, invIndex, docList, avgdl);
		
		String ranking = "";
		
		for(double score: rank.keySet()){
			ranking = "Document: "+rank.get(score)+ ", Score : "+ score +"\n"+ranking;
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

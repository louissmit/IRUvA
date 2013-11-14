package retrieval;

import java.util.HashMap;
import java.util.TreeMap;
import indexing.Indexor.*;

import models.IQuery;

public class ParsimLM implements IRetrievalModel{
	
	double lambda = 0.2;

	@Override
	public HashMap<String, Double> getRanking(IQuery queryObject,
			HashMap<String, TreeMap<String, Integer>> invIndex,
			HashMap<String, Integer> docList, double avgdl) {
		
		String [] query=queryObject.getQuery();
		
		HashMap <String, Double> rank = new HashMap <String, Double>();
		double score = 0;
		
		int csum = 0; //occurrecies of all the terms in the collection
		int [] qsum = new int[query.length];
		
		for(String doc: docList.keySet()){
			int i = 0;
			for(String term: query){
				if(!invIndex.containsKey(term)) continue;
				if(!invIndex.get(term).containsKey(doc)) continue;
				int x = invIndex.get(term).get(doc);
				csum += x;
				qsum[i] += x;
				i++;
			}
		}
		
		
		//calculate the probabilities P(t|D) for each document
		
		double [] ptd = new double[docList.size()];
		
		for(String doc: docList.keySet()){
			
			//for (String term: query){
			//	StatisticsTest
			//}
			
		}
		
		return null;
	}

}

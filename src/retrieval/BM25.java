package retrieval;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import models.IQuery;

public class BM25 implements IRetrievalModel{
	
	public final float k1 = 1.5f;
	public final float b = 0.75f;
	
	
	public HashMap <String, Double> getRanking(IQuery queryObject, HashMap <String, TreeMap <String, Integer>> invIndex, HashMap <String, Integer> docList, double avgdl){
		
		String [] query=queryObject.getQuery();
		HashMap <String, Double> rank = new HashMap <String, Double>();
		double score = 0;
		
		
		for(String doc: docList.keySet()){
		
			score = 0;
			int dLength = docList.get(doc);
			
			for(String term: query){
				if(!invIndex.containsKey(term)) continue;
				if(!invIndex.get(term).containsKey(doc)) continue;
				int fqd = invIndex.get(term).get(doc);
			    //score += Math.log((docList.size()-invIndex.get(term).size()+0.5)/(invIndex.get(term).size()+0.5))*(fqd*(k1+1))/(fqd+k1*(1-b+b*(dLength/avgdl))); //from wikipedia
				score += Math.log((docList.size())/(invIndex.get(term).size()))*(fqd*(k1+1))/(fqd+k1*(1-b+b*(dLength/avgdl))); // from lectures slides
			}
			if (score == 0) continue;
		rank.put(doc, score);
		}
		
		return sort(rank);
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public static HashMap <String, Double> sort(HashMap <String, Double> unsortMap) {
		 
	
		List  list = new LinkedList (unsortMap.entrySet());
		
		
 
		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
                                       .compareTo(((Map.Entry) (o1)).getValue());
			}
		});
 
		// put sorted list into map again
                //LinkedHashMap make sure order in which keys were inserted
		HashMap sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

}

package retrieval;

import java.util.HashMap;
import java.util.TreeMap;

public class BM25 {
	
	public static final float k1 = 1.5f;
	public static final float b = 0.75f;
	
	
	public static TreeMap <Double, String> getRanking(String [] query, HashMap <String, TreeMap <String, Integer>> invIndex, HashMap <String, Integer> docList, double avgdl){
		
		TreeMap <Double, String> rank = new TreeMap <Double, String>();
		double score = 0;
		for(String doc: docList.keySet()){
			score = 0;
			int dLength = docList.get(doc);
			
			for(String term: query){
				if(!invIndex.containsKey(term)) continue;
				if(!invIndex.get(term).containsKey(doc)) continue;
				int fqd = invIndex.get(term).get(doc);
			   score += (fqd*(k1+1))/(fqd+k1*(1-b+b*(dLength/avgdl)));
			}
			
		rank.put(score, doc);
		}
		
		return rank;
		
	}

}

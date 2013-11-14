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
		
		HashMap <String, Double> PtC = new HashMap <String, Double>();//key: term
        HashMap <String, HashMap<String,Double>> PtD = new HashMap <String, HashMap<String,Double>>();// key: document,
        //value: term and probability value
		double score = 0;
		
		double csum = 0; //occurrecies of all the terms in the collection
		HashMap<String,Double> qsum = new HashMap<String, Double>();
		
		for(String doc: docList.keySet()){
            double sum=0;
			for(String term: invIndex.keySet())
            {
				if(!invIndex.containsKey(term)) continue;
				if(!invIndex.get(term).containsKey(doc)) continue;
				int x = invIndex.get(term).get(doc);
				csum += x;
				sum += x;
			}
            qsum.put(doc,sum);
		}
        for(String doc: docList.keySet()){
            double sum=0;
            PtD.put(doc,new HashMap<String, Double>());
            for(String term: invIndex.keySet())
            {
                if(!invIndex.containsKey(term)) continue;
                if(!invIndex.get(term).containsKey(doc)) continue;
                int tf = invIndex.get(term).get(doc);
                double probabilityD=(double)tf/qsum.get(doc);
                double probabilityC=(double)tf/csum;
                PtC.put(term,probabilityC);
                PtD.get(doc).put(term,probabilityD);
            }
            qsum.put(doc,sum);
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

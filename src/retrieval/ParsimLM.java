package retrieval;

import java.util.*;
import java.util.HashMap;
import java.util.TreeMap;

import models.IQuery;

public class ParsimLM implements IRetrievalModel{
	
	private double lambda = 0.99;
    private HashMap <String, Double> PtC = new HashMap <String, Double>();//key: term
    private HashMap <String, HashMap<String,Double>> PtD = new HashMap <String, HashMap<String,Double>>();// key: document,
    //value: term and probability value
    private HashMap<String, HashMap<String,Double>> Et=new HashMap<String, HashMap<String,Double>>();// key: document,
    //value: term and probability value
    private HashMap<String, TreeMap<String, Integer>> invIndex=new HashMap<String, TreeMap<String, Integer>>();//key: term
    //value: doc and occurrences
    public static int maxNumberOfIterations=100;
    private static double threshold=0.0000001;
    private double currentMaxDiff=2*threshold;

	@Override
	public HashMap<String, Double> getRanking(IQuery queryObject,
			HashMap<String, TreeMap<String, Integer>> _invIndex,
			HashMap<String, Integer> docList, double avgdl) {


		
		double csum = 0; //occurrences of all the terms in the collection
		HashMap<String,Double> qsum = new HashMap<String, Double>();
        this.invIndex=_invIndex;
		
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
            PtD.put(doc,new HashMap<String, Double>());
            Et.put(doc,new HashMap<String, Double>());
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
        }
        for(int i=0;i<maxNumberOfIterations;i++)
        {
            if(this.currentMaxDiff<threshold)
                break;
            this.CalculateEStep();
            this.CalculateMStep();
        }

		return MakeRanking(queryObject);
	}

    private HashMap<String,Double> MakeRanking(IQuery queryObject)
    {
        String [] query=queryObject.getQuery();
        HashMap<String,Double> result=new HashMap<String, Double>();
        double tempPtD,tempPtC;
        for(String doc:PtD.keySet())
        {
            double score=0;
            for(String term:query)
            {
                if(PtD.get(doc).containsKey(term))
                	tempPtD=PtD.get(doc).get(term);
                else
                	tempPtD=0;
                
                if(PtC.containsKey(term))
                	tempPtC=PtC.get(term);
                else
                	tempPtC=0;
               
                if(tempPtC!=0)
                	score+=(1 / (double)query.length) * Math.log( (1-lambda)*tempPtC+lambda*tempPtD) ;
            }
            if( score!=0 )
                result.put(doc, score);
        }
        HashMap<String,Double> cuttedResults=new HashMap<String,Double>();
        result=ParsimLM.sort(result);
        int iterator=0;
        for(String key:result.keySet())
        {
        	iterator++;
        	if(iterator<=100)
        		cuttedResults.put(key,result.get(key));
        }
        return ParsimLM.sort(cuttedResults);
    }

    private void CalculateEStep()
    {
        for(String doc:this.PtD.keySet())
        {
            for(String term:this.PtD.get(doc).keySet())
            {
                double result=this.invIndex.get(term).get(doc)*( lambda*PtD.get(doc).get(term) ) /
                        ( (1-lambda)*PtC.get(term)+lambda*PtD.get(doc).get(term) );
                this.Et.get(doc).put(term,result);
            }
        }
    }

    private void CalculateMStep()
    {
        double sum;
        double oldValue=0;
        double maxDif=0;
        for(String doc:this.Et.keySet())
        {
            sum=0;
            for(String term:this.Et.get(doc).keySet())
            {
                 sum+=this.Et.get(doc).get(term);
            }
            for(String term:this.Et.get(doc).keySet())
            {
                double newResult=Et.get(doc).get(term)/sum;
                if(this.PtD.get(doc).containsKey(term))
                    oldValue=this.PtD.get(doc).get(term);
                maxDif=Math.max(maxDif,Math.abs(oldValue-newResult));
                this.PtD.get(doc).put(term,newResult);
            }
        }
        this.currentMaxDiff=maxDif;
    }
    
    public static HashMap <String, Double> sort(HashMap <String, Double> unsortMap) {


        List list = new LinkedList(unsortMap.entrySet());



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

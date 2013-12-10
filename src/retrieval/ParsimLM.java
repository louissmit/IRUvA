package retrieval;

import java.util.HashMap;
import java.util.TreeMap;

import models.IQuery;

public class ParsimLM implements IRetrievalModel{
	
	private double lambda = 0.001;
    private HashMap <String, Double> PtC = new HashMap <String, Double>();//key: term
    private HashMap <String, HashMap<String,Double>> PtD = new HashMap <String, HashMap<String,Double>>();// key: document,
    //value: term and probability value
    private HashMap<String, HashMap<String,Double>> Et=new HashMap<String, HashMap<String,Double>>();// key: document,
    //value: term and probability value
    private HashMap<String, TreeMap<String, Integer>> invIndex=new HashMap<String, TreeMap<String, Integer>>();//key: term
    //value: doc and occurrences
    public static int maxNumberOfIterations=100;
    private static double threshold=0.0001;
    private double currentMaxDiff=2*threshold;

	@Override
	public HashMap<String, Double> getRanking(IQuery queryObject,
			HashMap<String, TreeMap<String, Integer>> _invIndex,
			HashMap<String, Integer> docList, double avgdl) {

		
		double csum = 0; //occurrecies of all the terms in the collection
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
            double sum=0;
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
            qsum.put(doc,sum);
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

        for(String doc:PtD.keySet())
        {
            double score=1.0;
            for(String term:query)
            {
                if(PtD.get(doc).containsKey(term)&&PtC.containsKey(term))
                    score+=(1 / (double)query.length) * Math.log( (1-lambda)*PtC.get(term)+lambda*PtD.get(doc).get(term) );
            }
            if(score!=1.0)
                result.put(doc, -score);
        }
        return BM25.sort(result);
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


}

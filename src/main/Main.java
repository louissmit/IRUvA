package main;

import java.util.HashMap;
import java.util.TreeMap;

import com.sun.corba.se.spi.monitoring.StatisticsAccumulator;

import models.IQuery;
import models.StringQuery;
import retrieval.BM25;
import retrieval.IRetrievalModel;
import retrieval.ParsimLM;
import retrieval.QueryProcessing;
import indexing.*;

public class Main {

	public static String docPath="src/Docs";
	
	public static void main(String[] args) {
		
		String line="ustainable ecosystems";
		String [] queryStrings= line.split(" ");
		String queryId="6";
		IQuery query6=new StringQuery(queryStrings,queryId);
		
		line="air guitar textile sensors";
		queryStrings= line.split(" ");
		queryId="7";
		IQuery query7=new StringQuery(queryStrings,queryId);
		
		IRetrievalModel retrievalModel=new BM25();
        IRetrievalModel retrievalModelLM=new ParsimLM();
		QueryProcessing queryProcessing=new QueryProcessing(retrievalModel, docPath);
        QueryProcessing queryProcessingLM=new QueryProcessing(retrievalModelLM, docPath);
		
		HashMap <String, Double> rank6 = queryProcessing.CalculateAndSaveToFileRank(query6, "output6.txt");
		HashMap <String, Double> rank7 = queryProcessing.CalculateAndSaveToFileRank(query7, "output7.txt");

        HashMap <String, Double> rank6LM = queryProcessingLM.CalculateAndSaveToFileRank(query6, "output6LM.txt");
        HashMap <String, Double> rank7LM = queryProcessingLM.CalculateAndSaveToFileRank(query7, "output7LM.txt");

		
		String ranking = "";
		for(String document: rank6.keySet()){
			ranking += "Document: "+document+ ", Score : "+ rank6.get(document) +"\n";
		}
		System.out.println("Statistic for query 6:");
		System.out.println(ranking);
		
		ranking = "";		
		for(String document: rank7.keySet()){
			ranking += "Document: "+document+ ", Score : "+ rank7.get(document) +"\n";
		}
		System.out.println("Statistic for query 7:");
		System.out.println(ranking);
		
		queryProcessing.PrintStatistics();
		
		
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

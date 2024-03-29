package main;

import java.util.HashMap;
import java.util.TreeMap;

import preprocess.Tokenizer;

import com.sun.corba.se.spi.monitoring.StatisticsAccumulator;

import evaluation.Evaluator;
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
		
		String query="sustainable ecosystems";

		String queryId="6";
		IQuery query6=new StringQuery(query,queryId);
		
		query="air guitar textile sensors";
		
		queryId="7";
		IQuery query7=new StringQuery(query,queryId);
		
		IRetrievalModel retrievalModel=new BM25();
        IRetrievalModel retrievalModelLM=new ParsimLM();
		QueryProcessing queryProcessing=new QueryProcessing(retrievalModel, docPath);
        QueryProcessing queryProcessingLM=new QueryProcessing(retrievalModelLM, docPath);
        System.out.println("Files Loaded");
		HashMap <String, Double> rank6 = queryProcessing.CalculateAndSaveToFileRank(query6, "output6.txt");
		HashMap <String, Double> rank7 = queryProcessing.CalculateAndSaveToFileRank(query7, "output7.txt");

        HashMap <String, Double> rank6LM = queryProcessingLM.CalculateAndSaveToFileRank(query6, "output6LM.txt");
        HashMap <String, Double> rank7LM = queryProcessingLM.CalculateAndSaveToFileRank(query7, "output7LM.txt");

        Evaluator eval = new Evaluator("qrels.txt");
       
		queryProcessing.PrintStatistics();

        System.out.println("Precision for query 6 for BM25, top20: "+eval.getPrecisionAt(20,query6.getQueryID(),rank6));
        System.out.println("Precision for query 6 for LM, top20: "+eval.getPrecisionAt(20,query6.getQueryID(),rank6LM));

        System.out.println("Precision for query 7 for BM25, top20: "+eval.getPrecisionAt(20,query7.getQueryID(),rank7));
        System.out.println("Precision for query 7 for LM, top20: "+eval.getPrecisionAt(20,query7.getQueryID(),rank7LM));
        System.out.println("Rankings of documents were saved to files");
		
	}

}

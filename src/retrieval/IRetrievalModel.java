package retrieval;

import java.util.HashMap;
import java.util.TreeMap;

import models.IQuery;

public interface IRetrievalModel {
	HashMap <String, Double> getRanking(IQuery queryObject, HashMap <String, TreeMap <String, Integer>> invIndex, HashMap <String, Integer> docList, double avgdl);
}

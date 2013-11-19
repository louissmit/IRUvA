package evaluation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: louissmit
 * Date: 11/17/13
 * Time: 11:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Evaluator {

    private HashMap<String, HashMap<String, Integer>> qrels;

    public Evaluator(String fileName) {
        this.qrels = parseQRels(fileName);
    }

    private HashMap<String, HashMap<String, Integer>> parseQRels(String fileName) {
        qrels = new HashMap<String, HashMap<String, Integer>>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                String queryId = words[0];
                String docName = words[2];
                int relevance = Integer.parseInt(words[3]);

                if(!qrels.containsKey(queryId)) {
                    HashMap<String, Integer> rels = new HashMap<String, Integer>();
                    rels.put(docName, relevance);
                    qrels.put(queryId, rels);
                } else {
                    qrels.get(queryId).put(docName, relevance);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return qrels;
    }

    public double getPrecisionAt(int k, String queryId, HashMap <String, Double> rank) {
        int i = 0;
        int relevant = 0;
        HashMap<String, Integer> qrelsForQ = qrels.get(queryId);
        for(String docName: rank.keySet()) {
            if(qrelsForQ.containsKey(docName) && qrelsForQ.get(docName) == 1) relevant++;
            i++;
            if(i == k) break;
        }
        return relevant / (double)k;
    }


}

package indexing;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import preprocess.Tokenizer;


public class Indexor {
	
	private HashMap <String, Integer> docList = new HashMap <String, Integer> (); // documentID, number of terms in the document;
	private HashMap <String, TreeMap <String, Integer>> invIndex = new HashMap <String, TreeMap <String, Integer>> ();
	HashMap <String, Integer> counts = null;
	private double avgLength = 0; // average lenght of the documents in the collecton
	
	public double getAvgLength() {
		return avgLength;
	}

	public void setAvgLength(double avgLength) {
		this.avgLength = avgLength;
	}

	public HashMap<String, Integer> getDocList() {
		return docList;
	}

	public void setDocList(HashMap<String, Integer> docList) {
		this.docList = docList;
	}

	public HashMap<String, TreeMap<String, Integer>> getInvIndex() {
		return invIndex;
	}

	public void setInvIndex(HashMap<String, TreeMap<String, Integer>> invIndex) {
		this.invIndex = invIndex;
	}

	public  HashMap <String, TreeMap <String, Integer>> makeIndex(String path){
		
	
		int sum = 0 ;//to calculate the average length
				
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		
		//System.out.println("Loaded "+listOfFiles.length+" files.");
	   
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	//System.out.println(file.getName());
		    	Tokenizer t = new Tokenizer();
		    	counts = t.tokenize(file.getName());
		    	
		    	sum += counts.size();
		    	
		    	String docName = file.getName().substring(0, file.getName().length() - 4);
		    	
		    	docList.put(docName, counts.size());
		    	
		    	for(String token : counts.keySet()){
			    	if(!invIndex.containsKey(token)){
			    		invIndex.put(token, new TreeMap <String, Integer> ());
			    		invIndex.get(token).put(docName, counts.get(token));
			    	}
			    	else{
			    		invIndex.get(token).put(docName, counts.get(token));
			    	}
			    }
		    }
		    
		    
		    
		}
		
		avgLength = sum / listOfFiles.length;
		
		/*for(String token: invIndex.keySet()){
			
			System.out.println(token+":");
			
			for (String doc : invIndex.get(token).keySet()){
				System.out.print(doc+":"+invIndex.get(token).get(doc)+", ");
			}
			
			System.out.println("");
			
		}*/
		
		return invIndex;
	}







}

package indexing;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import tokenize.Tokenizer;


public class Indexor {
	
	
	
	public static HashMap <String, TreeMap <String, Integer>> makeIndex(){
		
		
		HashMap <String, TreeMap <String, Integer>> invIndex = new HashMap <String, TreeMap <String, Integer>> ();
		HashMap <String, Integer> counts = null;
		
		File folder = new File("src/Docs");
		File[] listOfFiles = folder.listFiles();
	   
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	//System.out.println(file.getName());
		    	Tokenizer t = new Tokenizer();
		    	counts = t.tokenize(file.getName());
		    	
		    	String docName = file.getName().substring(0, file.getName().length() - 4);
		    	
		    	
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
		
		for(String token: invIndex.keySet()){
			
			System.out.println(token+":");
			
			for (String doc : invIndex.get(token).keySet()){
				System.out.print(doc+":"+invIndex.get(token).get(doc)+", ");
			}
			
			System.out.println("");
			
		}
		
		return invIndex;
	}







}

package preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;

public class Tokenizer {
	
	
	
	public HashMap <String, Integer> tokenize(String filename) {
		HashMap <String, Integer> list = new HashMap <String, Integer>(); 
		 File file = new File("src/Docs/"+filename);
		  ArrayList <String> lines = new ArrayList <String>();
		  
		  try {
		    FileReader reader = new FileReader(file);
		    BufferedReader buffReader = new BufferedReader(reader);
		    
		    String s;
		  
		    while((s = buffReader.readLine()) != null){
		    	s = Normalizer.normalize(s, Normalizer.Form.NFD); 
		    	s = s.replaceAll("[^\\p{ASCII}]", "");//removing diatrics
		        lines.add(s.toLowerCase());
		     
		    }
		    
		    buffReader.close();
		 }
		  catch(IOException e){
		    System.exit(0);
		}
		String OPERATORS = " \\\":()/<>?-_.:;,+*°€='";

        Preprocessor preprocessor = new Preprocessor();
		for (String st : lines) {
		    StringTokenizer tokens = new StringTokenizer(st, OPERATORS, true);
		    while (tokens.hasMoreTokens()) {
		        String token = tokens.nextToken();
                token = preprocessor.stem(token);
                if (!OPERATORS.contains(token)) {
		        	if(!list.containsKey(token)){
		        		list.put(token, 1);
		        	}
		        	else{
		        		list.put(token, list.get(token)+1);
		        	}
		        	
		        }
		            
		    }
		     }
		
		return list;
		   }

public static String[] tokenizeQuery(String query){
	
		LinkedList <String> list = new LinkedList<String>();
		
		query = Normalizer.normalize(query, Normalizer.Form.NFD); 
    	query = query.replaceAll("[^\\p{ASCII}]", "");
    	query = query.toLowerCase();
    	
		String OPERATORS = " \\\":()/<>?-_.:;,+*°€'!|�$%&*";
		StringTokenizer queryTokens = new StringTokenizer(query, OPERATORS, true);
		
		
		
		
		
		 while (queryTokens.hasMoreTokens()) {
		        String token = queryTokens.nextToken();
		        if (!OPERATORS.contains(token)){
		        	
		         list.add(token);
		        	
		        }
		            
		    }
		 
		 String[] res = new String[list.size()];
		 int i = 0;
		 for (String token: list){
			 res[i] = token;
			 i++;
		 }
		
		return res;
	}
	
}

package preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Tokenizer {
	
	Stemmer stemmer = new Stemmer();
	
	HashMap <String, Integer> list = new HashMap <String, Integer>(); 
	
	public HashMap <String, Integer> tokenize(String filename) {
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
		String OPERATORS = " \\\":()/<>?-_.:;,+*°€'";

		for (String st : lines) {
		    StringTokenizer tokens = new StringTokenizer(st, OPERATORS, true);
		    while (tokens.hasMoreTokens()) {
		        String token = tokens.nextToken();
		        if (OPERATORS.contains(token))
		        {}
		        else{
		        	stemmer = new Stemmer();
		        	stemmer.add(token.toCharArray(), token.length());
		        	stemmer.stem();
		        	
		        	token = stemmer.toString();
		        	
		        	
		        	
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


	
}

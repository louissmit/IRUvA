/**
 * 
 */
package preprocess;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * @author louissmit
 *
 */
public class Preprocessor {
	Stemmer stemmer;
	LinkedList <String> list = new LinkedList <String>(); 
	
	
	public String[] preprocessQuery(String query){
		
		query = Normalizer.normalize(query, Normalizer.Form.NFD); 
    	query = query.replaceAll("[^\\p{ASCII}]", "");
    	query = query.toLowerCase();
    	
		String OPERATORS = " \\\":()/<>?-_.:;,+*°€'";
		StringTokenizer queryTokens = new StringTokenizer(query, OPERATORS, true);
		
		
		
		
		
		 while (queryTokens.hasMoreTokens()) {
		        String token = queryTokens.nextToken();
		        if (OPERATORS.contains(token))
		        {}
		        else{
		        	stemmer = new Stemmer();
		        	stemmer.add(token.toCharArray(), token.length());
		        	stemmer.stem();
		        	
		        	token = stemmer.toString();
		        	
		        	
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

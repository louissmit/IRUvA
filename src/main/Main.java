package main;

import java.util.HashMap;
import java.util.TreeMap;

import indexing.Indexor;

public class Main {

	
	public static void main(String[] args) {
		
		HashMap <String, TreeMap <String, Integer>> invIndex = Indexor.makeIndex();
		
	for(String token: invIndex.keySet()){
			
			System.out.println(token+":");
			
			for (String doc : invIndex.get(token).keySet()){
				System.out.print(doc+":"+invIndex.get(token).get(doc)+", ");
			}
			
			System.out.println("");
			
		}
		
	}

}

package topcoder.bfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class TransformWord {
	
	public static List<String> transform(String startWord, String stopWord, Set<String> dictionary) {
		Queue<String> queue = new LinkedList<String>();
		HashSet<String> set = new HashSet<String>();
		HashMap<String, String> map = new HashMap<String, String>();
		List<String> result = new ArrayList<String>();
		
		queue.offer(startWord);
		set.add(startWord);
		
		while(!queue.isEmpty()) {
			String currentWord = queue.poll();
			
			if(currentWord.equals(stopWord)) {
				getResult(map, stopWord, startWord, result);
				return result;
			}
						
			for(int i=0; i<startWord.length(); i++) {
				char[] newWord = currentWord.toCharArray();
								
				for(char c = 'A'; c <= 'Z'; c++) {
					if(c == newWord[i])
						continue;
					
					newWord[i] = c;
					String newWordStr = new String(newWord);
					
					if(dictionary.contains(newWordStr) && !set.contains(newWordStr)) {
						queue.offer(newWordStr);
						map.put(newWordStr, currentWord);
						set.add(newWordStr);						
					}
				}
			}				
		}
		
		return result;
	}
	
	public static void getResult(HashMap<String, String> map, String word, String startWord, List<String> result) {
		if(word.equals(startWord)) 
			result.add(startWord);
		else {
			getResult(map, map.get(word), startWord, result);
			result.add(word);
		}
	}
	
	public static void main(String[] args) {		
		String start = "DAMP";
		String finish = "LIKE";
		HashSet<String> dictionary = new HashSet<String>();
		dictionary.add("DAMP");
		dictionary.add("LAMP");
		dictionary.add("LIMP");
		dictionary.add("LIME");
		dictionary.add("LIKE");
		
		List<String> list = transform(start, finish, dictionary);
		for(String s : list)
			System.out.println(s);
	}
}

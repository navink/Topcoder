/*
The toy company "I Can't Believe It Works!" has hired you to help develop educational toys. The current project is a word toy that displays four letters at all times. Below each letter are two buttons that cause the letter above to change to the previous or next letter in alphabetical order. So, with one click of a button the letter 'c' can be changed to a 'b' or a 'd'. The alphabet is circular, so for example an 'a' can become a 'z' or a 'b' with one click. 

In order to test the toy, you would like to know if a word can be reached from some starting word, given one or more constraints. A constraint defines a set of forbidden words that can never be displayed by the toy. Each constraint is formatted like "X X X X", where each X is a string of lowercase letters. A word is defined by a constraint if the ith letter of the word is contained in the ith X of the contraint. For example, the constraint "lf a tc e" defines the words "late", "fate", "lace" and "face". 
*/

package topcoder.bfs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SmartWordToy {

	public static int minPresses(String start, String finish, String[] forbid) {
		int minPresses = -1;
		Queue<String> queue = new LinkedList<String>();
		HashSet<String> set = new HashSet<String>();
		HashMap<String, String> map = new HashMap<String, String>();
		
		queue.offer(start);
		set.add(start);
		
		while(!queue.isEmpty()) {
			String currentWord = queue.poll();
			
			if(currentWord.equals(finish)) {
				minPresses = getMinPresses(map, start, finish);
				return minPresses;
			}
						
			for(int i=0; i<4; i++) {
				char[] newWord1 = currentWord.toCharArray();
				char[] newWord2 = currentWord.toCharArray();
				
				int nextChar = currentWord.charAt(i) + 1;
				int prevChar = currentWord.charAt(i) - 1;
				
				if(nextChar > 'z')
					nextChar = 'a';
				
				if(prevChar < 'a')
					prevChar = 'z';
				
				newWord1[i] = (char) nextChar;
				newWord2[i] = (char) prevChar;
								
				String newWord1Str = new String(newWord1);
				String newWord2Str = new String(newWord2);
								
				if(isValid(newWord1, forbid) && !set.contains(newWord1Str)) {
					queue.offer(newWord1Str);
					map.put(newWord1Str, currentWord);
					set.add(newWord1Str);					
				}
				
				if(isValid(newWord2, forbid) && !set.contains(newWord2Str)) {
					queue.offer(newWord2Str);
					map.put(newWord2Str, currentWord);
					set.add(newWord2Str);					
				}
			}
		}
		
		return minPresses;
	}

	private static int getMinPresses(HashMap<String, String> map, String start, String finish) {
		String currentWord = finish;
		int minPresses=0;
		
		while(!currentWord.equals(start)) {
			currentWord = map.get(currentWord);
			minPresses++;			
		}
		
		return minPresses;
	}

	private static boolean isValid(char[] word, String[] forbid) {
		
		for(String f:forbid) {
			String[] forbidStr = f.split(" ");
			int i;
			
			for(i=0; i<4; i++) {
				if(forbidStr[i].indexOf(word[i]) == -1)
					break;
			}
			
			if(i==4)
				return false;
		}
		
		return true;
	}

	public static void main(String[] args) {		
		String start = "aaaa";
		String finish = "zzzz";
		String[] forbid = {"cdefghijklmnopqrstuvwxyz a a a", 
				 "a cdefghijklmnopqrstuvwxyz a a", 
				 "a a cdefghijklmnopqrstuvwxyz a", 
				 "a a a cdefghijklmnopqrstuvwxyz"};
		System.out.println(minPresses(start, finish, forbid));
	}
}

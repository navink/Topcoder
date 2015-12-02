package topcoder.greedy;

import java.util.HashMap;
import java.util.Map;

public class GroceryBagger {
	int minimumBags(int strength, String[] itemType) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int minBags = 0;
		
		for (String item : itemType) {
			int count = map.containsKey(item) ? map.get(item) : 0;
			map.put(item, count + 1);
		}
				
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			int totalBags = (int) Math.ceil((((double)entry.getValue())/strength));
			minBags += totalBags;
		}
		
		return minBags;
	}
	
	public static void main(String[] args) {
		GroceryBagger gb = new GroceryBagger();		
		String[] itemType = {"CANNED",   "CANNED",  "PRODUCE",
				 "DAIRY",    "MEAT",    "BREAD",
				 "HOUSEHOLD","PRODUCE", "FROZEN",
				 "PRODUCE", "DAIRY"};
		System.out.println("Min. Bags: " + gb.minimumBags(5, itemType));
	}
}

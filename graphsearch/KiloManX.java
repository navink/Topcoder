// http://community.topcoder.com/stat?c=problem_statement&pm=2288&rd=4725

package topcoder.graphsearch;

import java.util.PriorityQueue;
import java.util.Queue;

public class KiloManX {
	
	private static class Node implements Comparable<Node> {
		public int weapons;
		public int shots;
		
		public Node(int weapons, int shots) {
			this.weapons = weapons;
			this.shots = shots;
		}
		
		public int compareTo(Node o) {
			return this.shots == o.shots? 0 : this.shots > o.shots ? 1 : -1;			
		}		
	}
	
	public int leastShots(String[] damageChart, int[] bossHealth) {
		Queue<Node> pq = new PriorityQueue<Node>();
		boolean[] visited = new boolean[32768];		
		pq.add(new Node(0, 0));
		int numWeapons = damageChart.length;
		
		while(!pq.isEmpty()) {
			Node current = pq.remove();
			
			if(visited[current.weapons])
				continue;
			
			visited[current.weapons] = true;
			// A quick trick to check if we have all the weapons, meaning we defeated all the bosses.
			// We use the fact that (2^numWeapons - 1) will have all the numWeapons bits set to 1.
			if (current.weapons == (1 << numWeapons) - 1)
				return current.shots;
			
			for (int i = 0; i < damageChart.length; i++) {
				// Check if we've already visited this boss, then don't bother trying him again
				if (((current.weapons >> i) & 1) == 1) 
					continue;

				// Now figure out what the best amount of time that we can destroy this boss is, given the weapons we have.
				// We initialize this value to the boss's health, as that is our default (with our KiloBuster).
				int best = bossHealth[i];
				
				for (int j = 0; j < damageChart.length; j++) {
				    if (i == j) continue;
				    
				    if ((((current.weapons >> j) & 1) == 1) && damageChart[j].charAt(i) != '0') {
				    	// We have this weapon, so try using it to defeat this boss
				    	int shotsNeeded = bossHealth[i] / (damageChart[j].charAt(i) - '0');
				    	
				    	if (bossHealth[i] % (damageChart[j].charAt(i) - '0') != 0) shotsNeeded++;
				    		best = Math.min(best, shotsNeeded);
				    }
				}
				System.out.print(Integer.toBinaryString(current.weapons | (1 << i)));
				System.out.print(" : ");
				System.out.println(current.shots + best);
				// Add the new node to be searched, showing that we defeated boss i, and we used 'best' shots to defeat him.
				pq.add(new Node((current.weapons | (1 << i)), current.shots + best));
			}
		}
		
		return Integer.MAX_VALUE;
	}
	
	public static void main(String[] args) {
		KiloManX kmx = new KiloManX();
		String[] damageChart = {"070","500","140"};
		int[] bossHealth = {150,150,150};
		
		System.out.println(kmx.leastShots(damageChart, bossHealth));
	}
}

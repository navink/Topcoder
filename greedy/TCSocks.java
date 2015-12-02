/*
You wish to earn money selling cute socks from the TC store. You live in Glastonbury, but its citizens are all long-time TopCoders, so you can't sell any socks there. Thus you are going to visit several other cities, find new TopCoders there, and sell them some socks. Unfortunately, several other TC members are also sock salesmen. If one or more sock salesmen visits a city before you, or at the same time as you, your profit in that city will be halved once for each of the other salesmen (because some people will buy his socks). Hence, if two people visit the city before you, you will only get one fourth of the potential profit. Also, travelling between cities is not free, so you may lose money if you visit too many cities. However, you have your competitors' plans, so you know which cities they will visit, and in what order they will visit them. Now you are planning your route, and want to maximize your profit. 
*/
package topcoder.greedy;

public class TCSocks {	
	int[] money;
	int[][] tt;
	int[][] cc;
	int[][] prev;
	
	int earnMoney(int[] money, String[] cost, String[] time, String[] competitors) {
		int n = cost.length;
		int t = 100;
		boolean[] visited = new boolean[n];
		tt = new int[n][n];
		cc = new int[n][n];
		this.money = money;
		
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {		 
				tt[i][j] = Integer.parseInt(time[i].split("\\s")[j]);
				cc[i][j] = Integer.parseInt(cost[i].split("\\s")[j]);			
			}
		}
		
		prev = new int[t][n];
		
		for (int i=0; i<competitors.length; i++) {			
			int city = 0;
			int totalTime=0;
			
			String[] cities = competitors[i].split("\\s");
			
			for(String s : cities) {
				int next=0;
								
				next = Integer.parseInt(s);
				totalTime += tt[city][next];
				city = next;
				
				prev[totalTime][next]++;
			}
		}
		
		for(int j=1; j<t; j++) {
			for (int i=0; i<n; i++) {
				prev[j][i] += prev[j-1][i];
			}			 
		}
		
		int maxProfit = recurse(visited, 0, 0, n);
		
		return maxProfit;
	}
	
	int recurse(boolean[] visited, int cur, int tm, int n){
        int maxProfit = (money[cur]>>prev[tm][cur])-cc[cur][0];
        
        for (int i=1; i<n; i++) {
            if(!visited[i]){
                visited[i] = true;
                int profit = recurse(visited,i,tm+tt[cur][i], n);
                maxProfit = Math.max(maxProfit, (profit + (money[i]>>prev[tm][cur]) - cc[cur][i]));
                visited[i] = false;
            }
        }
        return maxProfit;
    }
	
	public static void main(String[] args) {
		TCSocks tcs = new TCSocks();		
		int[] money = {0, 100, 100, 100};
		String[] cost = {"0 50 50 200", "0 0 50 200", "0 10 0 200", "0 0 0 0"};
		String[] time = {"0 1 1 1", "1 0 1 1", "1 1 0 1", "1 1 1 0"};
		String[] competitors = {"3", "2 3 1", "2 1"};
		
		System.out.println("Total Income: " + tcs.earnMoney(money, cost, time, competitors));
	}
}

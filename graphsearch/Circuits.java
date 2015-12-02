/*
 	An essential part of circuit design and general system optimization is critical path analysis. On a chip, the critical path represents the longest path any signal would have to travel during execution. In this problem we will be analyzing chip designs to determine their critical path length. The chips in this problem will not contain any cycles, i.e. there exists no path from one component of a chip back to itself.

	Given a String[] connects representing the wiring scheme, and a String[] costs representing the cost of each connection, your method will return the size of the most costly path between any 2 components on the chip. In other words, you are to find the longest path in a directed, acyclic graph. Element j of connects will list the components of the chip that can be reached directly from the jth component (0-based). Element j of costs will list the costs of each connection mentioned in the jth element of connects. As mentioned above, the chip will not contain any cyclic paths. For example:
	connects = {"1 2",
				"2",
				""}
	costs    = {"5 3",
				"7",
				""}
	In this example, component 0 connects to components 1 and 2 with costs 5 and 3 respectively. Component 1 connects to component 2 with a cost of 7. All connections mentioned are directed. This means a connection from component i to component j does not imply a connection from component j to component i. Since we are looking for the longest path between any 2 components, your method would return 12.
*/

package topcoder.graphsearch;

public class Circuits {
	int n;
	int[][] graph;
	
	public int howLong(String[] connects, String[] costs) {
		n= connects.length;
		graph = new int[n][n];
		
		for(int i=0; i<n; i++) {
			String[] nodesIndex = connects[i].split("\\s");
			String[] nodesCosts = costs[i].split("\\s");
			
			for(int j=0; j<nodesIndex.length; j++) {
				if(nodesIndex[j] != "") {
					int b = Integer.parseInt(nodesIndex[j]);
					int cost = Integer.parseInt(nodesCosts[j]);
					graph[i][b] = cost;
				}
			}
		}
		
		int maxPath = 0;
		for(int i=0; i<n; i++) {
			int pathLen=0;
			pathLen = dfs(i);
			
			if(pathLen > maxPath)
				maxPath = pathLen;			
		}
		
		return maxPath;
	}
	
	public int dfs(int index) {
		int pathLen=0;
		int maxPathLen=0;
		
		for(int i=0; i<n; i++) {
			if(graph[index][i] != 0)
				pathLen = graph[index][i] + dfs(i);
			
			if(pathLen > maxPathLen)
				maxPathLen = pathLen;
		}
		
		return maxPathLen;
	}
	
	public static void main(String[] args) {
		Circuits c = new Circuits();
		String[] connects = {"","2 3","3 4 5","4 6","5 6","7","5 7",""};
		String[] costs = {"","30 50","19 6 40","12 10","35 23","8","11 20",""};
		System.out.println("Longest Path: " + c.howLong(connects, costs));
	}
}

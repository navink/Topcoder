/*
	You are arranging a weird game for a team building exercise. In this game there are certain locations that people can stand at, and from each location there are paths that lead to other locations, but there are not necessarily paths that lead directly back. You have everything set up, but you need to know two important numbers. There might be some locations from which every other location can be reached. There might also be locations that can be reached from every other location. You need to know how many of each of these there are.
*/
	
package topcoder.graphsearch;

public class TeamBuilder {		
	public int[] specialLocations(String[] paths) {
		int n = paths.length;
		int[][] w = new int[n][n];
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(paths[i].charAt(j) == '1')
					w[i][j] = 1;
			}
		}
		
		for(int k=0; k<n; k++) {
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					if(w[i][k]==1 && w[k][j]==1)
						w[i][j] = 1;
				}
			}
		}
		
		int numFromEveryLoc = 0;
		int numToEveryLoc = 0;
		boolean noMatch = false;
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(i!=j && w[i][j] != 1) {
					noMatch = true;
					break;			
				}
			}
			if(!noMatch)
				numFromEveryLoc++;
			
			noMatch = false;
		}
		
		noMatch = false;
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(i!=j && w[j][i] != 1) {
					noMatch = true;
					break;			
				}
			}
			if(!noMatch)
				numToEveryLoc++;
			
			noMatch = false;
		}
		
		int[] result = {numFromEveryLoc, numToEveryLoc};
		
		return result;
	}
	
	public static void main(String[] args) {
		TeamBuilder tb = new TeamBuilder();
		String[] paths = {"0010","1000","1100","1000"};
		int[] results = tb.specialLocations(paths);		
		
		for(int result : results) {
			System.out.println(result);
		}
	}
}

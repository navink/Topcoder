/*
You are standing at the corner with coordinates 0,0. Your destination is at corner width,height. You will return the number of distinct paths that lead to your destination. Each path must use exactly width+height blocks. In addition, the city has declared certain street blocks untraversable. These blocks may not be a part of any path. You will be given a String[] bad describing which blocks are bad. If (quotes for clarity) "a b c d" is an element of bad, it means the block from corner a,b to corner c,d is untraversable. For example, let's say
width  = 6
length = 6
bad = {"0 0 0 1","6 6 5 6"}
*/

package topcoder.dynamic;

public class AvoidRoads {
	
	static final int INT_MAX = 100;	
	String[] bad;
	long[][] cache = new long[INT_MAX][INT_MAX];
	
	public long numWays(int width, int height, String[] bad) {		
		this.bad = bad;
		long[][] grid = new long[width+1][height+1];
		grid[0][0] = 1;
		
		for(int x = 0; x<=width; x++) {
			for(int y = 0; y<=height; y++){
				if((x-1 >=0) && !blocked(x-1,y,x,y))
					grid[x][y]+=grid[x-1][y];
				
				if((y-1 >=0) && !blocked(x,y-1,x,y))
					grid[x][y]+=grid[x][y-1];
			}
		}
		
		return grid[width][height];		
	}
	
	public boolean blocked(int x1, int y1, int x2, int y2) {
		
		for(String s : bad) {
			String[] coords = s.split("\\s");
			
			if(Integer.parseInt(coords[0]) == x1 && Integer.parseInt(coords[1]) == y1 
					&& Integer.parseInt(coords[2]) == x2 && Integer.parseInt(coords[3]) == y2)
				return true;
			
			if(Integer.parseInt(coords[0]) == x2 && Integer.parseInt(coords[1]) == y2 
					&& Integer.parseInt(coords[2]) == x1 && Integer.parseInt(coords[3]) == y1)
				return true;
		}
		
		return false;
	}
	
	long ways(int x, int y){
		if(x==0&&y==0)return 1;
		if(x<0 || y<0) return 0;
		
		if(cache[x][y] != 0)return cache[x][y];
		long ret = 0;
		if(!blocked(x-1,y,x,y))ret+=ways(x-1,y);
		if(!blocked(x,y-1,x,y))ret+=ways(x,y-1);
		cache[x][y] = ret;
		return ret;
	}
	
	long waysRecursive(int x, int y, String[] bad){
		this.bad = bad;
		
		return ways(x, y);
	}
	
	public static void main(String[] args) {
		AvoidRoads ar = new AvoidRoads();
		String[] bad = {"0 0 0 1","6 6 5 6"};
		
		System.out.print(ar.numWays(6, 6, bad));
		//System.out.print(ar.waysRecursive(6, 6, bad));
	}
}

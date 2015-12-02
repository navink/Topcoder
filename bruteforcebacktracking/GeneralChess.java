/*
You are given some knights (at most 8), with their positions on the table (-10000<=x, y<=10000). 
You need to find all positions to place another one, so that it threatens all given pieces.
*/

package topcoder.bruteforcebacktracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class GeneralChess {
	public static final int INT_MAX = 10000;
	public static final int INT_MIN = -10000;
	
	public Set<String> attackPositions(String[] pieces) {
		Set<String> result = new HashSet<String>();
		for(String s:pieces) {
			if(result.size() == 0)
				result = getKnightAttackCells(s);
			else
				result.retainAll(getKnightAttackCells(s));
		}
		
		return result;
	}
	
	public Set<String> getKnightAttackCells(String s) {
		Set<String> result = new TreeSet<String>();
		String[] coords = s.split(",");
		int x = Integer.parseInt(coords[0]);
		int y = Integer.parseInt(coords[1]);
		List<Tuple<Integer, Integer>> knightmoves = createListOfKnightMoves();
		
		for(Tuple<Integer, Integer> move : knightmoves) {
			int nextx = x + move.x;
			int nexty = y + move.y;
			
			if(nextx <= INT_MAX && nextx >= INT_MIN && nexty <= INT_MAX && nexty >= INT_MIN)
				result.add(nextx + "," + nexty);
		}
		
		return result;
	}
	
	private List<Tuple<Integer, Integer>> createListOfKnightMoves() {		
		Tuple<Integer, Integer> t1 = new Tuple<Integer, Integer>(2,1);
		Tuple<Integer, Integer> t2 = new Tuple<Integer, Integer>(2,-1);
		Tuple<Integer, Integer> t3 = new Tuple<Integer, Integer>(1,2);
		Tuple<Integer, Integer> t4 = new Tuple<Integer, Integer>(1,-2);
		Tuple<Integer, Integer> t5 = new Tuple<Integer, Integer>(-2, 1);
		Tuple<Integer, Integer> t6 = new Tuple<Integer, Integer>(-2,-1);
		Tuple<Integer, Integer> t7 = new Tuple<Integer, Integer>(-1,2);
		Tuple<Integer, Integer> t8 = new Tuple<Integer, Integer>(-1,-2);
		
		List<Tuple<Integer, Integer>> result = new ArrayList<Tuple<Integer, Integer>>();
		result.add(t1);
		result.add(t2);
		result.add(t3);
		result.add(t4);
		result.add(t5);
		result.add(t6);
		result.add(t7);
		result.add(t8);
		
		return result;
	}
	
	public static void main(String[] args) {
		GeneralChess gc = new GeneralChess();
		String[] pieces = {"-1000,1000", "-999,999", "-999,997"}; 
		Set<String> result = gc.attackPositions(pieces);
				
		for(String s : result) {
			System.out.println(s);
		}
	}
	
	public class Tuple<X, Y> { 
	    public final X x; 
	    public final Y y; 
	    public Tuple(X x, Y y) { 
	        this.x = x; 
	        this.y = y; 
	    }

	    @Override
	    public String toString() {
	        return "(" + x + "," + y + ")";
	    }

	    @Override
	    public boolean equals(Object other) {
	        if (other == null) {
	            return false;
	        }
	        if (other == this) {
	            return true;
	        }
	        if (!(other instanceof Tuple)){
	            return false;
	        }
	        Tuple<X,Y> other_ = (Tuple<X,Y>) other;
	        return other_.x == this.x && other_.y == this.y;
	    }

	    @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((x == null) ? 0 : x.hashCode());
	        result = prime * result + ((y == null) ? 0 : y.hashCode());
	        return result;
	    }
	}
}

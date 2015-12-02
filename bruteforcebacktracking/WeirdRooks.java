/*
On a weird chess board, each row can have a different number of columns. Element k of cols will give the number of columns in row k. Each row is flush left, so the right side can look quite ragged. In a valid assignment of n rooks to the weird chess board, no two rooks can share a row or column. In such an assignment, an unoccupied square is considered special if there is no rook to its right in the same row and no rook below in the same column (element 0 of cols describes the highest row). You are going to return a String containing a single-space delimited list of pairs. The pair (quotes for clarity) "r,f" should appear in the final string if and only if there is a valid assignment with r rooks such that f squares are special. The pairs should be sorted in nondecreasing order by r values. If a tie occurs, the lower f value should come first. The returned value should contain no repeated pairs. See the examples for further clarifications.
*/

package topcoder.bruteforcebacktracking;

import java.util.ArrayList;
import java.util.List;

public class WeirdRooks {
	
	final static int NMAX = 100;
	List<Integer> listSpecialSquares = new ArrayList<Integer>();
	
	public String describe(int[] cols) {
		int[] a = new int[NMAX];
		StringBuilder result = new StringBuilder();
		
		for (int rooks=0; rooks<cols.length; rooks++) {
			listSpecialSquares.clear();
			backtrack(a, rooks, 0, cols);
			
			for(int numSpSquare : listSpecialSquares) {
				result.append(rooks);
				result.append(",");
				result.append(numSpSquare);
				result.append(" ");
			}
		}
		return result.toString();
	}
	
	public void backtrack(int[] a, int n, int k, int[] data) {
		int[] c = new int[NMAX];
		int numCandidates;
		
		if(is_a_solution(a, n, k, data)) {
			process_solution(a, n, k, data);
		}
		else {
			k = k+1;
			numCandidates = construct_candidates(a, c, n, k, data);
			
			for(int i=1; i<=numCandidates; i++) {
				a[k] = c[i];
				backtrack(a, n, k, data);
			}
		}
	}
	
	private boolean is_a_solution(int[] a, int n, int k, int[] data) {
		return n == k;
	}
	
	private void process_solution(int a[], int n, int k, int[] data)
	{
		int specialSquares=0;
		int index=0;
		int rowIndex=0;
		
		for(int i=0; i<data.length; i++) {
			rowIndex += data[i];
			for(int j=0; j<data[i]; j++) {				
				if(!rook_on_square(a, k, index) &&
				   !rook_right(a, k, index, j+1, data[i]) &&
				   !rook_down(a, k, rowIndex, j, i+1, data)) {
						specialSquares++;
				}
				index++;
			}			
		}
		
		if(!listSpecialSquares.contains(specialSquares))
			listSpecialSquares.add(specialSquares);
	}
	
	private boolean rook_on_square(int a[], int k, int index) {
		for (int i=1; i<=k; i++) {
			if(a[i] == index)
				return true;			
		}
		return false;
	}
	
	private boolean rook_right(int a[], int k, int index, int start, int right) {		
		for(int j=start; j<=right; j++) {			
			if(rook_on_square(a, k, index))
				return true;
			
			index++;
		}
		
		return false;
	}
	
	private boolean rook_down(int a[], int k, int row, int col, int start, int[] data) {		
		int bottom = data.length-1;
		int index = row+col;
		
		for(int j=start; j<=bottom; j++) {			
			if(rook_on_square(a, k, index))
				return true;
			
			index = index + data[j-1];
		}
		
		return false;
	}

	private int construct_candidates(int a[], int c[], int n, int k, int[] data) {
		int ncandidates=0;
		int index=0;
		
		//Filled in 1 through k-1 already, now time to fill kth slot				
		for(int i=0; i<data.length; i++)
		{
			for(int j=0; j<data[i]; j++) {
				c[++ncandidates] = index++;
			}
		}
		
		return ncandidates;
	}
	
	public static void main(String[] args) {
		int[] cols = {3,3,3};
		WeirdRooks wr = new WeirdRooks();
		wr.describe(cols);
	}
}

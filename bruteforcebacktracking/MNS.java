/*
9 numbers need to be arranged in a magic number square. A magic number square is a square of numbers that is arranged such that every row and column has the same sum. For example:

1 2 3
3 2 1
2 2 2
Create a class MNS containing a method combos which takes as an argument a int[] numbers and returns the number of distinct ways those numbers can be arranged in a magic number square. Two magic number squares are distinct if they differ in value at one or more positions. For example, there is only one magic number square that can be made of 9 instances of the same number.
*/

package topcoder.bruteforcebacktracking;

import java.util.HashSet;
import java.util.Set;

public class MNS {
	
	final static int NMAX = 10;
	public int comboCount;
	public Set<String> perms = new HashSet<String>();
	
	public int combos(int[] numbers) {
		int[] a = new int[NMAX];
		backtrack(a, 9, 0, numbers);
		
		return comboCount;
	}
	
	public void backtrack(int[] a, int n, int k, int[] data) {
		int[] c = new int[NMAX];
		int numCandidates;
		
		if(is_a_solution(a, n, k, data)) {
			process_solution(a, n, k, data);
		}
		else {
			k = k+1;
			numCandidates = construct_candidates(a, c, n, k);
			
			for(int i=1; i<=numCandidates; i++) {
				a[k] = c[i];
				backtrack(a, n, k, data);
			}
		}
	}
	
	private boolean is_a_solution(int[] a, int n, int k, int[] data) {
		return n == k;
	}
	
	private void process_solution(int[] a, int n, int k, int[] data) {
		StringBuilder sb = new StringBuilder();
		
		if((data[a[1]-1]+data[a[2]-1]+data[a[3]-1] == data[a[4]-1]+data[a[5]-1]+data[a[6]-1]
			       && data[a[4]-1]+data[a[5]-1]+data[a[6]-1] == data[a[7]-1]+data[a[8]-1]+data[a[9]-1]
			       && data[a[1]-1]+data[a[4]-1]+data[a[7]-1] == data[a[2]-1]+data[a[5]-1]+data[a[8]-1]
			       && data[a[2]-1]+data[a[5]-1]+data[a[8]-1] == data[a[3]-1]+data[a[6]-1]+data[a[9]-1]))
		{
			for(int i=1; i<=n; i++) {
				sb.append(data[a[i]-1]);
				sb.append(" ");
			}
			
			if(!perms.contains(sb.toString())) {
				comboCount++;
				perms.add(sb.toString());
			}
		}
	}
	
	private int construct_candidates(int a[], int c[], int n, int k)
	{
		int ncandidates=0;
		boolean[] inPerm;
				
		inPerm = new boolean[10];
		
		//Filled in 1 through k-1 already, now time to fill kth slot
		for(int i=1; i<k; i++)
			inPerm[a[i]] = true;
		
		for(int i=1; i<=n; i++)
		{
			if(!inPerm[i])
			{
				c[++ncandidates] = i;				
			}
		}
		
		return ncandidates;
	}
	
	public static void main(String[] args) {
		int[] numbers = {1,2,3,3,2,1,2,2,2};
		MNS mns = new MNS();
		System.out.println(mns.combos(numbers));
	}
}

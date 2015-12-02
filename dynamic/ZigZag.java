/*
A sequence of numbers is called a zig-zag sequence if the differences between successive numbers strictly alternate between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence with fewer than two elements is trivially a zig-zag sequence. Given a sequence of integers, return the length of the longest subsequence that is a zig-zag sequence. A subsequence is obtained by deleting some number of elements (possibly zero) from the original sequence, leaving the remaining elements in their original order. Assume the sequence contains between 1 and 50 elements, inclusive.
*/

package topcoder.dynamic;

public class ZigZag {

	int[][] c;
	int[] predecessor;
	int[] highlow;
	
	int longestZigZag(int[] sequence) {
		c = new int[sequence.length][2];
		predecessor = new int[sequence.length];
		highlow = new int[sequence.length];
		int i=0;
		
		for(i=0; i<sequence.length; i++) {			
			c[i][0] = 1;
			c[i][1] = 1;
		}
		
		for(i=0; i<sequence.length; i++) {
			predecessor[i] = -1;		
			highlow[i] = -1;		
		}
		
		for(i=1; i<sequence.length; i++) {
			for(int j=0; j<i; j++) {		
				//When i is the increasing part of the subsequence
				if(sequence[i] > sequence[j]) {
					//The previous number is the decreasing part of the subsequence
					//c[i][0] = Math.max(c[i][0], c[j][1]+1);
					
					if(c[j][1]+1 >= c[i][0]) {
						c[i][0] = c[j][1]+1;
						predecessor[i] = j;
						highlow[i] = 1;
					}
				}
				
				//When i is the decreasing part of the subsequence
				if(sequence[i] < sequence[j]) {
					//The previous number is the increasing part of the subsequence
					//c[i][1] = Math.max(c[i][1], c[j][0]+1);
					
					if(c[j][0]+1 >= c[i][1]) {
						c[i][1] = c[j][0]+1;
						predecessor[i] = j;
						highlow[i] = 0;
					}
				}		
			}
		}
		
		int maxSeq = 1;
		int maxIndex1 = 0;
		int maxIndex2 = 0;
		
		for(int k=0; k<sequence.length; k++) {
			for(int l=0; l<2; l++) {
				if(c[k][l] > maxSeq) {
					maxSeq = c[k][l];
					maxIndex1 = k;
					maxIndex2 = l;
				}
			}
		}
		
		return maxSeq;
	}
		
	private void printSeq(int[] x, int index1, int index2) {
		if(index1==-1) {					
			return;
		}
		
		printSeq(x, predecessorhigh[index1], predecessorlow[index2]);
		System.out.print(x[index] + " ");
	}
	
	public static void main(String[] args) {
		ZigZag zz = new ZigZag();		
		int[] x = { 1, 17, 5, 10, 13, 15, 10, 5, 16, 8 };
		
		System.out.println("\nZigZag: " + zz.longestZigZag(x));		
	}
}

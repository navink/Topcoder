package topcoder.greedy;

import java.util.Arrays;

public class PlayGame {
	
	int saveCreatures(int[] you, int[] computer) {
		Arrays.sort(you);
		Arrays.sort(computer);
		int n = you.length;
		int total=0;
		int j=n-1;
		
		for(int i=n-1; i>=0; i--) {			
			for(; j>=0; j--) {
				if(you[i] > computer[j]) {
					total += you[i]; 
					j--;
					break;
				}
			}
		}
		
		return total;
	}
	
	public static void main(String[] args) {
		PlayGame pg = new PlayGame();		
		int[] you = {5, 15, 100, 1, 5};
		int[] computer = {5, 15, 100, 1, 5};
		
		System.out.println("Max. Total Strength: " + pg.saveCreatures(you, computer));
	}
}

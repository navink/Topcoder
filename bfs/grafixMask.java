/*
In one mode of the grafix software package, the user blocks off portions of a masking layer using opaque rectangles. The bitmap used as the masking layer is 400 pixels tall and 600 pixels wide. Once the rectangles have been blocked off, the user can perform painting actions through the remaining areas of the masking layer, known as holes. To be precise, each hole is a maximal collection of contiguous pixels that are not covered by any of the opaque rectangles. Two pixels are contiguous if they share an edge, and contiguity is transitive.
You are given a String[] named rectangles, the elements of which specify the rectangles that have been blocked off in the masking layer. Each String in rectangles consists of four integers separated by single spaces, with no additional spaces in the string. The first two integers are the window coordinates of the top left pixel in the given rectangle, and the last two integers are the window coordinates of its bottom right pixel. The window coordinates of a pixel are a pair of integers specifying the row number and column number of the pixel, in that order. Rows are numbered from top to bottom, starting with 0 and ending with 399. Columns are numbered from left to right, starting with 0 and ending with 599. Every pixel within and along the border of the rectangle defined by these opposing corners is blocked off.
Return a int[] containing the area, in pixels, of every hole in the resulting masking area, sorted from smallest area to greatest.
*/

package topcoder.bfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class grafixMask {

	public static List<Integer> sortedAreas(String[] rectangles) {
		List<Integer> result = new ArrayList<Integer>();
		HashSet<String> pixelSet = new HashSet<String>();
		for(int i=0; i<400; i++) {
			for(int j=0; j<600; j++) {
				pixelSet.add(String.valueOf(i) + " " + String.valueOf(j));
			}
		}
		
		for(String s:rectangles) {
			String[] coords = s.split(" ");
			int x1 = Integer.parseInt(coords[0]);
			int x2 = Integer.parseInt(coords[2]);
			int y1 = Integer.parseInt(coords[1]);
			int y2 = Integer.parseInt(coords[3]);
			
			for(int i=x1; i<=x2; i++) {
				for(int j=y1; j<=y2; j++) {
					pixelSet.remove(String.valueOf(i) + " " + String.valueOf(j));
				}
			}
		}
		
		while (!pixelSet.isEmpty()) {
			Queue<String> queue = new LinkedList<String>();				
			int pixelCount = 0;
			
			String start = getSetElement(pixelSet);
			queue.offer(start);			
			pixelSet.remove(start);
			pixelCount++;
			
			while(!queue.isEmpty()) {
				String currentCell = queue.poll();
				
				List<String> list = getNeighboringCells(currentCell, pixelSet);
				if(list.size() == 0)
					continue;
				
				for(String s : list) {				
						queue.offer(s);				
						pixelSet.remove(s);
						pixelCount++;
					}
				
			}
			result.add(pixelCount);
		}
		return result;
	}
	
	private static List<String> getNeighboringCells(String currentCell, HashSet<String> pixelSet) {
		List<String> neighbors = new ArrayList<String>();
		String[] coords = currentCell.split(" ");
		int x = Integer.parseInt(coords[0]);
		int y = Integer.parseInt(coords[1]);
		String nextCell;
				
		if(x<399) {
			nextCell = x+1 + " " + y;			
			if(pixelSet.contains(nextCell))
				neighbors.add(nextCell);
		}
		
		if(x>0) {
			nextCell = x-1 + " " + y;
			if(pixelSet.contains(nextCell))
				neighbors.add(nextCell);
		}
		
		if(y<599) {
			nextCell = x + " " + (y+1);
			if(pixelSet.contains(nextCell))
				neighbors.add(nextCell);
		}
		
		if(y>0) {
			nextCell = x + " " + (y-1);			
			if(pixelSet.contains(nextCell))
				neighbors.add(nextCell);
		}
		
		return neighbors;
	}

	public static String getSetElement(HashSet<String> pixelSet) {
		Iterator<String> it = pixelSet.iterator();
		
		if(it.hasNext())
			return it.next();
		else
			return null;
	}
	
	public static void main(String[] args) {
		String[] rectangles = {"0 192 399 207", "0 392 399 407", "120 0 135 599", "260 0 275 599"}; 
		List<Integer> result = sortedAreas(rectangles);
				
		for(Integer i : result) {
			System.out.println(i);
		}
	}
}

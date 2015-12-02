/*
Given a board consisting of empty space, walls, and the starting positions of two players A and B, determine the minimum number of turns it will take for players A and B to switch positions on the board.
During a turn, one or both players may take a step. A step is defined as a unit movement up, down, left, right, or in any of the four diagonals. Players may not step into walls or off the board. Players may never share the same square at the end of a turn. Players may not cross paths during a turn. Crossing paths occurs when players A and B switch positions in a single turn. For example, assume player A is in the upper left corner of the board, and player B is in the square immediately to his right. Player A may not move right while player B moves left, since they would be passing each other. Player A can, however, move right if player B moves in any other direction.
You will be given a String[] board, representing the game board. board will contain exactly one 'A' and exactly one 'B'; each other character will be either '.' (empty space), or 'X' (a wall). Your method should return the minimum number of turns necessary for players A and B to switch positions, or -1 if this is impossible.
*/

package topcoder.graphsearch;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class PathFinding {
	public class Node {
		int A_X;
		int A_Y;
		int B_X;
		int B_Y;
		int totalTurns;
		
		public Node(int ax, int ay, int bx, int by, int turns) {
			A_X = ax;
			A_Y = ay;
			B_X = bx;
			B_Y = by;
			totalTurns = turns;
		}
	}
	
	int A_StartX;
	int A_StartY;
	int B_StartX;
	int B_StartY;
	int maxNumRow;
	int maxNumCol;
	
	void getStartPos(String[] board) {
		boolean foundA=false, foundB=false;
		for(int i=0; i<board.length; i++) {
			String s = board[i];
			
			for(int j=0; j<s.length(); j++) {
				if(foundA && foundB)
					return;
				
				if(!foundA && s.charAt(j) == 'A') {
					A_StartX = i;
					A_StartY = j;
					foundA = true;
				}		
								
				if(!foundB && s.charAt(j) == 'B') {
					B_StartX = i;
					B_StartY = j;
					foundB = true;
				}				
			}
		}
	}
	
	int minTurns(String[] board) {
		getStartPos(board);		
		Node start = new Node(A_StartX, A_StartY, B_StartX, B_StartY, 0);		
		int n = board.length;
		maxNumRow = n;
		maxNumCol = board[0].length();
		
		Queue<Node> queue = new LinkedList<Node>();
		Set<String> visited = new HashSet<String>();
		queue.add(start);
		
		while (!queue.isEmpty()) {
			Node current = queue.remove();
			String currentState = current.A_X + "_" + current.A_Y + "_" + current.B_X + "_" + current.B_Y;
			
			if(visited.contains(currentState))
				continue;
			
			visited.add(currentState);
			
			if(current.A_X < 0 || current.A_Y < 0 || current.B_X < 0 || current.B_Y < 0)
				continue;
			
			if(current.A_X >= maxNumRow || current.B_X >= maxNumRow || current.A_Y >= maxNumCol || current.B_Y >= maxNumCol)
				continue;
			
			if(current.A_X == current.B_X && current.A_Y == current.B_Y)
				continue;
			
			if(board[current.A_X].charAt(current.A_Y) == 'X')
				continue;
			
			if(board[current.B_X].charAt(current.B_Y) == 'X')
				continue;
			
			if(current.A_X == start.B_X && current.A_Y == start.B_Y && current.B_X == start.A_X && current.B_Y == start.A_Y)
				return current.totalTurns;
			
			// Now we need to generate all of the transitions between nodes, we can do this quite easily using some
			// nested for loops, one for each direction that it is possible for one player to move.  Since we need
			// to generate the following deltas: (-1,-1), (-1,0), (-1,1), (0,-1), (0,0), (0,1), (1,-1), (1,0), (1,1)
			// we can use a for loop from -1 to 1 to do exactly that.
			for (int player1XDelta = -1; player1XDelta <= 1; player1XDelta++) {
				for (int player1YDelta = -1; player1YDelta <= 1; player1YDelta++) {
					for (int player2XDelta = -1; player2XDelta <= 1; player2XDelta++) {
						for (int player2YDelta = -1; player2YDelta <= 1; player2YDelta++) {
							// Careful though!  We have to make sure that player 1 and 2 did not swap positions on this turn
							if (current.A_X == current.B_X + player2XDelta && current.A_Y == current.B_Y + player2YDelta &&
									current.B_X == current.A_X + player1XDelta && current.B_Y == current.A_Y + player1YDelta)
								continue;

							// Add the new node into the queue
							queue.add(new Node(current.A_X + player1XDelta, current.A_Y + player1YDelta,
									current.B_X + player2XDelta, current.B_Y + player2YDelta,
									current.totalTurns + 1));
						}
					}
				}
			 }
				
		}
		
		return -1;
	}
	
	
	public static void main(String[] args) {
		PathFinding p = new PathFinding();
		String[] board = {"XXXXXXXXX",
						  "A.......B",
		 				  "XXXX.XXXX"};
		
		System.out.println("Min Turns to Swap Positions: " + p.minTurns(board));
	}
}

/*
 * 2002 TopCoder Invitational Semifinal Round 2 - Division I, Level Two
 * http://www.topcoder.com/stat?c=problem_statement&pm=1170&rd=4371
 */
 
package topcoder.graphsearch;

import java.util.PriorityQueue;
import java.util.Queue;
 
public class Escape {
 
        private static final byte HARMFUL = 1;
        private static final byte DEADLY = 2;
       
        private final byte[][] field = new byte[501][501];
        private final boolean[][] visited = new boolean[501][501];
       
        public int lowest(String[] harmful, String[] deadly) {
                assert 0 <= harmful.length && harmful.length <= 50;
                assert 0 <= deadly.length && deadly.length <= 50;
               
                fill(harmful, HARMFUL);
                fill(deadly, DEADLY);
                return lowestLife();
        }
       
        private static class Point implements Comparable<Point> {
                public int x;
                public int y;
                public int cost;
               
                public Point(int x, int y, int cost) {
                        this.x = x;
                        this.y = y;
                        this.cost = cost;
                }
               
                public int compareTo(Point o) {
                        return cost == o.cost ? 0 : ((cost > o.cost) ? 1 : -1);
                }
        }
       
        private static final int[][] MOVES = {
                { 1,  0},
                {-1,  0},
                { 0,  1},
                { 0, -1},
        };
       
        private int lowestLife() {
                Queue<Point> queue = new PriorityQueue<Point>();
               
                visited[0][0] = true;
                queue.add(new Point(0, 0, 0));
               
                while (!queue.isEmpty()) {
                        final Point p = queue.remove();
                        int cost = p.cost;
                        if (p.x == 500 && p.y == 500) return cost;
                       
                        for (int[] move : MOVES) {
                                int x = p.x + move[0], y = p.y + move[1];
                                if ((x < 0 || x > 500) || (y < 0 || y > 500))
                                        continue;
                                if (visited[x][y] || field[x][y] == DEADLY)
                                        continue;
                               
                                visited[x][y] = true;
                                queue.add(new Point(x, y, cost + field[x][y]));
                        }
                }
               
                return -1;
        }
 
        private void fill(String[] strings, byte type) {
                for (int i = 0; i < strings.length; i++) {
                        fill(strings[i], type);
                }
        }
 
        private void fill(String string, byte type) {
                String[] items = string.split(" ");
                assert items.length == 4;
                fill(
                        Integer.parseInt(items[0]),
                        Integer.parseInt(items[1]),
                        Integer.parseInt(items[2]),
                        Integer.parseInt(items[3]),
                        type);
        }
 
        private void fill(int x1, int y1, int x2, int y2, byte type) {
                assert 0 <= x1 && x1 <= 500;
                assert 0 <= y1 && y1 <= 500;
                assert 0 <= x2 && x2 <= 500;
                assert 0 <= y2 && y2 <= 500;
 
                final int column1 = Math.min(x1, x2);
                final int column2 = Math.max(x1, x2);
                final int row1 = Math.min(y1, y2);
                final int row2 = Math.max(y1, y2);
 
                for (int c = column1; c <= column2; c++) {
                        for (int r = row1; r <= row2; r++) {
                                field[c][r] = type;
                        }
                }
        }
        
        public static void main(String[] args) {
    		Escape esc = new Escape();
    		String[] harmful = {"0 0 250 250","250 250 500 500"};
    		String[] deadly = {"0 251 249 500","251 0 500 249"};
    		
    		System.out.println(esc.lowest(harmful, deadly));
    	}
}

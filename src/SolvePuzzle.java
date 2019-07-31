import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;


public class SolvePuzzle {
    
    
    private class Node { // node class
        private Puzzle puzzle;
        private int f, g, h;
        private Node prev;
        private Node next;
        
        public Node(Puzzle a, int moves, Node previous) {
            puzzle = new Puzzle(a);
            h = puzzle.manhattan();
            g = moves;
            prev = previous;
            next = null;
        }
        
        private Node[] moveDir(Node curr) { //branches from the current node
            Node m1, m2, m3, m4;
            m1 = new Node(curr.puzzle, 0, curr);
            m2 = new Node(curr.puzzle, 0, curr);
            m3 = new Node(curr.puzzle, 0, curr);
            m4 = new Node(curr.puzzle, 0, curr);
            
            m1.puzzle.movePiece(1);
            m2.puzzle.movePiece(2);
            m3.puzzle.movePiece(3);
            m4.puzzle.movePiece(4);
            
            Node allDirs[] = {m1, m2, m3, m4};
            return allDirs;
            
        }
        
    }
        
    public class compareCost implements Comparator<Node> { //comparator to compare f values of each puzzle board
        
        @Override
        public int compare(Node a, Node b) {
            if (a.f > b.f)
                return 1;
            if (a.f < b.f)
                return -1;
            return 0;
        }
        
    }

       public SolvePuzzle(Puzzle current) { //puzzle solving method with a* algorithm
           Comparator<Node> comparator = new compareCost();
           PriorityQueue<Node> openList = new PriorityQueue<>(100, comparator);
           ArrayList<Puzzle> closedList = new ArrayList<>();
           
           
           Node dirs[] = new Node[4];
           Node start_node = new Node(current, 0, null);
           Node end;
           end = null;
           openList.add(start_node);
           
           while (!openList.isEmpty()) {
                Node curr = openList.remove();
                dirs = curr.moveDir(curr);
                for (int i = 0; i < 4; i++) {
                    if (dirs[i] != null) {
                        if (dirs[i].h == 0) {
                            end = dirs[i];
                            break;
                        }
                        else {
                            if (!closedList.contains(dirs[i].puzzle)) {
                                dirs[i].g = curr.g + 1;
                                closedList.add(dirs[i].puzzle);
                                dirs[i].f = dirs[i].g + dirs[i].puzzle.manhattan();
                                openList.add(dirs[i]);
                            }
                        }
                    }
                }
                if (end != null)
                    break;
           }
           
           ArrayList<Puzzle> solveOrder = new ArrayList<>();
           while (end != null) {
                solveOrder.add(end.puzzle);
                end = end.prev;
            }
           
           Collections.reverse(solveOrder);
           
           for (int i = 0; i < solveOrder.size() - 1; i++) {
               solveOrder.get(i).print();
           }
           System.out.println("Solution length: " + solveOrder.size() + " moves");
           
       }
       
        public static void main(String[] args) {
            Scanner mc = new Scanner(System.in);
            int row = 0;
            int column = 0;
            System.out.println("Enter rows: ");
            row = mc.nextInt();
            System.out.println("Enter column: ");
            column = mc.nextInt();
            
            int [][] aPuzzle = new int[][]{ { 0, 1, 2},
                                            { 3, 4, 5},
                                            { 6, 7, 8} };
                                            
                                            
            ArrayList<ArrayList<Integer>> myList = new ArrayList<ArrayList<Integer>>();
            int fill = 0;
            
            for (int i = 0; i < row; i++) {
            	ArrayList<Integer> temp = new ArrayList<Integer>();
            	for (int j = 0; j < column; j++) {
            		temp.add(fill);
            		fill++;
            	}
            	myList.add(temp);
            }
            
            String pattern = "00";
            DecimalFormat dF = new DecimalFormat(pattern);
            
            
            System.out.println("+board_start+");
            for (int i = 0 ; i < myList.size(); i++) {
            	ArrayList<Integer> temp;
            	temp = myList.get(i);
            	for (int j = 0; j < temp.size(); j++) {
            		System.out.print("|");
            		System.out.print(dF.format(temp.get(j)));
            	}
            	System.out.print("|");
            	System.out.println("");
            }
            System.out.println("+board_end+");
            
            String t = mc.nextLine();
            
            Puzzle a = new Puzzle(aPuzzle);
            
            int shuffleLength = 0;
            shuffleLength = a.shuffle();
            SolvePuzzle b = new SolvePuzzle(a);
            
            System.out.println("Shuffle Length: " + shuffleLength + " moves");
    }    
    
}

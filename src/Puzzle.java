import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Puzzle {
    Random rand = new Random();
    private int [][] myPuzzle;
    ArrayList<ArrayList<Integer>> dynamicPuzzle;
    private int zeroLocationX = 0;
    private int zeroLocationY = 0;
    
    
    public Puzzle() { //default constructor
    }
    
    public Puzzle(int[][] np) { 
        myPuzzle = copyPuzzle(np);
    }
    
    public Puzzle(ArrayList<ArrayList<Integer>> np) {
        for (int i = 0; i < np.size(); i++) {
        	ArrayList<Integer> temp = new ArrayList<Integer>();
        	for (int j = 0; j < np.size(); j++) {
        		int cloneint = np.get(i).get(j);
        		temp.add(cloneint);
        	}
        	dynamicPuzzle.add(temp);
        }
    }
    
    public Puzzle(Puzzle copy) { //deep copy constructor
        this.myPuzzle = copy.copyPuzzle(copy.myPuzzle);
        this.zeroLocationX = copy.zeroLocationX;
        this.zeroLocationY = copy.zeroLocationY;
    }
    
    private int[][] copyPuzzle(int[][] a) { //method which allows a deep copy 
        int[][] copy = new int[3][3];
        for (int i = 0; i < a.length; i++) {
            copy[i] = Arrays.copyOf(a[i], a[i].length);
        }
        return copy;
    }
    
    public void print() { //print the puzzle
        System.out.println("+board+");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("|");
                System.out.print(myPuzzle[i][j]);           
            }
            System.out.print("|");
            System.out.println("");
        }
        System.out.println("+-----+");
    }
    
    public boolean moveableDirection(int movetype) { //check if a move is possible or if the empty tile is at a wall
        boolean canMove;
        if (movetype == 1 && zeroLocationX == 0) {
            canMove = false;
        }
        else if (movetype == 2 && zeroLocationX == 2) {
            canMove = false;
        }
        else if (movetype == 3 && zeroLocationY == 2) {
            canMove = false;
        }
        else if (movetype == 4 && zeroLocationY == 0) {
            canMove = false;
        }
        else   
            canMove = true;
            
        
        return canMove;
    }
            
    public boolean movePiece(int direction) { //executes a move after checking if the move is possible using moveableDirection method
        boolean canMove;
        canMove = moveableDirection(direction);
        
        if (canMove == true) {
            switch (direction) {
                case 1:
                    myPuzzle [zeroLocationX][zeroLocationY] = myPuzzle [zeroLocationX - 1][zeroLocationY];
                    myPuzzle [zeroLocationX - 1][zeroLocationY] = 0;
                    zeroLocationX--;
                    break;
                case 2:
                    myPuzzle [zeroLocationX][zeroLocationY] = myPuzzle [zeroLocationX + 1][zeroLocationY];
                    myPuzzle [zeroLocationX + 1][zeroLocationY] = 0;
                    zeroLocationX++;
                    break;
                case 3:
                    myPuzzle [zeroLocationX][zeroLocationY] = myPuzzle [zeroLocationX][zeroLocationY + 1];
                    myPuzzle [zeroLocationX][zeroLocationY + 1] = 0;
                    zeroLocationY++;
                    break;
                case 4:
                    myPuzzle [zeroLocationX][zeroLocationY] = myPuzzle [zeroLocationX][zeroLocationY  - 1];
                    myPuzzle [zeroLocationX][zeroLocationY  - 1] = 0;
                    zeroLocationY--;
                    break;
                default:
                    break;
            }   
            return true;
        }
        
        else {
            return canMove;
        }
    }

    public int regularH() { //returns the number of pieces out of place from their expected position
        int offset = 0;
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (counter != 0)
                    if (myPuzzle[i][j] != counter)
                        offset++;
                counter++;
            }
        }
        return offset;
    }
    
    public int manhattan() { //returns the manhattan distance of each piece from it's expected position
        int manhattanDist = 0;
        int current = 0;
        
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                current = myPuzzle[i][j];
                if (current != 0) {
                    int expY = current % 3;
                    int expX = current / 3;
                    int yOff = 0;
                    int xOff = 0;
                            
                    yOff = Math.abs(j - expY);
                    xOff = Math.abs(i - expX);
                    
                    manhattanDist = manhattanDist + xOff + yOff;
                }
            }      
         }
        return manhattanDist;
    
    }
    
    public int shuffle() { //shuffles the puzzle 
    	Scanner mc = new Scanner(System.in);
    	
        int shuffleCount = rand.nextInt(15) + 5;
        int shuffleCountR = shuffleCount;
        boolean success = false;
        int dir;
        System.out.println("Shuffling board:");
        print();
        
        while (shuffleCount > 0) {
            dir = rand.nextInt(4) + 1;
            success = movePiece(dir);
            if (success) {
                print();
                shuffleCount--;
            }
        }
        System.out.println("shuffle done, attempting to solve");
        
        return shuffleCountR;
    }
}

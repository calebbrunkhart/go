import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class App {

    static String[][] goBoard = {
        {null, null, null, " x", " o", null, null, null, null},
        {null, null, null, " x", null, null, null, null, null},
        {null, null, null, " x", " o", " o", " o", " o", " o"},
        {null, null, null, " x", " o", null, null, null, null},
        {null, null, null, " x", " o", null, null, null, null},
        {null, null, null, " x", " o", null, null, null, null},
        {null, null, null, " x", " o", null, null, null, null},
        {null, null, null, " x", " o", null, null, null, null},
        {null, null, null, " x", " o", null, null, null, null}
        };

    static boolean[][] territory = new boolean[9][9];
    static boolean[][] beenChecked = new boolean[9][9];

//This prints the board to the terminal
    static void getBoard(String[][] b){
        System.out.println("  0 1 2 3 4 5 6 7 8");
        for (int i = 0; i < b.length; i++){
            System.out.print(i);
            for (int j = 0; j < b[i].length; j++){                    
                if(b[i][j] == null){
                    System.out.print(" +");
                } else{
                    System.out.print(b[i][j]);
                }
            }
            System.out.println();
        }
    }

//Checking if a space has liberties
    static boolean hasLiberties(int x, int y) {
        if(goBoard[x][y] == null) return true;
        if(x+1 <= 8 && goBoard[x+1][y] == null) return true;
        if(x-1 >= 0 && goBoard[x-1][y] == null) return true;
        if(y+1 <= 8 && goBoard[x][y+1] == null) return true;
        if(y-1 >= 0 && goBoard[x][y-1] == null) return true;
        return false;
    }

    static boolean isAlive(int x, int y) {
        beenChecked[x][y] = true;

        if(hasLiberties(x, y) == true){
            return true;
        }

        if(x+1 <= 8 && goBoard[x+1][y].equals(goBoard[x][y])) {
            if(!beenChecked[x+1][y]) {
                if(isAlive(x+1, y)){
                    return true;
                }
            }
        }
        if(x-1 >= 0 && goBoard[x-1][y].equals(goBoard[x][y])) {
            if(!beenChecked[x-1][y]) {
                if(isAlive(x-1, y)){
                    return true;
                }
            }
        }
        if(y+1 <= 8 && goBoard[x][y+1].equals(goBoard[x][y])) {
            if(!beenChecked[x][y+1]) {
                if(isAlive(x, y+1)){
                    return true;
                }
            }
        }
        if(y-1 >= 0 && goBoard[x][y-1].equals(goBoard[x][y])) {
            if(!beenChecked[x][y-1]) {
                if(isAlive(x, y-1)){
                    return true;
                }
            }
        }

        return false;

    }

    static int checkTerritory(int x, int y, Set<String> borderingColors) {
        if (x < 0 || x > 8 || y < 0 || y > 8 || goBoard[x][y] != null || territory[x][y]) {
            return 0;
        }

        territory[x][y] = true;
        int areaCount = 1; // Initialize territory count

        // Check adjacent spaces and expand the territory
        if (x + 1 <= 8) {
            if (goBoard[x + 1][y] != null) borderingColors.add(goBoard[x + 1][y]);
            else areaCount += checkTerritory(x + 1, y, borderingColors);
        }
        if (x - 1 >= 0) {
            if (goBoard[x - 1][y] != null) borderingColors.add(goBoard[x - 1][y]);
            else areaCount += checkTerritory(x - 1, y, borderingColors);
        }
        if (y + 1 <= 8) {
            if (goBoard[x][y + 1] != null) borderingColors.add(goBoard[x][y + 1]);
            else areaCount += checkTerritory(x, y + 1, borderingColors);
        }
        if (y - 1 >= 0) {
            if (goBoard[x][y - 1] != null) borderingColors.add(goBoard[x][y - 1]);
            else areaCount += checkTerritory(x, y - 1, borderingColors);
        }

        return areaCount;
    }

    public static void main(String[] args) throws Exception {

        Scanner scn = new Scanner(System.in);
        Boolean quit = false;
        int moveX, moveY;
        Boolean flipper = true;

    //Values to track whether the game has ended. If both players pass in succession, the game ends.
        String pass; 
        int pass_count = 0;

    //White "O" starts with .5 points to ensure there is a winner. Black has an advantage of going first, so white gets the half point. This is called Komi.
        int playerXscore = 0;
        double playerOscore = 0.5;

        getBoard(goBoard);

        while(!quit){      
            System.out.println();

            boolean[][] dead = new boolean [9][9];
            
            if (pass_count == 2) {
                quit = true;
                continue;
            }

            System.out.println("Would you like to pass? (y for yes, n for no)");
            pass = scn.next();
            if (pass.equalsIgnoreCase("y")) {
                pass_count++;
                flipper = !flipper;
                continue;                    
            }
            else pass_count = 0;
 

            while (true){
                System.out.println(pass_count);
                if (flipper) {
                    System.out.println("Player 1's turn");
                }
                else {
                    System.out.println("Player 2's turn");
                }
                System.out.println();
                System.out.println("Please enter the x coordinate for your piece (1-9): ");
                moveX = scn.nextInt();
                System.out.println("Please enter the y coordinate for your piece (1-9): ");
                moveY = scn.nextInt();

                if ((moveX < 0 || moveX > 8 || moveY < 0 || moveY > 8)){
                    System.out.println("Invalid move.");
                } else if (goBoard[moveY][moveX] == " x" || goBoard[moveY][moveX] == " o"){
                    System.out.println("Invalid move.");
                } 
                else break;
            }

            if(flipper){
                goBoard[moveY][moveX] = " x";
            } else{
                goBoard[moveY][moveX] = " o";
            }

            for (int i = 0; i < goBoard.length; i++) {
                for (int j = 0; j < goBoard[i].length; j++) {
                    beenChecked = new boolean[9][9];
                    if (i == moveY && j == moveX) {
                        continue;
                    }
                    else if (!isAlive(i, j)) {
                        dead[i][j] = true;
                    }
                }
            }

            for (int i=0; i < goBoard.length; i++) {
                for (int j = 0; j < goBoard[i].length; j++){
                    if (dead[i][j]) {
                        goBoard[i][j] = null;
                        if (flipper) {
                            playerXscore++;
                        }
                        else {
                            playerOscore++;
                        }
                    }
                }
            }

            if (!isAlive(moveY, moveX)) {
                goBoard[moveY][moveX] = null;
                System.out.println("Invalid move. Piece would be immediately captured.");
            }
            else{
                getBoard(goBoard);
                flipper = !flipper;
            }
        }

        territory = new boolean[9][9];
        for (int i = 0; i < goBoard.length; i++) {
            for (int j = 0; j < goBoard[i].length; j++) {
                if (goBoard[i][j] == null && !territory[i][j]) {
                    Set<String> borderingColors = new HashSet<>();
                    int areaSize = checkTerritory(i, j, borderingColors);
                    if (borderingColors.size() == 1) {
                        String color = borderingColors.iterator().next();
                        if (color.equals(" x")) {
                            playerXscore += areaSize;
                        }
                        else {
                            playerOscore += areaSize;
                        }
                    }
                }
            }
        } 

        System.out.println("Player 1's score: " + playerXscore);
        System.out.println("Player 2's score: " + playerOscore);
        scn.close();
    }

}


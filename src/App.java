import java.util.Scanner;

public class App {

    static String[][] goBoard = {
        {null, null, " o", " o", null, null, null, null, null},
        {null, " o", " x", " x", " o", null, null, null, null},
        {null, " o", " x", null, " x", " o", null, null, null},
        {null, " o", " x", " x", " x", " o", null, null, null},
        {null, " o", " x", null, " x", " o", null, null, null},
        {null, null, " o", " x", " x", " o", null, null, null},
        {null, null, null, " o", " o", null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null}
    };


    static boolean[][] lives = new boolean[9][9];
    static boolean[][] territory = new boolean[9][9];
    static boolean[][] beenChecked = new boolean[9][9];

// *This prints the board to the terminal
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


    public static void main(String[] args) throws Exception {

        Scanner scn = new Scanner(System.in);
        Boolean quit = false;
        int moveX, moveY;
        Boolean flipper = true; 

        getBoard(goBoard);

        while(!quit){      
            System.out.println();

            boolean[][] dead = new boolean [9][9];

            while (true){
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
        scn.close();
    }

}



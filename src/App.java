import java.util.Scanner;

public class App {

    static String[][] goBoard = new String[10][10];

// *This prints the board to the terminal
    static void getBoard(String[][] b){
        for (int i = 0; i < b.length; i++){
            for (int j = 0; j < b[i].length; j++){ 
                if (i == 0){
                    System.out.print(j);
                    System.out.print(" ");
                    
                }       
                else if(b[i][j] == null){
                    if(j == 0){
                        System.out.print(i);
                    } else{
                        System.out.print(" +");
                    }
                } else{
                    System.out.print(b[i][j]);
                }
            }
            System.out.println();
        }
    }
    public static void main(String[] args) throws Exception {

        Scanner scn = new Scanner(System.in);
        Boolean quit = false;
        int moveX, moveY;
        Boolean flipper = true; 

        getBoard(goBoard);

        while(!quit){      
            System.out.println();

            while (true){
                System.out.println();
                System.out.println("Please enter the x coordinate for your piece (1-9): ");
                moveX = scn.nextInt();
                System.out.println("Please enter the y coordinate for your piece (1-9): ");
                moveY = scn.nextInt();

                if ((moveX < 1 || moveX > 9 || moveY < 1 || moveY > 9)){
                    System.out.println("Invalid move.");
                } else if (goBoard[moveY][moveX] == " x" || goBoard[moveY][moveX] == " o"){
                    System.out.println("Invalid move.");
                }
                else {
                    break;
                }
            }

            if(flipper){
                goBoard[moveY][moveX] = " o";
            } else{
                goBoard[moveY][moveX] = " x";
            }

            getBoard(goBoard);
            flipper = !flipper;

        }

        scn.close();
    }

}
/******************************************************
 * Author : Vishmi Kalansooriya
 * Date : 15th April 2022
 * Purpose : To create a simple maze game according to the user input file and let the user play it.
 *
 */




import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main
{
    public static void main(String args[])
    {

        menu();
    }
    /*********************************************************
     * Name : play
     * Date: 18/4/2022
     * Import : None
     * Export : None
     * Purpose: To make the game .
     *********************************************************************************************************/

    public static void play()
    {
        final Logger logger = Logger.getLogger(Main.class.getName());
        String[][] getMap = {};

        //Calling the (design pattern) class to create the map first.
        createMap newMap = new KeysDecorator(new theMap());
        getMap = newMap.readFile();

        //To clear the screen before printing the map to the user for the first time.
        System.out.println("\033[H\033[2J");
        print(getMap);

       // System.out.println(getKeyColor);


        int rows = 0;
        int cols = 0;

        rows = getMap.length;
        cols = getMap[0].length;

        int endRow = 0;
        int endCol = 0;

        Scanner sc = new Scanner(System.in);
        String inAction; // To get the user choice of movement.


        Game newGame; //Creating a new object of the game to make the movements.
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                if (getMap[i][j] == "E") // To get the row index and col index of the end point.
                {
                    endRow = i;
                    endCol = j;

                }
                do
                {
                    inAction = sc.nextLine(); //get the user input.

                    switch(inAction)
                    {
                        case "w" : //move forward if the user presses w.
                        {
                            newGame = new Game(new Front());
                            newGame.move(getMap);
                        }

                        break;

                        case "n" : //move upwards if the user presses n
                        {
                            newGame = new Game(new Up());
                            newGame.move(getMap);
                        }

                        break;

                        case "s" :  //move downwards if the user presses s.
                        {
                            newGame = new Game(new Down());
                            newGame.move(getMap);
                        }

                        break;

                        case "e" : //move backwards if the user presses e

                        {
                            newGame = new Game(new Back());
                            newGame.move(getMap);

                        }

                        break;

                        default:
                        {
                            logger.info("Please use only the Letters N,S,W,E !");
                            print(getMap);
                        }


                    }

                }while(getMap[endRow][endCol] == "P"); // Do the process until the pawn meets the end point.

            }
        }

        System.out.println("You win !"); // To indicate the completion of the game.

    }
    /*********************************************************
     * Name : print
     * Date: 19/4/2022
     * Import :  String[][] pMap
     * Export : None
     * Purpose: To print the map to the user.
     **********************************************************************************************************/


    public static void print(String[][] pMap) {
        int rows = pMap.length;
        int cols = pMap[0].length;

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                System.out.print(pMap[i][j]+" ");
            }
            System.out.println(" ");
        }
    }
    /*********************************************************
     * Name : traverseThroughDoors
     * Date: 24/4/2022
     * Import : String[][] pMap,int pRow, int pCol
     * Export : None
     * Purpose: To check if a door can be opened by a checking whether we have the relevant key.(in the arrayList)
     *********************************************************************************************************/


    public static void traverseThroughDoors(String[][] pMap,int pRow, int pCol)
    {
        final String  red = "\033[31m";
        final String green = "\033[32m";
        final String yellow = "\033[33m";
        final  String blue = "\033[34m";
        final String magenta = "\033[35m";
        final String cyan = "\033[36m";
        final String setBack = "\033[m";


        if(pMap[pRow][pCol].contains("\u2592"))
        {
           if(pMap[pRow][pCol].contains(red))
           {


           }else if(pMap[pRow][pCol].contains(green))
           {

           }else if(pMap[pRow][pCol].contains(yellow))
           {

           }else if(pMap[pRow][pCol].contains(blue))
           {

           }else if(pMap[pRow][pCol].contains(magenta))
           {

           }else if(pMap[pRow][pCol].contains(cyan))
           {

           }

        }

    }
    /*********************************************************
     * Name : stepOnKeys
     * Date: 24/4/2022
     * Import : None
     * Export : -
     * Purpose: To store the keys with their respective colors in an array list when the pawn steps on it.
     *********************************************************************************************************/


    public static void stepOnKeys()
    {
        ArrayList<String> keyBag = new ArrayList<>();

    }
    /*********************************************************
     * Name : menu
     * Date: 25/4/2022
     * Import : None
     * Export : None
     * Purpose: To print a welcome message for the user.
     *********************************************************************************************************/

    public static void menu()
    {
        Scanner sc = new Scanner(System.in);
        int getInput = 0;
        System.out.println(" Hello welcome to the game");
        System.out.println(" Small set of instructions before we begin");
        System.out.println("1. Press n - to move upwards \n2.Press s - to move downwards \n3.Press w- to move Forward \n4.Press e to move backwards");
        System.out.println("Press 1 if you are ready to play \nOr press 2 to exit");

        getInput = sc.nextInt();
        if(getInput == 1)
        {
            play();

        }else if(getInput == 2)
        {
            System.out.println("Thank you! Have a nice day. :)");
        }


        sc.close();

    }


}



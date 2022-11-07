/*********
 * Name : Up
 * Purpose : To implement the upward movement of the pawn and print the map with the changes.
 * A concrete class of the game behaviour class.
 *
 *
 * */



import java.util.logging.Logger;

public class Up implements GameBehaviour
{

    @Override
    public String[][] move(String[][] pMap)
    {
        final Logger logger = Logger.getLogger(Up.class.getName());

        int rows = pMap.length;// To get the number of rows in the map passed(2D array)
        int cols = pMap[0].length; // To get the number of rows in the map passed.
        int StartRow = 0;
        int StartCol = 0;

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                //To get the row index and col index of the pawn whenever it changes.
                if(pMap[i][j] == "P")
                {
                    StartRow = i;
                    StartCol = j;
                }


            }
        }

        //To let the pawn move only through doors, step on keys and to the end point.
        if(pMap[StartRow-1][StartCol] == " " || pMap[StartRow-1][StartCol].contains("\u2555") || pMap[StartRow-1][StartCol].contains("\u2592") ||  pMap[StartRow-1][StartCol].contains("E") )
        {
            pMap[StartRow-1][StartCol] = "P";
            pMap[StartRow][StartCol] = " ";
            StartRow = StartRow-1;

        }else
        {
            logger.info("You cannot walk through a Wall !"); // If the user tries to move through walls.

        }

        System.out.println("\033[H\033[2J"); //To clear the screen before printing it to the user.
        print(pMap);
        System.out.println("Key pressed : n  -> Your moving Upwards "); // To show where the user is moving.

        return pMap;
    }
    @Override
    public void print(String[][] pMap) {
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
}

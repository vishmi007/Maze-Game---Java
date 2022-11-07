
/*********
 * Name : Up
 * Purpose : To implement the backward movement of the pawn and print the map with the changes.
 * A concrete class of the game behaviour class.
 *
 *
 * */

import java.util.logging.Logger;

public class Back implements GameBehaviour
{
    @Override
    public String[][] move(String[][] pMap)
    {
        final Logger logger = Logger.getLogger(Back.class.getName());
       int rows = pMap.length;
       int cols = pMap[0].length;



        int StartRow = 0;
        int StartCol = 0;

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                if(pMap[i][j].contains("P"))
                {
                    StartRow = i;
                    StartCol = j;

                }


            }
        }

        if(pMap[StartRow][StartCol-1] == " "  || pMap[StartRow][StartCol-1].contains("\u2555") || pMap[StartRow][StartCol-1].contains("\u2592"))
        {
            pMap[StartRow][StartCol-1] = "P";
            pMap[StartRow][StartCol] = " ";
            StartCol = StartCol-1;

        }else
        {
            logger.info("You cannot walk through a Wall !");

        }

        System.out.println("\033[H\033[2J");
        print(pMap);
        System.out.println("Key pressed : e  -> Your moving Backward");

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

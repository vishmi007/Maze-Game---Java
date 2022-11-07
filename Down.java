
/*********
 * Name : Game.Game.Down
 * Purpose : To implement the Downward movement of the pawn and print the map with the changes.
 * A concrete class of the game behaviour class.
 *
 *
 * */
import java.util.logging.Logger;

public class Down implements GameBehaviour {
    @Override
    public String[][] move(String[][] pMap)
    {
        final Logger logger = Logger.getLogger(Down.class.getName());

        int rows = pMap.length;
        int cols = pMap[0].length;


        int StartRow = 1;
        int StartCol = 2;

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                if(pMap[i][j] == "P")
                {
                   StartRow = i;

                   StartCol = j;

               }


            }
        }

        if(pMap[StartRow+1][StartCol] == " " || pMap[StartRow+1][StartCol].contains("\u2555") ||  pMap[StartRow+1][StartCol].contains("\u2592") ||  pMap[StartRow+1][StartCol].contains("E"))
        {
            pMap[StartRow+1][StartCol] = "P";
            pMap[StartRow][StartCol] = " ";
            StartRow = StartRow+1;

        }else
        {
            logger.info("You cannot walk through a Wall !");

        }

        System.out.println("\033[H\033[2J");
        print(pMap);
        System.out.println("Key pressed : s  -> Your moving Downwards ");
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

import java.util.ArrayList;
/******************************************************
 * Author : Vishmi Kalansooriya
 * Date : 15th April 2022
 * Purpose : This is an interface created to design the map of the maze game.
 *
 * (Start of using the decorator pattern)
 *
 */
public interface createMap
{
   public String[][] readFile();
   public void fillMap(int pRows, int pCols,String[][] pMap,String pChar,String filler);
   public void printMap(int pRows, int pCols, String[][] pMap);
   public void positions(String inIndex, String pRow, String pCol,String[][] pMap) throws CustomException;
   public void connection(String[][] pMap,int pRows, int pCols);

}
